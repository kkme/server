package com.hifun.soul.gameserver.yellowvip.manager;

import java.util.List;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.StringUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanYellowVipRewardStateEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.template.SpreeTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.IntPropertyCacheSet;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.tencent.TencentUserInfo;
import com.hifun.soul.gameserver.yellowvip.YellowVipRewardState;
import com.hifun.soul.gameserver.yellowvip.YellowVipRewardType;
import com.hifun.soul.gameserver.yellowvip.msg.GCUpdateYellowVipRewardState;
import com.hifun.soul.gameserver.yellowvip.template.YearYellowVipRewardTemplate;
import com.hifun.soul.gameserver.yellowvip.template.YellowVipDailyRewardTemplate;
import com.hifun.soul.gameserver.yellowvip.template.YellowVipLevelUpRewardTemplate;
import com.hifun.soul.proto.common.YellowVip.HumanYellowVipRewardStateInfo;

/**
 * 腾讯黄钻管理器
 * 
 * @author magicstone
 * 
 */
public class HumanYellowVipManager implements IHumanPersistenceManager, ICachableComponent, ILoginManager,IEventListener {
	private Human human;
	private int onceRewardState = YellowVipRewardState.CANNOT_GET.getIndex();
	private int dailyRewardState = YellowVipRewardState.CANNOT_GET.getIndex();
	private int yearVipDailyRewardState = YellowVipRewardState.CANNOT_GET.getIndex();
	private int[] levelUpRewardState;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();

