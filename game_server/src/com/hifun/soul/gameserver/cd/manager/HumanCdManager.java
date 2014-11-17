package com.hifun.soul.gameserver.cd.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanCdEntity;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.cd.CdInfo;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.converter.CdInfoToEntityConverter;
import com.hifun.soul.gameserver.cd.msg.CdQueueInfo;
import com.hifun.soul.gameserver.cd.msg.GCCdQueueInfo;
import com.hifun.soul.gameserver.cd.msg.GCCdQueueInfos;
import com.hifun.soul.gameserver.cd.msg.GCCdSpeedUp;
import com.hifun.soul.gameserver.cd.template.CdTemplate;
import com.hifun.soul.gameserver.cd.template.CdTiredTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.proto.common.Cds.CdData;
import com.hifun.soul.proto.data.entity.Entity.HumanCd;


/**
 * 
 * cd管理器
 * 
 * @author magicstone
 *
 */
public class HumanCdManager implements IHumanPersistenceManager, ICachableComponent,ILoginManager {
	private static Logger logger = Loggers.CD_LOGGER;
	private Human human;
	/** cd信息 */
	private Map<CdType,CdInfo> cdInfoMap = new HashMap<CdType,CdInfo>();
	/** cd缓存 */
	private CacheEntry<CdType,CdInfo> cdInfoCaches = new CacheEntry<CdType,CdInfo>();
	/** 转化器 */
	private CdInfoToEntityConverter converter;

	public HumanCdManager(Human human) {
		this.human = human;
		converter = new CdInfoToEntityConverter(human);
		
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		this.human.registerLoginManager(this);
	}
	
	public Human getHuman() {
		return human;
	}
	
	/**
	 * 获取cdType对应的cd信息
	 * 
	 * @param cdType
	 * @return
	 */
	public CdInfo getCdInfo(CdType cdType) {
		CdInfo cdInfo = cdInfoMap.get(cdType);
		if(cdInfo == null){
			cdInfo = new CdInfo();
			cdInfo.setCdType(cdType.getIndex());
			cdInfo.setEndTime(GameServerAssist.getSystemTimeService().now());
		}
		return cdInfo;
	}
	
	/**
	 * cdType是否可以添加ms长的时间
	 * 
	 * @param cdType
	 * @param ms
	 * @return
	 */
	public boolean canAddCd(CdType cdType, long ms) {
		CdTemplate cdTemplate = GameServerAssist.getCdTemplateManager().getCdTemplate(cdType.getIndex());
		if(cdTemplate == null) {
			return false;
		}
		Long cdEndTime = getCdInfo(cdType).getEndTime();
		long now = GameServerAssist.getSystemTimeService().now();
		if(cdTemplate.getCdMode()==2){
			//cd不可累加，cd结束后才能添加
			return now >= cdEndTime;
		}
		long maxMs = cdTemplate.getCdMaxNum() * TimeUtils.MIN;
		
		return ( cdEndTime - now + ms ) <= maxMs;
	}

	/**
	 * 添加ms长的时间
	 * 
	 * @param cdType
	 * @param ms
	 */
	public void addCd(CdType cdType, long ms) {
		// 出现ms小于零的情况肯定是非法情况
		if(ms < 0){
			return;
		}
		// 调用添加cd之前应该已经判断过，所以如果再次判断不能添加直接返回
		if(!canAddCd(cdType,ms)){
			return;
		}
		
		CdInfo cdInfo = getCdInfo(cdType);
		long now = GameServerAssist.getSystemTimeService().now();
		if(cdInfo.getEndTime()<now){
			cdInfo.setEndTime(now + ms);
		}
		else{
			cdInfo.setEndTime(cdInfo.getEndTime() + ms);
		}
		cdInfo.setTimes(cdInfo.getTimes()+1);
		cdInfoMap.put(cdType, cdInfo);
		// 同步缓存
		cdInfoCaches.addUpdate(cdType, cdInfo);
	}
	
