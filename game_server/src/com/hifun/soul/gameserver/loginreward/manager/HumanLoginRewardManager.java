package com.hifun.soul.gameserver.loginreward.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanLoginRewardEntity;
import com.hifun.soul.gamedb.entity.HumanSpecialLoginRewardEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.loginreward.LoginRewardInfo;
import com.hifun.soul.gameserver.loginreward.LoginRewardStateType;
import com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo;
import com.hifun.soul.gameserver.loginreward.converter.LoginRewardInfoToEntityConverter;
import com.hifun.soul.gameserver.loginreward.converter.SpecialLoginRewardInfoToEntityConverter;
import com.hifun.soul.gameserver.loginreward.msg.GCHasLoginReward;
import com.hifun.soul.gameserver.loginreward.msg.GCShowLoginRewardPanel;
import com.hifun.soul.gameserver.loginreward.service.LoginRewardTemplateManager;
import com.hifun.soul.gameserver.loginreward.template.LoginRewardTimeTemplate;
import com.hifun.soul.gameserver.loginreward.template.LoginSpecialRewardTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.proto.data.entity.Entity.HumanLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialLoginReward;

public class HumanLoginRewardManager implements ILoginManager,IHumanPersistenceManager,ICachableComponent {
	private Logger logger = Loggers.LOGINREWARD_LOGGER;
	private Human human;
	/** 抽取的奖励信息 */
	private List<LoginRewardInfo> rewards = new ArrayList<LoginRewardInfo>();
	/** 特殊奖励 */
	private Map<Integer,SpecialLoginRewardInfo> specialLoginRewardInfoMap = new HashMap<Integer,SpecialLoginRewardInfo>();
	/** 转化器 */
	private LoginRewardInfoToEntityConverter loginRewardConverter;
	private SpecialLoginRewardInfoToEntityConverter specialLoginRewardConverter;
	/** 缓存 */
	private CacheEntry<Integer, LoginRewardInfo> rewardCaches = new CacheEntry<Integer, LoginRewardInfo>();
	private CacheEntry<Integer, SpecialLoginRewardInfo> specialRewardCaches = new CacheEntry<Integer, SpecialLoginRewardInfo>();
	/** 连续登陆模版管理 */
	private LoginRewardTemplateManager loginRewardTemplateManager;
	
	public HumanLoginRewardManager(Human human) {
		this.human = human;
		loginRewardConverter = new LoginRewardInfoToEntityConverter(human);
		specialLoginRewardConverter = new SpecialLoginRewardInfoToEntityConverter(human);
		loginRewardTemplateManager = GameServerAssist.getLoginRewardTemplateManager();
		initSpecialLoginRewardInfos();
		
		this.human.registerLoginManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
	}
	
	public Human getHuman() {
		return human;
	}
	
	/**
	 * 初始化特殊奖励信息
	 */
	private void initSpecialLoginRewardInfos() {
		for(LoginSpecialRewardTemplate loginSpecialRewardTemplate : loginRewardTemplateManager.getLoginSpecialRewardTemplates()){
			if(specialLoginRewardInfoMap.get(loginSpecialRewardTemplate.getId()) == null){
				setLoginRewardState(loginSpecialRewardTemplate.getId(), LoginRewardStateType.CAN_NOT_GET, false);
			}
		}
	}
	
