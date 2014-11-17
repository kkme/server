package com.hifun.soul.gameserver.refine.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.AuraLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanRefineMapEntity;
import com.hifun.soul.gamedb.entity.HumanRefineStageEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.battle.callback.BattleWithRefineMonsterCallback;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.RefineChallengeEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.refine.AttackStateType;
import com.hifun.soul.gameserver.refine.RefineMapInfo;
import com.hifun.soul.gameserver.refine.RefinePanelInfo;
import com.hifun.soul.gameserver.refine.RefineStageInfo;
import com.hifun.soul.gameserver.refine.converter.RefineMapToEntityConverter;
import com.hifun.soul.gameserver.refine.converter.RefineStageToEntityConverter;
import com.hifun.soul.gameserver.refine.msg.GCAttackRefineStage;
import com.hifun.soul.gameserver.refine.msg.GCAttackRefineStageFirst;
import com.hifun.soul.gameserver.refine.msg.GCOpenRefinePanel;
import com.hifun.soul.gameserver.refine.msg.GCRefineBagFull;
import com.hifun.soul.gameserver.refine.msg.GCUpdateRefinePanel;
import com.hifun.soul.gameserver.refine.service.RefineTemplateManager;
import com.hifun.soul.gameserver.refine.template.RefineMapTemplate;
import com.hifun.soul.gameserver.refine.template.RefineStageTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;
import com.hifun.soul.gameserver.vip.template.RefineCostTemplate;
import com.hifun.soul.gameserver.vip.template.RefineRefreshCostTemplate;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineMap;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineStage;

/**
 * 试炼管理器
 * @author magicstone
 */
public class HumanRefineManager implements ILoginManager,ICachableComponent,IHumanPersistenceManager{
	private static Logger logger = Loggers.REFINE_LOGGER;
	private Human human;
	private RefineStageInfo refineStageInfo = null;
	private Monster monster = null;
	/** 试炼地图信息 */
	private Map<Integer, RefineMapInfo> refineMapInfoMap = new HashMap<Integer, RefineMapInfo>();
	/** 试炼地图的关卡信息 */
	private Map<Integer, HashMap<Integer,RefineStageInfo>> refineStageInfosMap = new HashMap<Integer, HashMap<Integer,RefineStageInfo>>();
	private RefineMapToEntityConverter refineMapConverter;
	private RefineStageToEntityConverter refineStageConverter;
	private CacheEntry<Integer, RefineMapInfo> mapCache = new CacheEntry<Integer, RefineMapInfo>();
	private CacheEntry<String, RefineStageInfo> stageCache = new CacheEntry<String, RefineStageInfo>();
	private static final int INIT_STAGEID = 1;
	private GameFuncService gameFuncService;
	private RefineTemplateManager refineTemplateManager;
	private GameConstants gameConstants;
	private VipPrivilegeTemplateManager vipPrivilegeTemplateManager;
	
	public HumanRefineManager(Human human) {
		this.human = human;
		refineMapConverter = new RefineMapToEntityConverter(human);
		refineStageConverter = new RefineStageToEntityConverter(human);
		gameFuncService = GameServerAssist.getGameFuncService();
		refineTemplateManager = GameServerAssist.getRefineTemplateManager();
		gameConstants = GameServerAssist.getGameConstants();
		vipPrivilegeTemplateManager = GameServerAssist.getVipPrivilegeTemplateManager();
		
		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerLoginManager(this);
	}
	
