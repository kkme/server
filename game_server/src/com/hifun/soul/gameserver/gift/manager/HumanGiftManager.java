package com.hifun.soul.gameserver.gift.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.GiftUpgradeEvent;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.gift.GiftActiveState;
import com.hifun.soul.gameserver.gift.model.GiftInfo;
import com.hifun.soul.gameserver.gift.model.GiftProperty;
import com.hifun.soul.gameserver.gift.msg.GCShowGiftPanel;
import com.hifun.soul.gameserver.gift.msg.GCUpdateGiftState;
import com.hifun.soul.gameserver.gift.template.GiftLevelTemplate;
import com.hifun.soul.gameserver.gift.template.GiftTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.entity.HumanGiftEntity;
import com.hifun.soul.proto.common.Gifts.Gift;
import com.hifun.soul.proto.data.entity.Entity.HumanGift;

public class HumanGiftManager implements IHumanPersistenceManager,
		ICachableComponent, IEventListener, IHumanPropertiesLoadForBattle {

	Human human;
	/** 已激活的天赋属性 */
	private Map<Integer, GiftProperty> giftPropertyMap = new HashMap<Integer, GiftProperty>();
	/** 天赋等级列表 */
	private Map<Integer, Integer> giftLevelMap = new HashMap<Integer, Integer>();
	GiftTemplateManager templateManager = null;
	private CacheEntry<Integer, IEntity> caches = new CacheEntry<Integer, IEntity>();

	public HumanGiftManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		templateManager = GameServerAssist.getGiftTemplateManager();
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 初始化天赋信息
		for (HumanGift.Builder builder : humanEntity.getBuilder()
				.getGiftBuilderList()) {
			giftLevelMap.put(builder.getGift().getGiftId(), builder.getGift()
					.getLevel());
		}

		// 把每行第一个天赋设置为已解锁状态
		if (giftLevelMap.size() == 0) {
			for (GiftTemplate giftTemplate : templateManager.getGiftTemplates()
					.values()) {
				if (giftTemplate.getPreviousGiftId() == 0) {
					giftLevelMap.put(giftTemplate.getId(), 0);
					caches.addUpdate(giftTemplate.getId(),
							convertToEntity(giftTemplate.getId(), 0));
				}
			}
		}
		// 初始化天赋属性
		for (int giftId : giftLevelMap.keySet()) {
			int giftLevel = getGiftLevel(giftId);
			if (giftLevel == 0) {
				continue;
			}
			GiftLevelTemplate giftLevelTemplate = templateManager
					.getLevelTemplate(giftId, giftLevel);
			GiftProperty property = new GiftProperty();
			property.setProperty(giftLevelTemplate.getPropertyId());
			property.setPropertyValue(giftLevelTemplate.getPropertyValue());
			property.setPropertyValueType(giftLevelTemplate.getAmendMethod());
			giftPropertyMap.put(property.getProperty(), property);
		}

		// 加成天赋属性
		amendGiftProperties();
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (Integer giftId : giftLevelMap.keySet()) {
			HumanGiftEntity giftEntity = new HumanGiftEntity();
			giftEntity.getBuilder().setHumanGuid(human.getHumanGuid());
			giftEntity.getBuilder().setGift(
					Gift.newBuilder().setGiftId(giftId)
							.setLevel(giftLevelMap.get(giftId)));
			humanEntity.getBuilder().addGift(giftEntity.getBuilder());
		}
	}

	/**
	 * 获取所有的天赋点数量
	 */
	private int getGiftPointTotal() {
		return human.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.GIFT_POINT_TOTAL);
	}

	/**
	 * 获取剩余天赋点
	 */
	public int getGiftPointRemain() {
		return human.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.GIFT_POINT_REMAIN);
	}

	/**
	 * 设置剩余天赋点
	 */
	private void setGiftPointRemain(int remainNum) {
		human.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.GIFT_POINT_REMAIN, remainNum);
	}

	/**
	 * 设置所有天赋
	 * 
	 * @param totalNum
	 */
	private void setGiftPointTotal(int totalNum) {
		human.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.GIFT_POINT_TOTAL, totalNum);
	}

	/**
	 * 添加天赋点
	 */
	public void addGiftPoint(int giftPoint) {
		if (giftPoint <= 0) {
			return;
		}
		setGiftPointTotal(getGiftPointTotal() + giftPoint);
		setGiftPointRemain(getGiftPointRemain() + giftPoint);
	}

	/**
	 * 通过评星添加天赋点
	 */
	public void addGiftPointByStar(int addStar) {
		int giftPoint = addStar * SharedConstants.STAR_TO_GIFT_POINT;
		addGiftPoint(giftPoint);
	}

	/**
	 * 使用天赋点
	 */
	private void useGiftPoint(int useNum) {
		int remainNum = getGiftPointRemain();
		if (useNum > remainNum) {
			return;
		}
		remainNum = remainNum - useNum;
		setGiftPointRemain(remainNum);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	/**
	 * 加成天赋属性
	 */
	private void amendGiftProperties() {
		for (int propertyId : giftPropertyMap.keySet()) {
			GiftProperty property = giftPropertyMap.get(propertyId);
			human.getPropertyManager().amendProperty(human,
					AmendMethod.valueOf(property.getPropertyValueType()),
					propertyId, property.getPropertyValue(),
					PropertyLogReason.GIFT_ACTIVE, "");
		}

	}

	/**
	 * 获取天赋属性
	 */
	private GiftProperty[] getGiftAttributes() {
		return giftPropertyMap.values().toArray(new GiftProperty[0]);
	}

	/**
	 * 获取不同攻击系天赋信息
	 */
	private GiftInfo[] getGiftInfos() {
		List<GiftInfo> infoList = new ArrayList<GiftInfo>();
		infoList.addAll(templateManager.getAttactGifts(human, giftLevelMap));
		infoList.addAll(templateManager.getDefenceGifts(human, giftLevelMap));
		infoList.addAll(templateManager.getEnduranceGifts(human, giftLevelMap));
		Collections.sort(infoList, new GiftSortComparator());
		return infoList.toArray(new GiftInfo[0]);
	}

	private GiftInfo getGiftInfo(int giftId) {
		GiftTemplate giftTemplate = templateManager.getGiftTemplate(giftId);
		return templateManager.genGiftInfoByTemplate(human, giftTemplate,
				getGiftLevel(giftId));
	}

	/**
	 * 发送天赋面板的更新消息
	 */
	public void sendShowGiftPanelMessage() {
		GCShowGiftPanel gcMsg = new GCShowGiftPanel();
		gcMsg.setGiftPointTotal(getGiftPointTotal());
		gcMsg.setGiftPointRemain(getGiftPointRemain());
		gcMsg.setGiftAttributes(getGiftAttributes());
		gcMsg.setGifts(getGiftInfos());
		human.sendMessage(gcMsg);
	}

	/**
	 * 判断指定的天赋是否可以点亮(状态置为未激活)
	 * 
	 */
	private boolean canLightGift(int giftId) {
		// 状态是未解锁，角色等级达到，前置等级达到，就将状态置为未激活
		if (getGiftState(giftId) != GiftActiveState.LOCKED.getIndex()) {
			return false;
		}
		GiftTemplate giftTemplate = templateManager.getGiftTemplate(giftId);
		if (human.getLevel() < giftTemplate.getOpenLevel()) {
			return false;
		}
		int previouseId = giftTemplate.getPreviousGiftId();
		if (previouseId != 0) {
			Integer previouseLevel = giftLevelMap.get(previouseId);
			if (previouseLevel == null) {
				return false;
			}
			if (previouseLevel < giftTemplate.getPreviousGiftLevel()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 激活天赋(方法已被废除)
	 */
	public void activeGift(int giftId) {

	}

	private HumanGiftEntity convertToEntity(int giftId, int giftLevel) {
		HumanGiftEntity entity = new HumanGiftEntity();
		entity.getBuilder().setHumanGuid(human.getHumanGuid());
		entity.getBuilder().setGift(
				Gift.newBuilder().setGiftId(giftId).setLevel(giftLevel));
		return entity;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return caches.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	class GiftSortComparator implements Comparator<GiftInfo> {
		@Override
		public int compare(GiftInfo o1, GiftInfo o2) {
			return o1.getId() - o2.getId();
		}
	}

	/**
	 * 获取天赋的状态
	 */
	public int getGiftState(int giftId) {
		int state = 0;
		if (giftLevelMap.get(giftId) == null) {
			state = GiftActiveState.LOCKED.getIndex();
		} else {
			if (giftLevelMap.get(giftId) > 0) {
				state = GiftActiveState.ACTIVE.getIndex();
			} else if (giftLevelMap.get(giftId) == 0) {
				state = GiftActiveState.UNACTIVE.getIndex();
			}
		}
		return state;
	}

	/**
	 * 获取天赋的等级
	 */
	public Integer getGiftLevel(int giftId) {
		return giftLevelMap.get(giftId);
	}

	/**
	 * 升级天赋
	 */
	public void upgradeGift(int giftId) {
		List<GiftInfo> changedInfoList = new ArrayList<GiftInfo>();
		// 消耗天赋点和道具
		GiftLevelTemplate levelTemplate = templateManager.getLevelTemplate(
				giftId, getGiftLevel(giftId));
		int itemId = levelTemplate.getCostItemId();
		int itemNum = levelTemplate.getCostItemNum();
		if (human.getBagManager().removeItemByItemId(itemId, itemNum,
				ItemLogReason.UPGRADE_GIFT, "")) {
			useGiftPoint(levelTemplate.getCostGiftPoint());
			int oldLevel = giftLevelMap.get(giftId);
			giftLevelMap.put(giftId, oldLevel + 1);
			caches.addUpdate(giftId, convertToEntity(giftId, oldLevel + 1));
			changedInfoList.add(getGiftInfo(giftId));
			// 修改下一个天赋的状态
			GiftTemplate giftTemplate = templateManager.getGiftTemplate(giftId);
			int nextGiftId = giftTemplate.getNextGiftId();
			// 如果有下个天赋，并且状态是未解锁，角色等级达到，前置等级达到，就将状态置为未激活
			if (nextGiftId != 0 && canLightGift(nextGiftId)) {
				setGiftStateUnactive(nextGiftId);
				changedInfoList.add(getGiftInfo(nextGiftId));
			}
			// 添加天赋属性
			GiftLevelTemplate giftLevelTemplate = templateManager
					.getLevelTemplate(giftId, oldLevel + 1);
			GiftProperty property = new GiftProperty();
			property.setProperty(giftLevelTemplate.getPropertyId());
			property.setPropertyValue(giftLevelTemplate.getPropertyValue());
			property.setPropertyValueType(giftLevelTemplate.getAmendMethod());
			giftPropertyMap.put(property.getProperty(), property);
			// 修正属性加成
			upgradeGiftAmendProperty(giftId, oldLevel);
			// 返回消息
			GCUpdateGiftState gcMsg = new GCUpdateGiftState();
			gcMsg.setChangedGift(changedInfoList.toArray(new GiftInfo[0]));
			gcMsg.setGiftAttributes(getGiftAttributes());
			gcMsg.setGiftPointRemain(getGiftPointRemain());
			human.sendMessage(gcMsg);
			// 发送事件
			human.getEventBus().fireEvent(new GiftUpgradeEvent());
		}

	}

	/**
	 * 将天赋状态置为未激活
	 */
	private void setGiftStateUnactive(int giftId) {
		giftLevelMap.put(giftId, 0);
		caches.addUpdate(giftId, convertToEntity(giftId, 0));
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色升级，满足条件的天赋置为未激活
		for (Integer giftId : templateManager.getGiftTemplates().keySet()) {
			if (canLightGift(giftId)) {
				setGiftStateUnactive(giftId);
			}
		}
	}

	/**
	 * 战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		for (HumanGift.Builder builder : humanEntity.getBuilder()
				.getGiftBuilderList()) {
			int giftId = builder.getGift().getGiftId();
			int giftLevel = builder.getGift().getLevel();
			GiftLevelTemplate template = templateManager.getLevelTemplate(
					giftId, giftLevel);
			if (template != null) {
				propertyManager.amendProperty(human,
						AmendMethod.valueOf(template.getAmendMethod()),
						template.getPropertyId(), template.getPropertyValue(),
						PropertyLogReason.GIFT_ACTIVE, "");
			}
		}
	}

	/**
	 * 天赋升级后属性加成
	 */
	private void upgradeGiftAmendProperty(int giftId, int oldLevel) {
		GiftLevelTemplate upTemplate = templateManager.getLevelTemplate(giftId,
				oldLevel + 1);
		AmendMethod amendType = AmendMethod
				.valueOf(upTemplate.getAmendMethod());
		int propertyId = upTemplate.getPropertyId();
		int propertyValue = upTemplate.getPropertyValue();
		// 要减掉上个级别的
		GiftLevelTemplate oldTemplate = templateManager.getLevelTemplate(
				giftId, oldLevel);
		if (oldTemplate != null) {
			propertyValue -= oldTemplate.getPropertyValue();
		}
		human.getPropertyManager().amendProperty(human, amendType, propertyId,
				propertyValue, PropertyLogReason.TITLE_UP, "");
	}
}