	/**
	 * 消除cdType的cd
	 * 
	 * @param cdType
	 */
	public void removeCd(CdType cdType) {
		long now = GameServerAssist.getSystemTimeService().now();
		// 获取cd时间
		long cdInterval = getDiffTime(getCdInfo(cdType).getEndTime());
		if(cdInterval <= 0) {
			return;
		}
		// 计算需要消耗的货币数量
		CdTemplate cdTemplate = GameServerAssist.getCdTemplateManager().getCdTemplate(cdType.getIndex());
		if(!cdTemplate.isCanRemove()){
			return;
		}
		CurrencyType currencyType = CurrencyType.indexOf(cdTemplate.getCurrencyType());
		int currencyNum = getRemoveCdCost(cdType,cdInterval);
		// 判断是否有足够的货币
		if (!human.getWallet().isEnough(currencyType, currencyNum)) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,currencyType.getDesc());
			return;
		}
		// 扣除货币
		boolean cost = human.getWallet().costMoney(currencyType, currencyNum,MoneyLogReason.REMOVE_CD, "");
		// 消除cd
		if (!cost) {
			logger.error("消除cd时扣除对应货币失败。humanid"+human.getHumanGuid()+"; currencyType："+currencyType.getDesc()+"; num:"+currencyNum);
			human.sendErrorMessage(LangConstants.SERVER_ERROR);
			return;
		}
		CdInfo cdInfo = getCdInfo(cdType);
		cdInfo.setEndTime(now);

		cdInfoMap.put(cdType, cdInfo);
		// 同步缓存
		cdInfoCaches.addUpdate(cdType, cdInfo);
		
		// 押运CD特殊处理
		if (cdType == CdType.ESCORT) {
			EscortInfo escortInfo = GameServerAssist.getGlobalEscortManager()
					.getEscortInfo(human.getHumanGuid());
			GameServerAssist.getGlobalEscortManager().endEscort(escortInfo);
		}
	}
	
	/**
	 * 免费消除cd
	 * 
	 * @param cdType
	 */
	public void removeCdFree(CdType cdType) {
		if(!cdInfoMap.containsKey(cdType)){
			return;
		}
		long now = GameServerAssist.getSystemTimeService().now();
		CdInfo cdInfo = getCdInfo(cdType);
		cdInfo.setEndTime(now);

		cdInfoMap.put(cdType, cdInfo);
		// 同步缓存
		cdInfoCaches.addUpdate(cdType, cdInfo);

	}
	
	/**
	 * 获取花费
	 * @param cdType
	 * @param cdInterval
	 * @return
	 */
	public int getRemoveCdCost(CdType cdType, long cdInterval){
		CdTemplate cdTemplate = GameServerAssist.getCdTemplateManager().getCdTemplate(cdType.getIndex());
		if(cdTemplate == null
				|| cdInterval < 1){
			return 0;
		}
		return (int)(cdTemplate.getCurrencyNum() * ((cdInterval-1)/(TimeUtils.MIN*cdTemplate.getCostMins())+1));
	}
	
	/**
	 * 获取cdType对应的疲劳度
	 * 
	 * @param cdType
	 * @return
	 */
	private float getCdTired(CdType cdType) {
		CdTemplate cdTemplate = GameServerAssist.getCdTemplateManager().getCdTemplate(cdType.getIndex());
		if( cdTemplate.getTiredRatio()<=0){
			return 0;
		}
		CdTiredTemplate cdTiredTemplate = GameServerAssist.getCdTemplateManager().getCdTiredTemplate(getCdInfo(cdType).getTimes());
		if(cdTiredTemplate == null) {
			return GameServerAssist.getCdTemplateManager().getMaxCdTired()*cdTemplate.getTiredRatio();
		}
		return cdTiredTemplate.getTired() * cdTemplate.getTiredRatio();
	}
	
	/**
	 * 获取操作应该花费的cd时间
	 * 
	 * @param cdType
	 * @param baseTime
	 * @return
	 */
	public long getSpendTime(CdType cdType, long baseTime) {
		CdTemplate cdTemplate = GameServerAssist.getCdTemplateManager().getCdTemplate(cdType.getIndex());
		if(cdTemplate == null){
			return baseTime;
		}
		return (long) (baseTime + (cdTemplate.getBaseCd() + getCdTired(cdType))*TimeUtils.MIN);
	}
	
	/**
	 * 下发cd信息
	 * 
	 */
	public void snapCdQueueInfos() {
		List<CdQueueInfo> cdQueueInfos = new ArrayList<CdQueueInfo>();
		for(CdType cdType : cdInfoMap.keySet()){
			CdQueueInfo cdQueueInfo = new CdQueueInfo();
			cdQueueInfo.setCdType(cdType.getIndex());
			cdQueueInfo.setTimeDiff((int) getDiffTime(getCdInfo(cdType).getEndTime()));
			cdQueueInfos.add(cdQueueInfo);
		}
		
		GCCdQueueInfos gcMsg = new GCCdQueueInfos();
		gcMsg.setCds(cdQueueInfos.toArray(new CdQueueInfo[0]));
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 单个cd信息下发
	 * 
	 * @param cdType
	 */
	public void snapCdQueueInfo(CdType cdType) {
		CdQueueInfo cdQueueInfo = new CdQueueInfo();
		cdQueueInfo.setCdType(cdType.getIndex());
		CdInfo cdInfo = getCdInfo(cdType);
		cdQueueInfo.setTimeDiff((int) getDiffTime(getCdInfo(cdType).getEndTime()));
		GCCdQueueInfo gcMsg = new GCCdQueueInfo();
		gcMsg.setCd(cdQueueInfo);
		human.sendMessage(gcMsg);
		cdInfoMap.put(cdType, cdInfo);
		// 同步缓存
		cdInfoCaches.addUpdate(cdType, cdInfo);
	}
	
	/**
	 * 获取cdType剩余的cd时间
	 * 
	 * @param cdType
	 * @return
	 */
	public int getRemainCd(CdType cdType) {
		return (int) getDiffTime(getCdInfo(cdType).getEndTime());
	}
	
	/**
	 * 下发加速消除cd的确认信息
	 * @param cdType
	 */
	public void snapCdSpeedUpInfo(CdType cdType){
		long timeDiff = getDiffTime(getCdInfo(cdType).getEndTime());
		if(timeDiff>0){
			CdTemplate cdTemplate = GameServerAssist.getCdTemplateManager().getCdTemplate(cdType.getIndex());
			int currencyNum = getRemoveCdCost(cdType, timeDiff);
			GCCdSpeedUp gcCdSpeedUpMsg = new GCCdSpeedUp();
			gcCdSpeedUpMsg.setCdType(cdType.getIndex());
			gcCdSpeedUpMsg.setCurrencyType(cdTemplate.getCurrencyType());
			gcCdSpeedUpMsg.setCurrencyNum(currencyNum);
			human.sendMessage(gcCdSpeedUpMsg);
		}
	}
	
	/**
	 * 减少CD时间
	 * 
	 * @param time 减少时间
	 */
	public void reduceCdTime(CdType cdType, long time) {
		if(!cdInfoMap.containsKey(cdType)){
			return;
		}
		CdInfo cdInfo = getCdInfo(cdType);
		cdInfo.setEndTime(cdInfo.getEndTime() - time);

		cdInfoMap.put(cdType, cdInfo);
		// 同步缓存
		cdInfoCaches.addUpdate(cdType, cdInfo);
	}
	
	/**
	 * 增加CD时间
	 * 
	 * @param time 增加时间
	 */
	public void addCdTime(CdType cdType, long time) {
		if (!cdInfoMap.containsKey(cdType)) {
			return;
		}
		CdInfo cdInfo = getCdInfo(cdType);
		long endTime = cdInfo.getEndTime();
		long now = GameServerAssist.getSystemTimeService().now();
		if (endTime < now) {
			endTime = now;
		}
		cdInfo.setEndTime(endTime + time);

		cdInfoMap.put(cdType, cdInfo);
		// 同步缓存
		cdInfoCaches.addUpdate(cdType, cdInfo);
	}
	
	
	/**
	 * 获取time与当前时间的间隔
	 * 
	 * @param time
	 * @return
	 */
	public long getDiffTime(long time) {
		long now = GameServerAssist.getSystemTimeService().now();
		long diffTime = time - now;
		if(diffTime < 0){
			return 0;
		}
		else{
			return diffTime;
		}
	}
	
	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_CD_RESET_TIME);
	}
	
	public void setLastResetTime(long lastResetTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_CD_RESET_TIME, lastResetTime);
	}
	
	/**
	 * 重置cd的疲劳度
	 * 
	 */
	public void resetCdTired() {
		for(CdInfo cdInfo : cdInfoMap.values()){
			cdInfo.setTimes(0);
			cdInfoMap.put(CdType.indexOf(cdInfo.getCdType()), cdInfo);
			// 同步缓存
			cdInfoCaches.addUpdate(CdType.indexOf(cdInfo.getCdType()), cdInfo);
		}
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for(HumanCd.Builder builder : humanEntity.getBuilder().getCdBuilderList()){
			CdData cdData = builder.getCdData();
			CdType cdType = CdType.indexOf(cdData.getCdType());
			CdInfo cdInfo = new CdInfo();
			cdInfo.setCdType(cdType.getIndex());
			cdInfo.setTimes(cdData.getTimes());
			cdInfo.setEndTime(cdData.getEndTime());
			
			cdInfoMap.put(cdType, cdInfo);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for(CdInfo cdInfo : cdInfoMap.values()){
			humanEntity.getBuilder().addCd(
					this.converter.convert(cdInfo).getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for(CdInfo cdInfo : cdInfoCaches.getAllUpdateData()){
			HumanCdEntity entity = this.converter.convert(cdInfo);
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		for(CdInfo cdInfo : cdInfoCaches.getAllDeleteData()){
			HumanCdEntity entity = this.converter.convert(cdInfo);
			deleteList.add(entity);
		}
		return deleteList;
	}

	@Override
	public void onLogin() {
		snapCdQueueInfos();		
	}
	
}