	/**
	 * 连续登陆天数
	 * 
	 * @return
	 */
	public int getDays() {
		int days = human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LOGINREWARD_DAYS);
		return days>0?days:1;
	}
	
	/**
	 * 连续登陆天数
	 * 
	 * @return
	 */
	public void setDays(int days) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.LOGINREWARD_DAYS, days);
	}
	
	/**
	 * 剩余领取次数
	 * 
	 * @return
	 */
	public int getTimes() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LOGINREWARD_TIMES);
	}
	
	/**
	 * 设置领取次数
	 * 
	 * @param times
	 */
	public void setTimes(int times) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.LOGINREWARD_TIMES, times);
	}
	
	/**
	 * 获取上次更新时间
	 * 
	 * @return
	 */
	public long getLastDaysUpdateTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(HumanLongProperty.LAST_LOGINREWARD_RESET_TIME);
	}
	
	/**
	 * 设置上次执行时间
	 * 
	 * @param lastDaysUpdateTime
	 */
	public void setLastDaysUpdateTime(long lastDaysUpdateTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_LOGINREWARD_RESET_TIME, lastDaysUpdateTime);
	}
	
	/**
	 * 获取特别奖励领取状态
	 * 
	 * @param loginSpecialRewardType
	 * @return
	 */
	public int getLoginRewardState(int loginSpecialRewardType) {
		SpecialLoginRewardInfo specialLoginRewardInfo = specialLoginRewardInfoMap.get(loginSpecialRewardType);
		if(specialLoginRewardInfo == null){
			return LoginRewardStateType.CAN_NOT_GET.getIndex();
		}
		return specialLoginRewardInfo.getState();
	}
	
	/**
	 * 设置特别奖品状态
	 * 
	 * @param loginSpecialRewardType
	 * @param loginRewardStateType
	 */
	public void setLoginRewardState(int loginSpecialRewardType, LoginRewardStateType loginRewardStateType, boolean needUpdate) {
		SpecialLoginRewardInfo specialLoginRewardInfo = specialLoginRewardInfoMap.get(loginSpecialRewardType);
		if(specialLoginRewardInfo == null){
			SimpleCommonItem simpleCommonItem = loginRewardTemplateManager.getReward(loginSpecialRewardType);
			if(simpleCommonItem == null){
				return;
			}
			specialLoginRewardInfo = new SpecialLoginRewardInfo();
			specialLoginRewardInfo.setDays(loginSpecialRewardType);
			specialLoginRewardInfo.setReward(simpleCommonItem);
		}
		specialLoginRewardInfo.setState(loginRewardStateType.getIndex());
		specialLoginRewardInfoMap.put(loginSpecialRewardType, specialLoginRewardInfo);
		if(needUpdate){
			specialRewardCaches.addUpdate(loginSpecialRewardType, specialLoginRewardInfo);
		}
	}
	
	/**
	 * 过零点的时候更新
	 */
	public void updateLoginReward() {
		if(TimeUtils.isSameDay(getLastDaysUpdateTime(), GameServerAssist.getSystemTimeService().now())){
			return;
		}
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(new Date(getLastDaysUpdateTime()));
		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(new Date(GameServerAssist.getSystemTimeService().now()));
		int diffDays = TimeUtils.getSoFarWentDays(lastDate, nowDate);
		if(diffDays > 1){
			setDays(1);
		}
		else if(diffDays == 1){
			setDays(getDays()+1);
		}
		// 更新连续登陆奖励领取次数和特殊奖励状态
		updateLoginRewardTimesAndSpecialRewardState();
		// 清空上次翻牌奖励
		for(LoginRewardInfo loginRewardInfo : rewards){
			loginRewardInfo.setCommonItem(null);
			rewardCaches.addUpdate(loginRewardInfo.getIndex(), loginRewardInfo);
		}
		rewards.clear();
		// 更新状态
		updateLoginRewardState();
	}
	
	private void updateLoginRewardTimesAndSpecialRewardState(){
		// 设置翻牌领奖次数
		setTimes(getLoginRewardTotalTimes());
		// 重置特殊奖励状态
		int specialType = getDays()%SharedConstants.LOGIN_REWARD_MAX_DAYS;
		if(specialType == 0){
			specialType = SharedConstants.LOGIN_REWARD_MAX_DAYS;
		}
		resetSpecialLoginRewardState(specialType==1);
		// 判断特殊奖励的领取状态
		for(SpecialLoginRewardInfo specialLoginRewardInfo : specialLoginRewardInfoMap.values()){
			if(specialType == specialLoginRewardInfo.getDays()){
				setLoginRewardState(specialType,LoginRewardStateType.CAN_GET, true);
			}
		}
	}
	
	/**
	 * 当前连续登陆天数可以翻牌次数
	 * @return
	 */
	private int getLoginRewardTotalTimes() {
		int days = getDays();
		if(days > SharedConstants.LOGIN_MAX_DAYS){
			days = SharedConstants.LOGIN_MAX_DAYS;
		}
		LoginRewardTimeTemplate loginRewardTimeTemplate = loginRewardTemplateManager.getLoginRewardTimeTemplate(days);
		if(loginRewardTimeTemplate == null){
			logger.error("can not find loginRewardTimeTemplate! humanGuid:" + human.getHumanGuid() + "; days:" + getDays());
			return 0;
		}
		return loginRewardTimeTemplate.getRewardNum();
	}
	
	private void resetSpecialLoginRewardState(boolean isNewPeriod) {
		// 未达成状态不变，可以领取状态变为已过期，已过期状态如果开启下一个周期(specialType==0)重置为未达成，已领取状态同已过期
		for(SpecialLoginRewardInfo specialLoginRewardInfo : specialLoginRewardInfoMap.values()){
			if(specialLoginRewardInfo.getState() == LoginRewardStateType.CAN_GET.getIndex()){
				if(isNewPeriod){
					setLoginRewardState(specialLoginRewardInfo.getDays(),LoginRewardStateType.CAN_NOT_GET, true);
				}
				else{
					setLoginRewardState(specialLoginRewardInfo.getDays(),LoginRewardStateType.PASTED, true);
				}
			}
			else if(specialLoginRewardInfo.getState() == LoginRewardStateType.PASTED.getIndex() && isNewPeriod){
				setLoginRewardState(specialLoginRewardInfo.getDays(),LoginRewardStateType.CAN_NOT_GET, true);
			}
			else if(specialLoginRewardInfo.getState() == LoginRewardStateType.GETED.getIndex() && isNewPeriod){
				setLoginRewardState(specialLoginRewardInfo.getDays(),LoginRewardStateType.CAN_NOT_GET, true);
			}
		}
	}

	@Override
	public void onLogin() {
		updateLoginRewardState();
	}
	
	/**
	 * 获取奖励信息
	 * 
	 * @return
	 */
	public LoginRewardInfo[] getRewards() {
		List<LoginRewardInfo> loginRewardInfos = new ArrayList<LoginRewardInfo>();
		for(LoginRewardInfo reward : rewards){
			if(reward.getCommonItem() != null){
				loginRewardInfos.add(reward);
			}
		}
		return loginRewardInfos.toArray(new LoginRewardInfo[0]);
	}
	
	/**
	 * 校验奖品位置的合法性
	 * 
	 * @param index
	 * @return
	 */
	public boolean checkIndex(int index) {
		if(index < 0
				|| index >= SharedConstants.LOGIN_REWARD_REWARD_SIZE){
			return false;
		}
		for(LoginRewardInfo reward : rewards){
			if(reward.getIndex() == index
					&& reward.getCommonItem() != null){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取奖励物品的id
	 * 
	 * @return
	 */
	public List<Integer> getRewardIds() {
		List<Integer> rewardIds = new ArrayList<Integer>();
		for(LoginRewardInfo reward : rewards){
			SimpleCommonItem commonItem = reward.getCommonItem();
			if(commonItem != null){
				rewardIds.add(commonItem.getItemId());
			}
		}
		return rewardIds;
	}
	
	/**
	 * 添加领取奖品
	 * 
	 * @param loginRewardInfo
	 */
	public void addLoginRewardInfo(LoginRewardInfo loginRewardInfo) {
		if(loginRewardInfo != null){
			rewards.add(loginRewardInfo);
			// 同步缓存
			rewardCaches.addUpdate(loginRewardInfo.getIndex(), loginRewardInfo);
		}
	}
	
	/**
	 * 连续登陆奖励信息
	 * @return
	 */
	public SpecialLoginRewardInfo[] getSpecialLoginRewardInfos() {
		if(specialLoginRewardInfoMap.values() == null){
			return new SpecialLoginRewardInfo[0];
		}
		else{
			return specialLoginRewardInfoMap.values().toArray(new SpecialLoginRewardInfo[0]);
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for(LoginRewardInfo loginRewardInfo : rewardCaches.getAllUpdateData()){
			HumanLoginRewardEntity entity = this.loginRewardConverter.convert(loginRewardInfo);
			updateList.add(entity);
		}
		for(SpecialLoginRewardInfo specialLoginRewardInfo : specialRewardCaches.getAllUpdateData()){
			HumanSpecialLoginRewardEntity entity = this.specialLoginRewardConverter.convert(specialLoginRewardInfo);
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for(HumanLoginReward.Builder builder : humanEntity.getBuilder().getLoginRewardBuilderList()){
			LoginRewardInfo loginRewardInfo = new LoginRewardInfo();
			loginRewardInfo.setIndex(builder.getLoginRewardData().getIndex());
			SimpleCommonItem simpleCommonItem = CommonItemBuilder.genSimpleCommonItem(builder.getLoginRewardData().getItemId());
			loginRewardInfo.setCommonItem(simpleCommonItem);
			rewards.add(loginRewardInfo);
		}
		for(HumanSpecialLoginReward.Builder builder : humanEntity.getBuilder().getSpecialLoginRewardBuilderList()){
			SpecialLoginRewardInfo specialLoginRewardInfo = new SpecialLoginRewardInfo();
			specialLoginRewardInfo.setDays(builder.getSpecialLoginRewardData().getDays());
			specialLoginRewardInfo.setState(builder.getSpecialLoginRewardData().getState());
			SimpleCommonItem simpleCommonItem = loginRewardTemplateManager.getReward(builder.getSpecialLoginRewardData().getDays());
			if(simpleCommonItem == null){
				continue;
			}
			else{
				specialLoginRewardInfo.setReward(simpleCommonItem);
			}
			specialLoginRewardInfoMap.put(builder.getSpecialLoginRewardData().getDays(), specialLoginRewardInfo);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for(LoginRewardInfo loginRewardInfo : rewards){
			humanEntity.getBuilder().addLoginReward(
					this.loginRewardConverter.convert(loginRewardInfo).getBuilder());
		}
		for(SpecialLoginRewardInfo specialLoginRewardInfo : specialLoginRewardInfoMap.values()) {
			humanEntity.getBuilder().addSpecialLoginReward(
					this.specialLoginRewardConverter.convert(specialLoginRewardInfo).getBuilder());
		}
	}
	
	/**
	 * 是否有可以领取的奖励
	 * @return
	 */
	public boolean hasLoginReward() {
		if(getTimes() > 0){
			return true;
		}
		for(SpecialLoginRewardInfo specialLoginRewardInfo : specialLoginRewardInfoMap.values()){
			if(specialLoginRewardInfo.getState() == LoginRewardStateType.CAN_GET.getIndex()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 同步状态
	 */
	public void updateLoginRewardState() {
		GCHasLoginReward gcMsg = new GCHasLoginReward();
		gcMsg.setHasReward(hasLoginReward());
		human.sendMessage(gcMsg);
	}

	public void onOpenClick() {
		GCShowLoginRewardPanel gcMsg = new GCShowLoginRewardPanel();
		gcMsg.setDays(getDays());
		gcMsg.setLeftTimes(getTimes());
		gcMsg.setTotalTimes(getLoginRewardTotalTimes());
		gcMsg.setLoginRewardTimeInfos(loginRewardTemplateManager.getLoginRewardTimeInfos());
		gcMsg.setSelectItems(getRewards());
		gcMsg.setSpecialLoginRewardInfos(getSpecialLoginRewardInfos());
		human.sendMessage(gcMsg);
	}
}