	public Human getHuman() {
		return this.human;
	}
	
	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_RESET_REFINE_REFRESH_TIMES);
	}

	public void setLastResetTime(long lastTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_RESET_REFINE_REFRESH_TIMES, lastTime);
	}
	
	/**
	 * 重置免费试炼的刷新次数
	 */
	public void resetFreeTimes() {
		setFreeTimes(gameConstants.getRefineRefreshTimes());
	}
	
	/**
	 * 重置试练塔某地图的关卡状态
	 * @param mapId
	 */
	public void resetRefineStages(int mapId) {
		HashMap<Integer,RefineStageInfo> refineStageInfos = refineStageInfosMap.get(mapId);
		if(refineStageInfos != null){
			for(RefineStageInfo refineStageInfo : refineStageInfos.values()){
				if(refineStageInfo.getStageId() == INIT_STAGEID){
					refineStageInfo.setAttackState(AttackStateType.CAN_ATTACK.getIndex());
					updateRefineStageInfo(refineStageInfo);
				}
				else{
					refineStageInfo.setAttackState(AttackStateType.CANNOT_ATTACK.getIndex());
					updateRefineStageInfo(refineStageInfo);
				}
			}
		}
	}
	
	/**
	 * 获取免费试炼的刷新次数
	 * @return
	 */
	private int getFreeTimes() {
		return human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.REFINE_TIMES);
	}
	
	/**
	 * 设置免费试炼的刷新次数
	 * @param times
	 */
	private void setFreeTimes(int times) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.REFINE_TIMES, times);
	}
	
	/**
	 * 获取魔晶试炼的刷新次数
	 * @return
	 */
	private int getCrystalRefreshTimes() {
		return human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.CRYSTAL_REFINE_TIMES);
	}
	
	/**
	 * 设置魔晶试炼的刷新次数
	 * @param times
	 */
	private void setCrystalRefreshTimes(int times) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.CRYSTAL_REFINE_TIMES, times);
	}
	
	/**
	 * 打开指定类型的试练塔
	 * @param mapType
	 */
	public void openRefinePanelHandler(int mapType) {
		// 判断试练塔功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.REFINE, true)){
			return;
		}
		// 判断mapType是否是合法的地图类型
		mapType = checkMapType(mapType);
		RefinePanelInfo refinePanelInfo = getRefinePanelInfo(mapType);
		if(refinePanelInfo != null){
			// 更新对应的地图信息
			GCOpenRefinePanel gcMsg = new GCOpenRefinePanel();
			gcMsg.setBestStageId(refinePanelInfo.getBestStageId());
			gcMsg.setFreeTimes(refinePanelInfo.getFreeTimes());
			gcMsg.setAutoBattleCrystal(refinePanelInfo.getAutoBattleCrystal());
			gcMsg.setRefreshCrystal(refinePanelInfo.getRefreshCrystal());
			gcMsg.setAttackAllCrystal(refinePanelInfo.getAttackAllCrystal());
			gcMsg.setCrystalTimes(refinePanelInfo.getCrystalTimes());
			gcMsg.setRefineStageInfo(refinePanelInfo.getRefineStageInfo());
			gcMsg.setRefineMapInfos(refineMapInfoMap.values().toArray(new RefineMapInfo[0]));
			human.sendMessage(gcMsg);
		}
	}
	
	private void updateRefinePanel(int mapType) {
		// 判断mapType是否是合法的地图类型
		mapType = checkMapType(mapType);
		RefinePanelInfo refinePanelInfo = getRefinePanelInfo(mapType);
		if(refinePanelInfo != null){
			// 更新对应的地图信息
			GCUpdateRefinePanel gcMsg = new GCUpdateRefinePanel();
			gcMsg.setBestStageId(refinePanelInfo.getBestStageId());
			gcMsg.setFreeTimes(refinePanelInfo.getFreeTimes());
			gcMsg.setAutoBattleCrystal(refinePanelInfo.getAutoBattleCrystal());
			gcMsg.setRefreshCrystal(refinePanelInfo.getRefreshCrystal());
			gcMsg.setAttackAllCrystal(refinePanelInfo.getAttackAllCrystal());
			gcMsg.setCrystalTimes(refinePanelInfo.getCrystalTimes());
			gcMsg.setRefineStageInfo(refinePanelInfo.getRefineStageInfo());
			gcMsg.setRefineMapInfos(refineMapInfoMap.values().toArray(new RefineMapInfo[0]));
			human.sendMessage(gcMsg);
		}
	}
	
	private RefinePanelInfo getRefinePanelInfo(int mapType){
		RefinePanelInfo refinePanelInfo = new RefinePanelInfo();
		refinePanelInfo.setBestStageId(getBestStageId(mapType));
		refinePanelInfo.setFreeTimes(getFreeTimes());
		refinePanelInfo.setAutoBattleCrystal(getAutoBattleCrystal());
		RefineRefreshCostTemplate refineRefreshCostTemplate = refineTemplateManager.getRefineRefreshCostTemplate(getCrystalRefreshTimes()+1);
		refinePanelInfo.setRefreshCrystal(refineRefreshCostTemplate.getCrystal());
		refinePanelInfo.setAttackAllCrystal(getCanAutoAttackStageNum(mapType)*getAutoBattleCrystal());
		VipPrivilegeTemplate vipPrivilegeTemplate = vipPrivilegeTemplateManager.getVipTemplate(human.getVipLevel());
		refinePanelInfo.setCrystalTimes(vipPrivilegeTemplate.getRefineRefreshTimes()-getCrystalRefreshTimes());
		RefineStageInfo refineStageInfo = getRefineStageInfo(mapType);
		if(refineStageInfo == null){
			return null;
		}
		else{
			refinePanelInfo.setRefineStageInfo(refineStageInfo);
			return refinePanelInfo;
		}
	}
	
	/**
	 * 可以自动扫荡的关卡个数
	 * @param mapId
	 * @return
	 */
	private int getCanAutoAttackStageNum(int mapId) {
		int num = 0;
		HashMap<Integer,RefineStageInfo> stageInfos = refineStageInfosMap.get(mapId);
		if(stageInfos != null){
			for(RefineStageInfo refineStageInfo : stageInfos.values()){
				if(refineStageInfo.getGetted()
						&& refineStageInfo.getAttackState() != AttackStateType.ATTACKED.getIndex()){
					num+=1;
				}
			}
		}
		return num;
	}
	
	/**
	 * 校验地图id的合法性
	 * @param mapType
	 * @return
	 */
	private int checkMapType(int mapType) {
		int maxMapType = getOpenedMaxMapType();
		if(mapType <= 0 || mapType > maxMapType) {
			mapType = maxMapType;
		}
		return mapType;
	}
	
	/**
	 * 自动战斗单轮消耗魔晶
	 * @return
	 */
	private int getAutoBattleCrystal() {
		RefineCostTemplate refineCostTemplate = refineTemplateManager.getRefineCostTemplate();
		return refineCostTemplate.getCrystal();
	}
	
	/**
	 * 某个试炼地图打到的最高关卡
	 * @param mapType
	 * @return
	 */
	private int getBestStageId(int mapType){
		RefineMapInfo refineMapInfo = refineMapInfoMap.get(mapType);
		if(refineMapInfo == null){
			return 0;
		}
		return refineMapInfo.getBestStageId();
	}
	
	/**
	 * 克隆refineStageInfo
	 * @param refineStageInfo
	 * @return
	 */
	private RefineStageInfo cloneRefineStageInfo(RefineStageInfo refineStageInfo) {
		RefineStageInfo newRefineStageInfo = new RefineStageInfo();
		newRefineStageInfo.setCommonItem(refineStageInfo.getCommonItem());
		newRefineStageInfo.setMapId(refineStageInfo.getMapId());
		newRefineStageInfo.setMapName(refineStageInfo.getMapName());
		newRefineStageInfo.setMonsterIcon(refineStageInfo.getMonsterIcon());
		newRefineStageInfo.setMonsterId(refineStageInfo.getMonsterId());
		newRefineStageInfo.setMonsterLevel(refineStageInfo.getMonsterLevel());
		newRefineStageInfo.setMonsterName(refineStageInfo.getMonsterName());
		newRefineStageInfo.setRewardNum(refineStageInfo.getRewardNum());
		newRefineStageInfo.setRewardAnima(refineStageInfo.getRewardAnima());
		newRefineStageInfo.setStageId(refineStageInfo.getStageId());
		return newRefineStageInfo;
	}
	
	/**
	 * 组合主键
	 * @param mapId
	 * @param stageId
	 * @return
	 */
	private String genKey(int mapId, int stageId) {
		return mapId + "&" + stageId;
	}
	
	/**
	 * 获取试炼关卡信息
	 * @param mapType
	 * @return
	 */
	private RefineStageInfo getRefineStageInfo(int mapType){
		HashMap<Integer,RefineStageInfo> refineStageInfoList = refineStageInfosMap.get(mapType);
		if(refineStageInfoList == null){
			refineStageInfoList = new HashMap<Integer,RefineStageInfo>();
			refineStageInfosMap.put(mapType, refineStageInfoList);
		}
		// 没有任何数据表明刚进入该地图，初始化数据
		if(refineStageInfoList.size() == 0){
			RefineStageInfo refineStageInfo = refineTemplateManager.getRefineStageInfo(mapType, INIT_STAGEID);
			if(refineStageInfo == null){
				logger.warn(String
						.format("cannot find refineStageInfo! humanName=%s,mapType=%d,stageId=%d", human.getName(), mapType, INIT_STAGEID));
			}
			refineStageInfo = cloneRefineStageInfo(refineStageInfo);
			refineStageInfo.setAttackState(AttackStateType.CAN_ATTACK.getIndex());
			refineStageInfo.setGetted(false);
			refineStageInfoList.put(refineStageInfo.getStageId(),refineStageInfo);
			stageCache.addUpdate(genKey(refineStageInfo.getMapId(), refineStageInfo.getStageId()), refineStageInfo);
			return refineStageInfo;
		}
		// 查找现有数据是否有可以试炼的关卡
		for(RefineStageInfo refineStageInfo : refineStageInfoList.values()){
			if(refineStageInfo.getAttackState() == AttackStateType.CAN_ATTACK.getIndex()){
				return refineStageInfo;
			}
		}
		// 是否所有的关卡都征战过，如果都征战过就返回最后面的关卡
		if(refineStageInfoList.size() >= refineTemplateManager.getRefineStageSizeInMap(mapType)){
			return refineStageInfoList.get(refineTemplateManager.getLastStageId(mapType));
		}
		// 既然当前试炼塔还有没有征战的关卡，按照默认规则关卡id+1的规则找到对应的下一个关卡
		int suitableStageId = getMaxStageId(refineStageInfoList)+1;
		RefineStageInfo refineStageInfo = refineTemplateManager.getRefineStageInfo(mapType, suitableStageId);
		if(refineStageInfo == null){
			logger.warn(String
					.format("cannot find refineStageInfo! humanName=%s,mapType=%d,stageId=%d", human.getName(), mapType, suitableStageId));
			return null;
		}
		refineStageInfo = cloneRefineStageInfo(refineStageInfo);
		refineStageInfo.setAttackState(AttackStateType.CAN_ATTACK.getIndex());
		refineStageInfo.setGetted(false);
		refineStageInfoList.put(refineStageInfo.getStageId(),refineStageInfo);
		stageCache.addUpdate(genKey(refineStageInfo.getMapId(), refineStageInfo.getStageId()), refineStageInfo);
		return refineStageInfo;
	}
	
	/**
	 * 获取当前地图最大的关卡id
	 * @param stageInfos
	 * @return
	 */
	private int getMaxStageId(HashMap<Integer,RefineStageInfo> stageInfos) {
		int stageId = INIT_STAGEID;
		for(RefineStageInfo refineStageInfo : stageInfos.values()){
			if(refineStageInfo.getStageId()>stageId){
				stageId = refineStageInfo.getStageId();
			}
		}
		return stageId;
	}
	
	/**
	 * 是否有可以攻打的关卡
	 * @param mapType
	 * @return
	 */
	private boolean canAttackRefineStage(int mapType) {
		// 判断是否有对应的试练塔地图
		RefineMapInfo refineMapInfo = refineMapInfoMap.get(mapType);
		if(refineMapInfo == null){
			return false;
		}
		// 判断当前玩家是否达到开放该试炼地图的等级
		if(refineMapInfo.getMapOpenLevel() > human.getLevel()){
			human.sendWarningMessage(LangConstants.LEVEl_NOT_ENOUGH,refineMapInfo.getMapOpenLevel());
			return false;
		}
		// 判断这个地图是否开启
		if (mapType > getOpenedMaxMapType()) {
			return false;
		}
		// 获取试炼关卡
		RefineStageInfo refineStageInfo = getRefineStageInfo(mapType);
		if(refineStageInfo == null
				|| refineStageInfo.getAttackState() != AttackStateType.CAN_ATTACK.getIndex()){
			return false;
		}
		return true;
	}
	
	/**
	 * 攻打试练塔
	 * @param mapType
	 */
	public void attackRefineStageHandler(int mapType) {
		// 判断试练塔功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.REFINE, true)){
			return;
		}
		// 判断当前地图是否有可以攻打的关卡
		if(!canAttackRefineStage(mapType)){
			return;
		}
		// 攻打关卡
		RefineStageInfo refineStageInfo = getRefineStageInfo(mapType);
		// 判断背包中是否有足够的位置放置奖励物品(没有领取过攻打奖励留两个格子，攻打过留一个)
		if(refineStageInfo.getGetted()){
			if(human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 1){
				human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
				return;
			}
		}
		else{
			if(human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 2){
				human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
				return;
			}
		}
		Monster monster = GameServerAssist.getMonsterFactory().createMonster(refineStageInfo.getMonsterId());
		if (monster == null) {
			return;
		}
		this.refineStageInfo = refineStageInfo;
		this.monster = monster;
		GameServerAssist.getBattleManager().startBattleWithMapMonster(
				human, monster,new BattleWithRefineMonsterCallback(human));
	}
	
	/**
	 * 战斗胜利回调
	 */
	public void attackRefineStageWinCallBack() {
		// 处理普通的攻打掉落
		generalAttackHandle(true);
		// 判断之前是否领取过初次攻打奖励
		firstAttackHandle();
		// 更新攻打关卡信息
		updateRefineStageInfo(this.refineStageInfo);
		// 更新下一个可以攻打的攻打
		openNextCanAttackStage(this.refineStageInfo);
		// 判断是否需要修改地图攻打的最高关卡
		checkIsNeedUpdateBestStageId();
		// 更新试练塔信息
		updateRefinePanel(this.refineStageInfo.getMapId());
		// 清空此次战斗的信息
		clearRefineBattleInfo();
		human.getHumanGuideManager().showGuide(GuideType.ATTACK_REFINE_STAGE_SUCCESS.getIndex());
	}
	
	/**
	 * 设置下一个可以攻打的关卡
	 * @param refineStageInfo
	 */
	private void openNextCanAttackStage(RefineStageInfo refineStageInfo) {
		int mapId = refineStageInfo.getMapId();
		int stageId = refineStageInfo.getStageId();
		RefineStageInfo nextRefineStageInfo =  refineStageInfosMap.get(mapId).get(stageId+1);
		if(nextRefineStageInfo != null){
			nextRefineStageInfo.setAttackState(AttackStateType.CAN_ATTACK.getIndex());
			updateRefineStageInfo(nextRefineStageInfo);
		}
		else{
			nextRefineStageInfo = refineTemplateManager.getRefineStageInfo(mapId, stageId+1);
			if(nextRefineStageInfo != null){
				nextRefineStageInfo = cloneRefineStageInfo(nextRefineStageInfo);
				nextRefineStageInfo.setAttackState(AttackStateType.CAN_ATTACK.getIndex());
				nextRefineStageInfo.setGetted(false);
				updateRefineStageInfo(nextRefineStageInfo);
			}
		}
	}
	
	/**
	 * 普通攻打处理逻辑
	 */
	private void generalAttackHandle(boolean win) {
		if(win){
			// 设置正在攻打的关卡为已经打过的状态
			this.refineStageInfo.setAttackState(AttackStateType.ATTACKED.getIndex());
		}
		// 找到奖励模版
		RefineStageTemplate refineStageTemplate = refineTemplateManager.getRefineStageTemplate(this.refineStageInfo.getMapId(), this.refineStageInfo.getStageId());
		boolean isGetted = false;
		// 判断物品掉落
		if(win && MathUtils.shake(refineStageTemplate.getRate()*1.0f/SharedConstants.DEFAULT_FLOAT_BASE)){
			isGetted = true;
			// 给玩家掉落奖励
			human.getBagManager().putItems(BagType.MAIN_BAG, refineStageTemplate.getRewardId(), refineStageTemplate.getRewardNum(), ItemLogReason.REFINE, "");
		}
		if (win) {
			// 奖励灵气值
			human.addAura(refineStageTemplate.getRewardAura(), true, AuraLogReason.REFINE_REWARD, "");
		}
		// 通知客户端
		GCAttackRefineStage gcAttackRefineStage = new GCAttackRefineStage();
		gcAttackRefineStage.setRefineType(this.refineStageInfo.getMapId());
		gcAttackRefineStage.setStageId(this.refineStageInfo.getStageId());
		gcAttackRefineStage.setMonsterName(this.monster.getName());
		gcAttackRefineStage.setReward(CommonItemBuilder.genCommonItem(refineStageTemplate.getRewardId()));
		if(isGetted){
			gcAttackRefineStage.setRewardNum(refineStageTemplate.getRewardNum());
		}
		else{
			gcAttackRefineStage.setRewardNum(0);
		}
		gcAttackRefineStage.setWin(win);
		human.sendMessage(gcAttackRefineStage);
		// 发送试炼挑战事件
		RefineChallengeEvent event = new RefineChallengeEvent();
		human.getEventBus().fireEvent(event);
	}
	
	/**
	 * 第一次攻打胜利额外奖励
	 */
	private void firstAttackHandle() {
		if(!this.refineStageInfo.getGetted()){
			this.refineStageInfo.setGetted(true);
			// 找到奖励模版
			RefineStageTemplate refineStageTemplate = refineTemplateManager.getRefineStageTemplate(this.refineStageInfo.getMapId(), this.refineStageInfo.getStageId());
			// 给玩家奖励
			if (refineStageTemplate.getFirstRewardNum() != 0) {
				human.getBagManager().putItems(BagType.MAIN_BAG, refineStageTemplate.getFirstRewardId(), refineStageTemplate.getFirstRewardNum(), ItemLogReason.FIRST_REFINE, "");
			}
			// 通知客户端
			GCAttackRefineStageFirst gcAttackRefineStageFirst = new GCAttackRefineStageFirst();
			gcAttackRefineStageFirst.setRefineType(this.refineStageInfo.getMapId());
			gcAttackRefineStageFirst.setStageId(this.refineStageInfo.getStageId());
			gcAttackRefineStageFirst.setMonsterName(this.monster.getName());
			gcAttackRefineStageFirst.setReward(CommonItemBuilder.genCommonItem(refineStageTemplate.getFirstRewardId()));
			gcAttackRefineStageFirst.setRewardNum(refineStageTemplate.getFirstRewardNum());
			human.sendMessage(gcAttackRefineStageFirst);
		}
	}
	
	/**
	 * 判断是否需要修改最好攻打记录
	 */
	private void checkIsNeedUpdateBestStageId() {
		RefineMapInfo refineMapInfo = this.refineMapInfoMap.get(this.refineStageInfo.getMapId());
		if(refineMapInfo.getBestStageId() < this.refineStageInfo.getStageId()){
			refineMapInfo.setBestStageId(this.refineStageInfo.getStageId());
			this.refineMapInfoMap.put(refineMapInfo.getMapId(), refineMapInfo);
			mapCache.addUpdate(refineMapInfo.getMapId(), refineMapInfo);
		}
	}
	
	/**
	 * 更新试炼关卡信息
	 * @param refineStageInfo
	 */
	private void updateRefineStageInfo(RefineStageInfo refineStageInfo) {
		this.refineStageInfosMap.get(refineStageInfo.getMapId()).put(refineStageInfo.getStageId(), refineStageInfo);
		stageCache.addUpdate(genKey(refineStageInfo.getMapId(), refineStageInfo.getStageId()), refineStageInfo);
	}
	
	/**
	 * 清理战斗信息
	 */
	private void clearRefineBattleInfo() {
		this.refineStageInfo = null;
		this.monster = null;
	}
	
	/**
	 * 攻打关卡失败回调
	 */
	public void attackRefineStageLoseCallBack() {
		// 处理普通的攻打掉落
		generalAttackHandle(false);
		// 更新试练塔信息
		updateRefinePanel(this.refineStageInfo.getMapId());
		// 清空此次战斗的信息
		clearRefineBattleInfo();
	}
	
	/**
	 * 自动攻打试练塔
	 * @param mapType
	 */
	public void autoAttackRefineStageHandler(int mapType) {
		// 判断自动攻打试练塔功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.AUTO_REFINE, true)){
			return;
		}
		// 判断当前地图是否有可以攻打的关卡
		if(!canAttackRefineStage(mapType)){
			return;
		}
		// 自动攻打试炼关卡
		autoAttackRefine(mapType);
	}
	
	/**
	 * 自动攻打管理
	 * @param mapType
	 * @param isNeedTimely
	 */
	private boolean autoAttackRefine(int mapType){
		// 判断是否有足够的魔晶
		RefineCostTemplate refineCostTemplate = refineTemplateManager.getRefineCostTemplate();
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, refineCostTemplate.getCrystal())){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return false;
		}
		// 攻打关卡
		RefineStageInfo refineStageInfo = getRefineStageInfo(mapType);
		// 没有打赢过的关卡不能进行自动扫荡
		if(!refineStageInfo.getGetted()){
			return false;
		}
		// 判断背包中是否有足够的位置放置奖励物品
		if(human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 1){
			human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			GCRefineBagFull gcMsg = new GCRefineBagFull();
			human.sendMessage(gcMsg);
			return false;
		}
		Monster monster = GameServerAssist.getMonsterFactory().createMonster(refineStageInfo.getMonsterId());
		if (monster == null) {
			return false;
		}
		this.refineStageInfo = refineStageInfo;
		this.monster = monster;
		// 消耗魔晶
		if(human.getWallet().costMoney(CurrencyType.CRYSTAL, refineCostTemplate.getCrystal(), MoneyLogReason.AUTO_ATTACK_REFINE, "")){
			// 处理普通的攻打掉落
			generalAttackHandle(true);
			// 更新攻打关卡信息
			updateRefineStageInfo(this.refineStageInfo);
			// 更新下一个可以攻打的攻打
			openNextCanAttackStage(this.refineStageInfo);
			// 更新试练塔信息
			updateRefinePanel(this.refineStageInfo.getMapId());
			// 清空此次战斗的信息
			clearRefineBattleInfo();
			return true;
		}
		else{
			// 清空此次战斗的信息
			clearRefineBattleInfo();
			return false;
		}
	}
	
	/**
	 * 一键扫荡试练塔
	 * @param mapType
	 */
	public void attackAllRefineStageHandler(int mapType) {
		// 判断自动攻打试练塔所有关卡功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.AUTO_ALL_REFINE, true)){
			return;
		}
		int i=0;
		for(;canAttackRefineStage(mapType)&&i<100;i++){
			if(!autoAttackRefine(mapType)){
				break;
			}
		}
	}
	
	/**
	 * 生成以下地图是否开启信息
	 */
	private void generateMapOpenInfo() {
		for (Integer type : refineMapInfoMap.keySet()) {
			RefineMapInfo mapInfo = refineMapInfoMap.get(type);
			if (mapInfo.getMapId() == SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID) {
				mapInfo.setMapActivated(true);
			}
			if (mapInfo.getBestStageId() > 0 
					&& human.getLevel() >= mapInfo.getMapOpenLevel()) {
				mapInfo.setMapActivated(true);
			}
			if (mapInfo.getBestStageId() >= mapInfo.getMapStageNum()) {
				// 如果上一层地图都打完了，下一层地图要开启
				RefineMapInfo nextMapInfo = refineMapInfoMap.get(type + 1);
				if (nextMapInfo != null && human.getLevel() >= nextMapInfo.getMapOpenLevel()) {
					nextMapInfo.setMapActivated(true);
				}
			}
		}
	}
	
	/**
	 * 获取开启的最大试炼地图类型
	 * @return
	 */
	private int getOpenedMaxMapType() {
		generateMapOpenInfo();
		for (Integer type : refineMapInfoMap.keySet()) {
			if (!refineMapInfoMap.get(type).getMapActivated()) {
				return type - 1;
			}	
		}
		return refineMapInfoMap.size();
	}
	
	/**
	 * 刷新试练塔
	 * @param mapType
	 */
	public void refreshRefineMapHandler(int mapType) {
		// 判断功能是否开启
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.REFINE, true)){
			return;
		}
		List<RefineMapTemplate> refineMapTemplates = refineTemplateManager.getSuitableRefineMapTemplates(human.getLevel());
		if(refineMapTemplates == null
				|| refineMapTemplates.size() <= 0){
			return;
		}
		// 判断是否有免费的次数
		if(getFreeTimes()>0){
			// 重置选中地图的初始关卡为可以征战，其他为未征战状态
			// edited by ydj at 2014/4/22 策划需求变更，刷新当前副本
			resetRefineStages(mapType);
			// 设置免费次数
			setFreeTimes(getFreeTimes()-1);
			// 发送面板更新消息
			updateRefinePanel(mapType);
			return;
		}
		// 查找当前vip等级对应的模版
		VipPrivilegeTemplate vipPrivilegeTemplate = vipPrivilegeTemplateManager.getVipTemplate(human.getVipLevel());
		if(vipPrivilegeTemplate.getRefineRefreshTimes() <= getCrystalRefreshTimes()){
			human.sendWarningMessage(LangConstants.ELITE_REFRESH_TIME_USE_OUT);
			return;
		}
		// 判断是否有足够的魔晶
		RefineRefreshCostTemplate refineRefreshCostTemplate = refineTemplateManager.getRefineRefreshCostTemplate(getCrystalRefreshTimes()+1);
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, refineRefreshCostTemplate.getCrystal())){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return;
		}
		// 消耗魔晶
		if(human.getWallet().costMoney(CurrencyType.CRYSTAL, refineRefreshCostTemplate.getCrystal(), MoneyLogReason.REFRESH_REFINE, "")){
			// 重置选中地图的初始关卡为可以征战，其他为未征战状态
			// edited by ydj at 2014/4/22 策划需求变更，刷新当前副本
			resetRefineStages(mapType);
			// 设置魔晶刷新次数
			setCrystalRefreshTimes(getCrystalRefreshTimes()+1);
			// 发送面板更新消息
			updateRefinePanel(mapType);
		}
	}
	
	/**
	 * 重置次数和关卡状态
	 */
	public void resetRefineTimesAndRefineStages(){
		resetFreeTimes();
		setCrystalRefreshTimes(0);
		// edit by cfh 策划需求
//		for(RefineMapInfo refineMapInfo : refineMapInfoMap.values()){
//			resetRefineStages(refineMapInfo.getMapId());
//		}
	}

	@Override
	public void onLogin() {
		// 登陆时判断地图是否初始化
		for(RefineMapTemplate refineMapTemplate : refineTemplateManager.getAllRefineMapTemplate()){
			if(refineMapInfoMap.get(refineMapTemplate.getId()) == null){
				RefineMapInfo refineMapInfo = new RefineMapInfo();
				refineMapInfo.setMapId(refineMapTemplate.getId());
				refineMapInfo.setMapName(refineMapTemplate.getName());
				refineMapInfo.setMapOpenLevel(refineMapTemplate.getOpenLevel());
				refineMapInfo.setMapStageNum(refineTemplateManager.getRefineStageSizeInMap(refineMapTemplate.getId()));
				refineMapInfoMap.put(refineMapTemplate.getId(), refineMapInfo);
				mapCache.addUpdate(refineMapTemplate.getId(), refineMapInfo);
			}
		}
	}
	

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 地图信息
		for(HumanRefineMap.Builder builder : humanEntity.getBuilder().getRefineMapBuilderList()){
			RefineMapInfo refineMapInfo = new RefineMapInfo();
			refineMapInfo.setMapId(builder.getRefineMapData().getMapId());
			refineMapInfo.setBestStageId(builder.getRefineMapData().getBestStageId());
			RefineMapTemplate refineMapTemplate = refineTemplateManager.getRefineMapTemplate(builder.getRefineMapData().getMapId());
			if(refineMapTemplate != null){
				refineMapInfo.setMapName(refineMapTemplate.getName());
				refineMapInfo.setMapOpenLevel(refineMapTemplate.getOpenLevel());
				refineMapInfo.setMapStageNum(refineTemplateManager.getRefineStageSizeInMap(builder.
								getRefineMapData().getMapId()));
				refineMapInfoMap.put(builder.getRefineMapData().getMapId(),refineMapInfo);
			}
		}
		// 地图关卡信息
		for(HumanRefineStage.Builder builder : humanEntity.getBuilder().getRefineStageBuilderList()){
			RefineStageInfo refineStageInfo = new RefineStageInfo();
			int mapId = builder.getRefineStageData().getMapId();
			int stageId = builder.getRefineStageData().getStageId();
			refineStageInfo = refineTemplateManager.getRefineStageInfo(mapId, stageId);
			refineStageInfo = cloneRefineStageInfo(refineStageInfo);
			refineStageInfo.setAttackState(builder.getRefineStageData().getStageState());
			refineStageInfo.setGetted(builder.getRefineStageData().getGetted());
			if(refineStageInfosMap.get(mapId) == null){
				refineStageInfosMap.put(mapId, new HashMap<Integer,RefineStageInfo>());
			}
			refineStageInfosMap.get(mapId).put(stageId, refineStageInfo);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for(RefineMapInfo refineMapInfo : refineMapInfoMap.values()){
			humanEntity.getBuilder().addRefineMap(this.refineMapConverter.convert(refineMapInfo).getBuilder());
		}
		if(refineStageInfosMap.size() > 0){
			for(HashMap<Integer,RefineStageInfo> refineStageInfos : refineStageInfosMap.values()){
				for(RefineStageInfo refineStageInfo : refineStageInfos.values()){
					humanEntity.getBuilder().addRefineStage(this.refineStageConverter.convert(refineStageInfo).getBuilder());
				}
			}
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for(RefineMapInfo refineMapInfo : this.mapCache.getAllUpdateData()){
			HumanRefineMapEntity entity = this.refineMapConverter.convert(refineMapInfo);
			updateList.add(entity);
		}
		for(RefineStageInfo refineStageInfo : this.stageCache.getAllUpdateData()){
			HumanRefineStageEntity entity = this.refineStageConverter.convert(refineStageInfo);
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}
	
	/**
	 * 是否已经挑战完可攻打的关卡
	 * @return
	 */
	public boolean isFinishCanAttactStages(){		
		for(Map<Integer,RefineStageInfo> mapInfo : refineStageInfosMap.values()){
			for(RefineStageInfo refineInfo : mapInfo.values()){
				if(refineInfo.getAttackState()==AttackStateType.CAN_ATTACK.getIndex()){
					return false;
				}
			}
		}
		return true;
	}
}