	public HumanYellowVipManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	@Override
	public void onLogin() {		
		int yellowVipLevel = 0;
		int isYearYellowVip = 0;
		int isYellowHighVip = 0;
		String openId = "";
		IntPropertyCacheSet propertySet = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY);
		if (human.getPlayer().getLocalProperties() != null) {
			Object objYellowVipLevel = human.getPlayer().getLocalProperties().get(SharedConstants.YELLOW_VIP_LEVEL);
			yellowVipLevel = objYellowVipLevel != null && !StringUtils.isEmpty(objYellowVipLevel.toString()) ? Integer
					.parseInt(objYellowVipLevel.toString()) : 0;
			Object objIsYearVip = human.getPlayer().getLocalProperties().get(SharedConstants.IS_YELLOW_YEAR_VIP);
			isYearYellowVip = objIsYearVip != null && !StringUtils.isEmpty(objIsYearVip.toString()) ? Integer
					.parseInt(objIsYearVip.toString()) : 0;			
			Object objIsYellowHighVip = human.getPlayer().getLocalProperties().get(SharedConstants.IS_YELLOW_HIGH_VIP);
			isYellowHighVip = objIsYellowHighVip != null && !StringUtils.isEmpty(objIsYellowHighVip.toString()) ? Integer
					.parseInt(objIsYellowHighVip.toString()) : 0;	
			Object objOpenId = human.getPlayer().getLocalProperties().get(SharedConstants.OPEN_ID);
			openId = objOpenId != null ? objOpenId.toString() : "";
			if(yellowVipLevel<=0){
				isYearYellowVip = 0;
				isYellowHighVip = 0;
			}
		}		
		propertySet.setPropertyValue(HumanIntProperty.YELLOW_VIP_LEVEL, yellowVipLevel);
		propertySet.setPropertyValue(HumanIntProperty.IS_YEAR_YELLOW_VIP, isYearYellowVip);		
		propertySet.setPropertyValue(HumanIntProperty.IS_YELLOW_HIGH_VIP, isYellowHighVip);
		TencentUserInfo txUserInfo = new TencentUserInfo();
		txUserInfo.setId(human.getHumanGuid());
		txUserInfo.setPassportId(human.getPlayer().getPassportId());
		txUserInfo.setOpenId(openId);
		txUserInfo.setYellowVipLevel(yellowVipLevel);
		txUserInfo.setIsYearYellowVip(isYearYellowVip==1);
		txUserInfo.setIsYellowHighVip(isYellowHighVip==1);
		GameServerAssist.getTencentUserInfoManager().updateTencentUserInfo(txUserInfo);
		updateLevelUpRewardState();
	}

	/**
	 * 获取黄钻等级
	 * 
	 * @return
	 */
	public int getYellowVipLevel() {
		return human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.YELLOW_VIP_LEVEL);
	}

	/**
	 * 获取黄钻新手礼包领取状态
	 * 
	 * @return
	 */
	public int getYellowVipOnceRewardState() {
		int yellowVipLevel = getYellowVipLevel();
		if (onceRewardState == YellowVipRewardState.HAS_GOT.getIndex()) {
			return onceRewardState;
		} else if (yellowVipLevel > 0) {
			return YellowVipRewardState.CAN_GET.getIndex();
		} else {
			return YellowVipRewardState.CANNOT_GET.getIndex();
		}
	}

	/**
	 * 获取黄钻每日礼包领取状态
	 * 
	 * @return
	 */
	public int getYellowVipDailyRewardState() {
		int yellowVipLevel = getYellowVipLevel();
		if (dailyRewardState == YellowVipRewardState.HAS_GOT.getIndex()) {
			return dailyRewardState;
		} else if (yellowVipLevel > 0) {
			return YellowVipRewardState.CAN_GET.getIndex();
		} else {
			return YellowVipRewardState.CANNOT_GET.getIndex();
		}
	}

	/**
	 * 获取年费黄钻每日礼包领取状态
	 * 
	 * @return
	 */
	public int getYearYellowVipRewardState() {
		IntPropertyCacheSet propertySet = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY);
		int yellowVipLevel = propertySet.getPropertyValue(HumanIntProperty.YELLOW_VIP_LEVEL);
		int isYearYellowVip = propertySet.getPropertyValue(HumanIntProperty.IS_YEAR_YELLOW_VIP);
		if (yearVipDailyRewardState == YellowVipRewardState.HAS_GOT.getIndex()) {
			return yearVipDailyRewardState;
		} else if (isYearYellowVip > 0 && yellowVipLevel > 0) {
			return YellowVipRewardState.CAN_GET.getIndex();
		} else {
			return YellowVipRewardState.CANNOT_GET.getIndex();
		}
	}

	/**
	 * 获取黄钻升级礼包领取状态
	 * 
	 * @return
	 */
	public int[] getYellowVipLevelUpRewardState() {
		return levelUpRewardState;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		int[] levels = GameServerAssist.getYellowVipTemplateManager().getLevelUpRewardKeys();
		levelUpRewardState = new int[levels.length];
		for (int i = 0; i < levelUpRewardState.length; i++) {
			levelUpRewardState[i] = YellowVipRewardState.CANNOT_GET.getIndex();
		}
		HumanYellowVipRewardStateInfo yellowVipRewardInfo = humanEntity.getBuilder().getYellowVipRewardState();
		if (yellowVipRewardInfo == null) {
			return;
		}
		this.dailyRewardState = yellowVipRewardInfo.getDailyRewardState();		
		this.yearVipDailyRewardState = yellowVipRewardInfo.getYearVipDailyRewardState();
		int size = levelUpRewardState.length > yellowVipRewardInfo.getLevelUpRewardStateCount() ? yellowVipRewardInfo
				.getLevelUpRewardStateCount() : levelUpRewardState.length;
		if (this.getYellowVipLevel() > 0) {
			this.onceRewardState = yellowVipRewardInfo.getOnceRewardState();
			for (int i = 0; i < size; i++) {
				levelUpRewardState[i] = yellowVipRewardInfo.getLevelUpRewardState(i);
			}			
		}
	}
	
	private void updateLevelUpRewardState(){		
		if (this.getYellowVipLevel() > 0) {	
			int[] levels = GameServerAssist.getYellowVipTemplateManager().getLevelUpRewardKeys();
			int humanLevel = human.getLevel();
			for (int i = 0; i < levels.length; i++) {
				if (humanLevel >= levels[i] && levelUpRewardState[i] != YellowVipRewardState.HAS_GOT.getIndex()) {
					levelUpRewardState[i] = YellowVipRewardState.CAN_GET.getIndex();
				}
			}
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		humanEntity.getBuilder().setYellowVipRewardState(this.convertToEntity().getBuilder());
	}

	/**
	 * 领取黄钻新手礼包
	 */
	public void receiveOnceReward() {
		if (getYellowVipOnceRewardState() != YellowVipRewardState.CAN_GET.getIndex()) {
			return;
		}
		int itemId = GameServerAssist.getYellowVipTemplateManager().getOnceRewardItemId();
		Item rewardItem = ItemFactory.creatNewItem(human, itemId);
		if (rewardItem == null) {
			return;
		}
		int itemCount = 1;
		if (rewardItem.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()) {
			SpreeTemplate spreeTemplate = GameServerAssist.getSpreeTemplateManager().getSpreeTemplate(itemId);
			if (spreeTemplate == null) {
				return;
			}
			itemCount = spreeTemplate.getItems().size() < spreeTemplate.getItemCount() ? spreeTemplate.getItems()
					.size() : spreeTemplate.getItemCount();

		}
		if (human.getBagManager().getFreeSize(BagType.MAIN_BAG) < itemCount) {
			human.sendErrorMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		human.getBagManager().putItems(BagType.MAIN_BAG, itemId, 1, ItemLogReason.YELLOW_VIP_ONCE_REWARD, "");
		onceRewardState = YellowVipRewardState.HAS_GOT.getIndex();
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		GCUpdateYellowVipRewardState gcMsg = new GCUpdateYellowVipRewardState();
		gcMsg.setRewardType(YellowVipRewardType.ONCE_REWARD.getIndex());
		gcMsg.setRewardState(new int[] { onceRewardState });
		human.sendMessage(gcMsg);
	}

	/**
	 * 领取年费黄钻每日礼包
	 */
	public void receiveYearVipDailyReward() {
		if (this.getYearYellowVipRewardState() != YellowVipRewardState.CAN_GET.getIndex()) {
			return;
		}
		YearYellowVipRewardTemplate template = GameServerAssist.getYellowVipTemplateManager()
				.getYearYellowVipRewardTemplate();
		if (human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 2) {
			human.sendErrorMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		human.getBagManager().putItems(BagType.MAIN_BAG, template.getItemId1(), template.getItemCount1(),
				ItemLogReason.YEAR_YELLOW_VIP_DAILY_REWARD, "");
		human.getBagManager().putItems(BagType.MAIN_BAG, template.getItemId2(), template.getItemCount2(),
				ItemLogReason.YEAR_YELLOW_VIP_DAILY_REWARD, "");
		yearVipDailyRewardState = YellowVipRewardState.HAS_GOT.getIndex();
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		GCUpdateYellowVipRewardState gcMsg = new GCUpdateYellowVipRewardState();
		gcMsg.setRewardType(YellowVipRewardType.YEAR_VIP_REWARD.getIndex());
		gcMsg.setRewardState(new int[] { yearVipDailyRewardState });
		human.sendMessage(gcMsg);
	}

	/**
	 * 领取黄钻每日礼包
	 */
	public void receiveDailyReward() {
		if (this.getYellowVipDailyRewardState() != YellowVipRewardState.CAN_GET.getIndex()) {
			return;
		}
		int yellowVipLevel = this.getYellowVipLevel();
		if (yellowVipLevel <= 0) {
			return;
		}
		YellowVipDailyRewardTemplate template = GameServerAssist.getYellowVipTemplateManager()
				.getYellowVipDailyRewardTemplate(human.getLevel());
		if (template == null) {
			return;
		}
		int maxRewardLevel = template.getVipCoin().size();
		int rewardIndex = yellowVipLevel >= maxRewardLevel ? maxRewardLevel - 1 : yellowVipLevel - 1;
		human.getWallet().addMoney(CurrencyType.COIN, template.getVipCoin().get(rewardIndex), true,
				MoneyLogReason.YELLOW_VIP_DAILY_REWARD, "");
		dailyRewardState = YellowVipRewardState.HAS_GOT.getIndex();
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		GCUpdateYellowVipRewardState gcMsg = new GCUpdateYellowVipRewardState();
		gcMsg.setRewardType(YellowVipRewardType.DAILY_REWARD.getIndex());
		gcMsg.setRewardState(new int[] { dailyRewardState });
		human.sendMessage(gcMsg);
	}

	public void receiveLevelUpReward(int rewardIndex) {
		if (rewardIndex < 0 || rewardIndex > levelUpRewardState.length - 1) {
			return;
		}
		if (levelUpRewardState[rewardIndex] != YellowVipRewardState.CAN_GET.getIndex()) {
			return;
		}
		int[] levels = GameServerAssist.getYellowVipTemplateManager().getLevelUpRewardKeys();
		if (human.getLevel() < levels[rewardIndex]) {
			return;
		}
		if (human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 1) {
			human.sendErrorMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		YellowVipLevelUpRewardTemplate template = GameServerAssist.getYellowVipTemplateManager()
				.getYellowVipLevelUpRewardTemplate(levels[rewardIndex]);
		human.getWallet().addMoney(CurrencyType.COIN, template.getCoin(), true,
				MoneyLogReason.YELLOW_VIP_LEVEL_UP_REWARD, "");
		human.getBagManager().putItems(BagType.MAIN_BAG, template.getItemId(), template.getItemCount(),
				ItemLogReason.YELLOW_VIP_LEVEL_UP_REWARD, "");
		levelUpRewardState[rewardIndex] = YellowVipRewardState.HAS_GOT.getIndex();
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		GCUpdateYellowVipRewardState gcMsg = new GCUpdateYellowVipRewardState();
		gcMsg.setRewardType(YellowVipRewardType.LEVEL_UP_REWARD.getIndex());
		gcMsg.setRewardState(levelUpRewardState);
		human.sendMessage(gcMsg);
	}

	private HumanYellowVipRewardStateEntity convertToEntity() {
		HumanYellowVipRewardStateInfo.Builder builder = HumanYellowVipRewardStateInfo.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());
		builder.setDailyRewardState(dailyRewardState);
		builder.setYearVipDailyRewardState(yearVipDailyRewardState);
		builder.setOnceRewardState(onceRewardState);
		for (Integer state : levelUpRewardState) {
			builder.addLevelUpRewardState(state);
		}
		return new HumanYellowVipRewardStateEntity(builder);
	}

	public void resetDailyData() {
		IntPropertyCacheSet propertySet = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY);
		int yellowVipLevel = propertySet.getPropertyValue(HumanIntProperty.YELLOW_VIP_LEVEL);
		int isYearYellowVip = propertySet.getPropertyValue(HumanIntProperty.IS_YEAR_YELLOW_VIP);
		if (yellowVipLevel > 0) {
			dailyRewardState = YellowVipRewardState.CAN_GET.getIndex();
		} else {
			dailyRewardState = YellowVipRewardState.CANNOT_GET.getIndex();
		}
		if (isYearYellowVip > 0 && yellowVipLevel > 0) {
			yearVipDailyRewardState = YellowVipRewardState.CAN_GET.getIndex();
		} else {
			yearVipDailyRewardState = YellowVipRewardState.CANNOT_GET.getIndex();
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	@Override
	public void onEvent(IEvent event) {
		int[] levels = GameServerAssist.getYellowVipTemplateManager().getLevelUpRewardKeys();		
		if (this.getYellowVipLevel() > 0) {
			int humanLevel = human.getLevel();
			boolean isUpdate = false;
			for (int i = 0; i < levels.length; i++) {
				if (humanLevel >= levels[i] && levelUpRewardState[i] != YellowVipRewardState.HAS_GOT.getIndex()) {
					levelUpRewardState[i] = YellowVipRewardState.CAN_GET.getIndex();
					isUpdate = true;
				}
			}
			if(isUpdate){
				GCUpdateYellowVipRewardState gcMsg = new GCUpdateYellowVipRewardState();
				gcMsg.setRewardType(YellowVipRewardType.LEVEL_UP_REWARD.getIndex());
				gcMsg.setRewardState(levelUpRewardState);
				human.sendMessage(gcMsg);
			}
		}
		
	}

}
