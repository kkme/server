package com.hifun.soul.gameserver.bag.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.AuraLogReason;
import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.LogReasons.HoroscopeLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.LogReasons.SkillPointLogReason;
import com.hifun.soul.common.LogReasons.StarSoulLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanItemEntity;
import com.hifun.soul.gameserver.bag.Bag;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.MainBag;
import com.hifun.soul.gameserver.bag.msg.GCBagUpdate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.currency.manager.IWallet;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.GCItemQuickBuyInfo;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.item.template.SpreeItemInfo;
import com.hifun.soul.gameserver.item.template.SpreeTemplate;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;

/**
 * 主背包管理类
 * 
 * @author magicstone
 * 
 */
public class HumanBagManager implements IHumanPersistenceManager, ICachableComponent, ILoginManager,
		IHumanPropertiesLoadForBattle {
	/** 整理背包时间间隔 */
	private static final long TIDY_BAG_TIME_SPAN = 4 * TimeUtils.SECOND;
	private static Logger logger = Loggers.BAG_LOGGER;
	private MainBag mainBag;
	private Human human;
	private long lastTidyBagTime = 0;
	/** 物品实体缓存 */
	protected CacheEntry<String, HumanItemEntity> itemCaches = new CacheEntry<String, HumanItemEntity>();

	public HumanBagManager(Human theHuman) {
		human = theHuman;
		mainBag = new MainBag(human);
		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerLoginManager(this);
	}

	/**
	 * 放入物品
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param bagType
	 *            背包类型
	 * @param item
	 * @return
	 */
	public boolean putItem(BagType bagType, Item item, ItemLogReason reason, String param) {
		if (item.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()) {
			useVirtualSpree(item, true);
			return true;
		}
		boolean result = this.getBag(bagType).putItem(item, true, reason, param);
		if (result) {
			human.sendImportantMessage(LangConstants.ITEM_OBTAIN, item.getOverlapNum(), item.getName());
		}
		return result;
	}

	/**
	 * 放入物品,自动放入空闲背包格中
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param bagType
	 *            背包类型
	 * @param item
	 *            物品
	 * @param isNotifyObtainItem
	 *            是否通知客户端获得物品
	 * @return
	 */
	public boolean putItem(BagType bagType, Item item, boolean isNotifyObtainItem, ItemLogReason reason, String param) {
		if (item.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()) {
			useVirtualSpree(item, isNotifyObtainItem);
			return true;
		}
		boolean result = this.getBag(bagType).putItem(item, true, reason, param);
		if (result && isNotifyObtainItem) {
			human.sendImportantMessage(LangConstants.ITEM_OBTAIN, item.getOverlapNum(), item.getName());
		}
		return result;
	}

	/**
	 * 放入物品,自动放入空闲背包格中
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param bagType
	 * @param items
	 * @param num
	 *            放入的物品数
	 * @return
	 */
	public boolean putItems(BagType bagType, int itemId, int num, ItemLogReason reason, String param) {
		return putItems(bagType, itemId, num, true, reason, param);
	}

	/**
	 * 放入物品,自动放入空闲背包格中
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param bagType
	 * @param items
	 * @param num
	 *            放入的物品数
	 * @return
	 */
	public boolean putItems(BagType bagType, int itemId, int num, boolean isNotifyObtainItem, ItemLogReason reason,
			String param) {
		boolean isNotifyUpdate = true;
		Item item = ItemFactory.creatNewItem(human, itemId);
		if (item == null) {
			return false;
		}
		if (item.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()) {
			useVirtualSpree(item, isNotifyObtainItem);
			return true;
		}
		Bag bag = this.getBag(bagType);
		if (!item.isOverlapable()) {
			if (bag.getFreeSize() >= num) {
				for (int i = 0; i < num; i++) {
					item = ItemFactory.creatNewItem(human, itemId);
					bag.putItem(item, isNotifyUpdate, reason, param);
				}
			} else {
				human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
				return false;
			}
		} else {
			item.setOverlapNum(num);
			if (bag.canContain(item)) {
				bag.putItem(item, isNotifyUpdate, reason, param);
			} else {
				return false;
			}
		}
		if (isNotifyObtainItem) {
			human.sendImportantMessage(LangConstants.ITEM_OBTAIN, num, item.getName());
		}
		return true;
	}

	/**
	 * 放入物品到指定的背包格
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param bagType
	 *            背包类型
	 * @param item
	 * @param index
	 *            背包格索引
	 * @return
	 */
	public boolean putItem(BagType bagType, Item item, int index, ItemLogReason reason, String param) {
		return putItem(bagType, item, index, false, reason, param);
	}

	/**
	 * 放入物品到指定的背包格
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param bagType
	 *            背包类型
	 * @param item
	 * @param index
	 *            背包格索引
	 * @return
	 */
	public boolean putItem(BagType bagType, Item item, int index, boolean isNotify, ItemLogReason reason, String param) {
		if (item.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()) {
			useVirtualSpree(item, isNotify);
			return true;
		}
		this.getBag(bagType).putItem(item, index, isNotify, reason, param);
		if (isNotify) {
			human.sendImportantMessage(LangConstants.ITEM_OBTAIN, item.getOverlapNum(), item.getName());
		}
		return true;
	}

	/**
	 * 根据物品uuid获取物品
	 * 
	 * @param itemId
	 * @return
	 */
	public Item getItem(BagType bagType, String itemUuid) {
		return getBag(bagType).getItemByUuid(itemUuid);
	}

	/**
	 * 根据物品Id获取指定数量的物品
	 * 
	 * @param itemId
	 * @return
	 */
	public List<Item> getItemsFromMainBag(int itemId, int count) {
		return mainBag.getItemsByItemId(itemId, count);
	}

	/**
	 * 根据背包索引获取物品
	 * 
	 * @param bagType
	 *            背包类型
	 * @param index
	 *            物品所在背包中的位置索引
	 * @return
	 */
	public Item getItem(BagType bagType, int index) {
		return this.getBag(bagType).getItem(index);
	}

	/**
	 * 获取背包中的所有物品列表
	 * 
	 * @param bagType
	 * @return
	 */
	public List<Item> getItems(BagType bagType) {
		List<Item> items = null;
		items = this.getBag(bagType).getItems();
		return items;
	}

	/**
	 * 获取背包中指定背包页的所有物品列表
	 * 
	 * @param bagType
	 *            背包类型
	 * @param pageIndex
	 *            背包页索引,第一页为0
	 * @return 当背包页超出范围时返回null
	 */
	public List<Item> getItems(BagType bagType, int pageIndex) {
		if (pageIndex < 0) {
			return null;
		}
		int pageSize = this.getBagPageSize(bagType);
		int fromIndex = pageIndex * pageSize;
		Bag bag = this.getBag(bagType);
		if (SharedConstants.MAIN_BAG_MAX_SIZE <= fromIndex) {
			logger.error("请求的背包页超出范围。human:" + human.getHumanGuid() + "; bagSize:" + bag.getSize() + "; requestIndex:"
					+ fromIndex);
			return null;
		}
		List<Item> items = bag.getItems(fromIndex, pageSize);
		return items;
	}

	public int getItemCount(int itemId) {
		return mainBag.getItemCount(itemId);
	}

	/**
	 * 清空背包
	 * 
	 * @param bagType
	 *            背包类型
	 */
	public void clear(BagType bagType, ItemLogReason reason, String param) {
		this.getBag(bagType).clear(reason, param);
	}

	/**
	 * 整理背包
	 * 
	 * @param bagType
	 *            背包类型
	 */
	public void tidy(BagType bagType) {
		switch (bagType) {
		case MAIN_BAG:
			long now = GameServerAssist.getSystemTimeService().now();
			if (lastTidyBagTime == 0 || (lastTidyBagTime > 0 && now - TIDY_BAG_TIME_SPAN > lastTidyBagTime)) {
				mainBag.tidy();
				mainBag.sendBagUpdateMessage();
				lastTidyBagTime = now;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 主背包扩容
	 * 
	 * @param level
	 *            所提升的等级
	 * @return
	 */
	public boolean upgradeMainBag(int level) {
		try {
			if (level <= 0) {
				return false;
			}
			if (mainBag.getSize() + level * SharedConstants.MAIN_BAG_UPDATE_LEVEL_SIZE > SharedConstants.MAIN_BAG_MAX_SIZE) {
				// 超出最大容量
				return false;
			}
			int currentLevel = (mainBag.getSize() - SharedConstants.INIT_HUMAN_BAG_SIZE)
					/ SharedConstants.MAIN_BAG_UPDATE_LEVEL_SIZE;
			int costNum = GameServerAssist.getBagTemplateManager().getMainBagUpgradePrice(currentLevel, level);
			if (!human.getWallet().isEnough(CurrencyType.CRYSTAL, costNum)) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.CRYSTAL.getDesc());
				return false;
			}
			boolean costResult = human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
					MoneyLogReason.MAIN_BAG_UPGRADE, "");
			if (costResult == false) {
				return false;
			}
			if (mainBag.addBagUnit(level * SharedConstants.MAIN_BAG_UPDATE_LEVEL_SIZE)) {
				human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
						.setPropertyValue(HumanIntProperty.BAG_SIZE, mainBag.getSize());
			} else {
				return false;
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 查询升级背包需要的价格
	 * 
	 * @param bagType
	 * @param upgradeLevel
	 * @return
	 */
	public int queryUpgradePrice(BagType bagType, int upgradeLevel) {
		// 目前只实现主背包，若还有其他背包可以升级扩容，可以通过策略来实现
		int currentLevel = (mainBag.getSize() - SharedConstants.INIT_HUMAN_BAG_SIZE)
				/ SharedConstants.MAIN_BAG_UPDATE_LEVEL_SIZE;
		return GameServerAssist.getBagTemplateManager().getMainBagUpgradePrice(currentLevel, upgradeLevel);
	}

	/**
	 * 移除指定背个单元的所有物品
	 * 
	 * @param bagType
	 *            背包类型
	 * @param index
	 *            物品所在背包中的位置索引
	 * @return
	 */
	public boolean removeItem(BagType bagType, int index, ItemLogReason reason, String param) {
		try {
			Bag bag = this.getBag(bagType);
			if (index >= bag.getSize()) {
				return false;
			}
			if (BagType.MAIN_BAG == bagType) {
				Item item = bag.getItem(index);
				if (item instanceof EquipItem) {
					EquipItem equipment = (EquipItem) item;
					if (equipment.isEquiped()) {
						// 装备背包移除
						mainBag.takeOffEquipItem(index, false, PropertyLogReason.EQUIP_ONOFF, "");
					}
				}
			}
			bag.removeItem(index, true, reason, param);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 从指定的背包位置移除一定数量的物品
	 * 
	 * @param bagType
	 *            背包类型
	 * @param index
	 *            背包位置索引
	 * @param removeNum
	 *            移除数量
	 * @return
	 */
	public boolean removeItem(BagType bagType, int index, int removeNum, ItemLogReason reason, String param) {
		if (index < 0) {
			throw new IllegalArgumentException("target index can not smaller then 0!");
		}
		try {
			if (BagType.MAIN_BAG == bagType) {
				Item item = getBag(bagType).getItem(index);
				if (item instanceof EquipItem) {
					EquipItem equipment = (EquipItem) item;
					if (equipment.isEquiped()) {
						// 装备背包移除
						mainBag.takeOffEquipItem(index, false, PropertyLogReason.EQUIP_ONOFF, "");
					}
				}
			}
			this.getBag(bagType).removeItem(index, removeNum, true, reason, param);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 根据物品模板Id从主背包中移除指定数量物品
	 * 
	 * <pre>
	 * 移除可叠加的物品用该方法
	 * </pre>
	 * 
	 * @param itemId
	 *            模板id
	 * @param removeNum
	 *            移除数量
	 * @return
	 */
	public boolean removeItemByItemId(int itemId, int removeNum, ItemLogReason reason, String param) {
		return mainBag.removeItemByItemId(itemId, removeNum, true, reason, param);
	}

	/**
	 * 根据uuid从主背包中指定物品
	 * 
	 * <pre>
	 * 移除不可叠加的物品用该方法
	 * </pre>
	 * 
	 * @param itemUuid
	 *            物品唯一id
	 * @return @
	 */
	public boolean removeItemByUuid(String itemUuid, ItemLogReason reason, String param) {
		Item item = mainBag.getItemByUuid(itemUuid);
		if (item instanceof EquipItem) {
			EquipItem equipment = (EquipItem) item;
			if (equipment.isEquiped()) {
				// 装备背包移除
				mainBag.takeOffEquipItem(equipment.getBagIndex(), false, PropertyLogReason.EQUIP_ONOFF, "");
			}
		}
		return mainBag.removeItemByUuid(itemUuid, true, reason, param);
	}

	/**
	 * 背包中是否有足够的物品
	 * 
	 * @param item
	 *            物品对象，必须有itemid和叠加数量
	 * @return
	 */
	public boolean isEnough(Item item) {
		return mainBag.isEnough(item.getItemId(), item.getOverlapNum());
	}

	/**
	 * 背包是否能放下指定的物品
	 * 
	 * @param item
	 */
	public boolean canContain(Item item) {
		return mainBag.canContain(item);

	}

	/**
	 * 背包是否能放下指定的物品
	 * 
	 * @param item
	 */
	public boolean canContain(int itemId, int num) {
		Item item = ItemFactory.creatNewItem(human, itemId);
		if (item == null) {
			return false;
		}
		if (!item.isOverlapable()) {
			return this.mainBag.getFreeSize() >= num;
		} else {
			item.setOverlapNum(num);
			return this.mainBag.canContain(item);
		}
	}

	/**
	 * 是否还有空闲的背包格
	 * 
	 * @param bagType
	 *            背包类型
	 * @return
	 */
	public boolean isFull(BagType bagType) {
		return this.getBag(bagType).isFull();
	}

	/**
	 * 移动主背包中的物品
	 * 
	 * @param fromBagType
	 * @param fromBagIndex
	 * @param toBagType
	 * @param toBagIndex
	 * @return
	 */
	public boolean moveItem(int fromBagIndex, int toBagIndex, ItemLogReason reason, String param) {
		mainBag.moveItem(fromBagIndex, toBagIndex, reason, param);
		return true;
	}

	/**
	 * 根据背包类型返回对应的背包
	 * 
	 * @param bagType
	 * @return
	 */
	protected Bag getBag(BagType bagType) {
		Bag bag = null;
		switch (bagType) {
		case MAIN_BAG:
			bag = mainBag;
			break;
		}
		return bag;
	}

	/**
	 * 获取背包容量大小
	 * 
	 * @param bagType
	 *            背包类型
	 * @return
	 */
	public int getBagSize(BagType bagType) {
		int size = 0;
		Bag bag = getBag(bagType);
		if (bag != null) {
			size = bag.getSize();
		}
		return size;

	}

	/**
	 * 往主背包放入物品
	 * 
	 * <pre>
	 * 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * </pre>
	 * 
	 * @param itemTemplate
	 * @param count
	 * @return
	 */
	public boolean giveItem(ItemTemplate itemTemplate, int count, ItemLogReason reason, String param) {
		int itemId = itemTemplate.getId();
		return putItems(BagType.MAIN_BAG, itemId, count, reason, param);
	}

	/**
	 * 按页发送更新背包消息
	 * 
	 * @param bagType
	 * @param pageIndex
	 */
	public void sendBagUpdateMessage(BagType bagType, int pageIndex) {
		List<Item> itemList = this.getItems(bagType, pageIndex);
		if (itemList == null) {
			return;
		}
		CommonItem[] items = new CommonItem[itemList.size()];
		for (int i = 0; i < itemList.size(); i++) {
			items[i] = CommonItemBuilder.converToCommonItem(itemList.get(i));
		}
		GCBagUpdate bagUpdate = new GCBagUpdate();
		bagUpdate.setItems(items);
		human.sendMessage(bagUpdate);
	}

	/**
	 * 向客户端发送背包单元格的更新。当背包单元格物品不为空时，发送更新消息，否则发送移除消息
	 * 
	 * @param bagType
	 * @param bagIndex
	 */
	public void updateItem(BagType bagType, int bagIndex) {
		Bag bag = getBag(bagType);
		if (bagIndex >= bag.getSize()) {
			throw new IllegalArgumentException("参数错误，bagIndex超出背包大小范围。");
		}
		bag.updateItem(bagIndex);
		bag.sendItemUpdateMessage(bagIndex);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> resultList = this.mainBag.getUpdateEntities();
		resultList.addAll(itemCaches.getAllUpdateData());
		return resultList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> resultList = this.mainBag.getDeleteEntities();
		resultList.addAll(itemCaches.getAllDeleteData());
		return resultList;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		int mainBagSize = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.BAG_SIZE);
		this.mainBag.init(mainBagSize);
		boolean itemConfigChange = false;
		// 初始化背包
		for (HumanItem itemBuilder : humanEntity.getBuilder().getItemList()) {
			int hasCount = itemBuilder.getItem().getItemCount();
			BagType bagType = BagType.indexOf(itemBuilder.getBagType());
			Item item = ItemFactory.convertHumanItemToItem(human, itemBuilder);
			if (item == null) {
				return;
			}
			if (item.getMaxOverlap() < hasCount) {
				itemConfigChange = true;
				int maxOverlap = item.getMaxOverlap();
				int n = (hasCount - 1) / maxOverlap + 1;
				int s = hasCount % maxOverlap;
				for (int i = 0; i < n - 1; i++) {
					Item newItem = ItemFactory.creatNewItem(human, item.getItemId());
					newItem.setOverlapNum(maxOverlap);
					this.putItem(bagType, newItem, false, ItemLogReason.LOAD, "");
				}
				Item newItem = ItemFactory.creatNewItem(human, item.getItemId());
				newItem.setOverlapNum(s);
				this.putItem(bagType, item, false, null, "");
				continue;
			}
			if (itemConfigChange) {
				if (this.getBag(bagType).isFull()) {
					logger.error("配置表中可叠加数的变动导致玩家物品丢失，角色id:" + human.getHumanGuid() + "丢失物品id：" + item.getItemId());
				}
				this.putItem(bagType, item, false, null, "");
			} else {
				this.putItem(bagType, item, itemBuilder.getItem().getIndex(), false, null, "");
			}
			if (item.isEquip()) {
				EquipItemFeature euipFeature = (EquipItemFeature) item.getFeature();
				if (euipFeature.isEquiped()) {
					mainBag.equipItem(item.getBagIndex(), false, null, "");
				}
			}
			if (item.getType() == ItemDetailType.VIRTUAL_SPREE.getIndex()) {
				HumanItemEntity itemEntity = new HumanItemEntity(itemBuilder.toBuilder());
				itemCaches.addDelete(itemEntity.getId(), itemEntity);
			}
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 背包数据持久化
		for (Item item : this.mainBag.getItems()) {
			HumanItem.Builder itemBuilder = ItemFactory.converItemToHumanItem(human.getHumanGuid(), item,
					BagType.MAIN_BAG);
			humanEntity.getBuilder().addItem(itemBuilder);
		}
	}

	/**
	 * 判断能否装备指定装备
	 * 
	 * @param item
	 * @return
	 */
	public boolean canEquip(EquipItem item) {
		int limitLevel = item.getLimitLevel();
		if (human.getLevel() < limitLevel) {
			human.sendWarningMessage(LangConstants.CHARACTER_LEVEL_LIMIT, limitLevel);
			return false;
		}
		Occupation occupation = human.getOccupation();
		int limitO = item.getLimitOccupation();
		if ((limitO | occupation.getType()) != limitO) {
			human.sendWarningMessage(LangConstants.EQUIP_OCCUPATION_NOT_MATCH);
			return false;
		}
		return true;
	}

	/**
	 * 获取空闲的背包格数量
	 * 
	 * @param bagType
	 * @return
	 */
	public int getFreeSize(BagType bagType) {
		Bag bag = this.getBag(bagType);
		if (bag != null) {
			return bag.getFreeSize();
		}
		return 0;
	}

	/**
	 * 获取放置一定数量的某种物品需要占用的背包格子数
	 * 
	 * @param itemId
	 * @param count
	 * @return
	 */
	public int getUnitCountForPlaceItem(int itemId, int count) {
		Item item = ItemFactory.creatNewItem(human, itemId);
		if (item.isOverlapable()) {
			return count / item.getMaxOverlap() + 1;
		} else {
			return count;
		}

	}

	private int getBagPageSize(BagType bagType) {
		switch (bagType) {
		case MAIN_BAG:
			return SharedConstants.MAIN_BAG_PAGE_SIZE;
		default:
			Bag bag = this.getBag(bagType);
			return bag.getSize();
		}
	}

	@Override
	public void onLogin() {
		this.mainBag.sendBagUpdateMessage();
		sendItemQuickBuyInfoMessage();
	}

	/**
	 * 下发可快捷购买的物品
	 */
	private void sendItemQuickBuyInfoMessage() {
		GCItemQuickBuyInfo gcMsg = new GCItemQuickBuyInfo();
		CommonItem[] items = new CommonItem[3];
		items[0] = CommonItemBuilder.genCommonItem(ItemConstantId.GUARD_STONE_ID);
		items[1] = CommonItemBuilder.genCommonItem(ItemConstantId.SIMPLE_FORTUNE_STONE_ID);
		items[2] = CommonItemBuilder.genCommonItem(ItemConstantId.SUPER_FORTUNE_STONE_ID);
		int[] quickBuyItemIds = new int[4];
		quickBuyItemIds[0] = ItemConstantId.RESET_PROP_ITEM_ID;
		quickBuyItemIds[1] = ItemConstantId.RESET_SKILL_POINT_ITEM_ID;
		quickBuyItemIds[2] = ItemConstantId.RESET_GIFT_POINT_ITEM_ID;
		quickBuyItemIds[3] = ItemConstantId.BATTLE_ROBOT_ID;
		gcMsg.setQuickBuyItems(items);
		gcMsg.setQuickBuyItemIds(quickBuyItemIds);
		human.sendMessage(gcMsg);
	}

	/**
	 * 使用虚拟礼包 在向背包中放入虚拟礼包时需要先检查背包是否能容纳礼包中的物品,星运临时背包能否容纳礼包中星运
	 * 
	 * @param item
	 */
	public boolean useVirtualSpree(Item item, boolean isNotifyObtainItem) {
		SpreeTemplate spreeTemplate = GameServerAssist.getSpreeTemplateManager().getSpreeTemplate(item.getItemId());
		// 抽取数量
		int containNum = spreeTemplate.getItemCount();
		// 礼包配置物品数量
		int itemNum = spreeTemplate.getItems().size();
		if (containNum < itemNum){
			itemNum = containNum;
		}
		if (this.getFreeSize(BagType.MAIN_BAG) < itemNum) {
			human.sendErrorMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return false;
		}
		// 星运背包格子是否足够
		if (spreeTemplate.getHoroscopeIdArray().length > 0) {
			if (human.getHumanHoroscopeManager().getEmptyGridSizeOfMainBag() < spreeTemplate.getHoroscopeIdArray().length) {
				human.sendErrorMessage(LangConstants.HOROSCOPE_MAINBAG_EMPTY_SIZE_NOT_ENOUGH);
				return false;
			}
		}
		// 精灵背包格子是否足够
		if (spreeTemplate.getSpriteIdArray().length > 0) {
			if (human.getHumanSpriteManager().getEmptyCellSize() < spreeTemplate.getSpriteIdArray().length) {
				human.sendErrorMessage(LangConstants.SPRITE_BAG_NO_SPACE);
				return false;
			}
		}
		// 权值列表
		List<Integer> weightList = new ArrayList<Integer>();
		// 物品id
		List<Integer> itemList = new ArrayList<Integer>();
		// key：物品id，value：物品对应的数量
		Map<Integer, Integer> keyNumMap = new HashMap<Integer, Integer>();
		// 选取池中的权重都是大于0的
		if (spreeTemplate.getCoinWeight() != 0) {
			weightList.add(spreeTemplate.getCoinWeight());
			itemList.add(1);
			keyNumMap.put(1, spreeTemplate.getCoinNum());
		}
		if (spreeTemplate.getCrystalWeight() != 0) {
			weightList.add(spreeTemplate.getCrystalWeight());
			itemList.add(2);
			keyNumMap.put(2, spreeTemplate.getCrystalNum());
		}
		if (spreeTemplate.getExpWeight() != 0) {
			weightList.add(spreeTemplate.getExpWeight());
			itemList.add(3);
			keyNumMap.put(3, spreeTemplate.getExpNum());
		}
		if (spreeTemplate.getHonourWeight() != 0) {
			weightList.add(spreeTemplate.getHonourWeight());
			itemList.add(4);
			keyNumMap.put(4, spreeTemplate.getHonourNum());
		}
		if (spreeTemplate.getSkillPointWeight() != 0) {
			weightList.add(spreeTemplate.getSkillPointWeight());
			itemList.add(5);
			keyNumMap.put(5, spreeTemplate.getSkillPointNum());
		}
		if (spreeTemplate.getTechPointWeight() != 0) {
			weightList.add(spreeTemplate.getTechPointWeight());
			itemList.add(6);
			keyNumMap.put(6, spreeTemplate.getTechPointNum());
		}
		if (spreeTemplate.getHoroscopeIdArray().length > 0) {
			weightList.add(spreeTemplate.getHoroscopeWeight());
			itemList.add(7);
		}
		if (spreeTemplate.getSpriteIdArray().length > 0) {
			weightList.add(spreeTemplate.getSpriteWeight());
			itemList.add(8);
		}
		if (spreeTemplate.getTrainCoinWeight() != 0) {
			weightList.add(spreeTemplate.getTrainCoinWeight());
			itemList.add(9);
			keyNumMap.put(9, spreeTemplate.getTrainCoinNum());
		}
		if (spreeTemplate.getAuraWeight() != 0) {
			weightList.add(spreeTemplate.getAuraWeight());
			itemList.add(10);
			keyNumMap.put(10, spreeTemplate.getAuraNum());
		}
		if (spreeTemplate.getStarSoulWeight() != 0) {
			weightList.add(spreeTemplate.getStarSoulWeight());
			itemList.add(11);
			keyNumMap.put(11, spreeTemplate.getStarSoulNum());
		}
		for (SpreeItemInfo spreeItem : spreeTemplate.getItems()) {
			if (spreeItem.getWeight() != 0) {
				weightList.add(spreeItem.getWeight());
				itemList.add(spreeItem.getItemid());
				keyNumMap.put(spreeItem.getItemid(), spreeItem.getNum());
			}
		}
		if (containNum > weightList.size()) {
			containNum = weightList.size();
		}

		int[] indexs = null;
		if (containNum == weightList.size()) {
			// 全选
			indexs = new int[containNum];
			for (int i = 0; i < containNum; i++) {
				indexs[i] = i;
			}
		} else {
			// 随机选取不重复的物品
			indexs = MathUtils.getRandomUniqueIndex(weightList, containNum);
		}
		IWallet wallet = human.getWallet();
		for (int i : indexs) {
			// 金币
			if (1 == itemList.get(i)) {
				wallet.addMoney(CurrencyType.COIN, keyNumMap.get(1), isNotifyObtainItem, MoneyLogReason.SPREE_USE, "");
			}
			// 魔晶
			else if (2 == itemList.get(i)) {
				wallet.addMoney(CurrencyType.CRYSTAL, keyNumMap.get(2), isNotifyObtainItem, MoneyLogReason.SPREE_USE,
						"");
			}
			// 经验
			else if (3 == itemList.get(i)) {
				human.addExperience(keyNumMap.get(3), isNotifyObtainItem, ExperienceLogReason.ITEM_USE_ADD_EXP, "");
			}
			// 荣誉
			else if (4 == itemList.get(i)) {
				human.addArenaHonor(keyNumMap.get(4), isNotifyObtainItem, HonourLogReason.ITEM_USE_ADD_HONOUR, "");
			}
			// 技能点
			else if (5 == itemList.get(i)) {
				human.addSkillPoint(keyNumMap.get(5), isNotifyObtainItem, SkillPointLogReason.ITEM_USE_ADD_SKILL_POINT,
						"");
			}
			// 科技点
			else if (6 == itemList.get(i)) {
				human.getHumanTechnologyManager().addTechnologyPoints(keyNumMap.get(6), isNotifyObtainItem,
						TechPointLogReason.ITEM_USE_ADD_TECH_POINT, "");
			}
			// 星运
			else if (7 == itemList.get(i)) {
				int horoIndex = 0;
				for (int horosId : spreeTemplate.getHoroscopeIdArray()) {
					HoroscopeInfo horoscopeInfo = GameServerAssist.getHoroscopeTemplateManager().genHoroscopeInfo(
							horosId, HoroscopeBagType.MAIN_BAG);
					for (int horoNumIndex = 0; horoNumIndex < spreeTemplate.getHoroscopeNumArray()[horoIndex]; horoNumIndex++) {
						human.getHumanHoroscopeManager().addHoroscopeInfo(HoroscopeBagType.MAIN_BAG, horoscopeInfo,
								HoroscopeLogReason.GET_FROM_VIRTURL_SPREE, "");
					}
					horoIndex++;
				}
			}
			// 精灵
			else if (8 == itemList.get(i)) {
				int spriteIndex = 0;
				for (int spriteId : spreeTemplate.getSpriteIdArray()) {
					SpritePubInfo spritePubInfo = GameServerAssist.getSpriteTemplateManager().getSpritePubInfoById(spriteId);
					for (int spriteNumIndex = 0; spriteNumIndex < spreeTemplate.getSpriteNumArray()[spriteIndex]; spriteNumIndex++) {
						human.getHumanSpriteManager().putToBag(spritePubInfo, true);
					}
					spriteIndex++;
				}
			}
			// 培养币
			else if (9 == itemList.get(i)) {
				human.addTrainCoin(keyNumMap.get(9), isNotifyObtainItem, TrainCoinLogReason.ITEM_USE_ADD_TRAIN_COIN, "");
			}
			// 灵气值
			else if (10 == itemList.get(i)) {
				human.addAura(keyNumMap.get(10), isNotifyObtainItem, AuraLogReason.ITEM_USE_ADD_AURA, "");
			}
			// 星魂
			else if (11 == itemList.get(i)) {
				human.addStarSoul(keyNumMap.get(11), isNotifyObtainItem, StarSoulLogReason.ITEM_USE_ADD_STAR_SOUL, "");
			}
			else {
				int itemId = itemList.get(i);
				putItems(BagType.MAIN_BAG, itemId, keyNumMap.get(itemId), isNotifyObtainItem, ItemLogReason.SPREE_USE,
						"");
			}
		}
		return true;
	}

	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity, HumanPropertyManager propertyManager) {
		for (HumanItem itemBuilder : humanEntity.getBuilder().getItemList()) {
			if (itemBuilder.getItem().getIsEquiped()) {
				Item item = ItemFactory.convertHumanItemToItem(human, itemBuilder);
				if (item == null) {
					return;
				}
				if (item.isEquip()) {
					EquipItemFeature euipFeature = (EquipItemFeature) item.getFeature();
					if (euipFeature.isEquiped()) {
						euipFeature.amendBattleProperty(propertyManager, null, "");
					}
				}
			}
		}

	}

	/**
	 * 穿装备
	 * 
	 * @param index
	 * @return
	 */
	public boolean equipItem(int index) {
		return mainBag.equipItem(index, true, PropertyLogReason.EQUIP_ONOFF, "");
	}

	/**
	 * 脱装备
	 * 
	 * @param index
	 * @return
	 */
	public boolean takeOffEquipItem(int index) {
		return mainBag.takeOffEquipItem(index, true, PropertyLogReason.EQUIP_ONOFF, "");
	}

	/**
	 * 获取已装备的物品
	 * 
	 * @return
	 */
	public List<Item> getEquipedItems() {
		return mainBag.getEquipedItems();
	}

	/**
	 * 获取某个装备位的物品
	 * 
	 * @param position
	 * @return
	 */
	public Item getEquipItem(int position) {
		return mainBag.getEquipItem(position);
	}

	/**
	 * 在指定背包中搜寻物品,若在某个背包单元格中搜到该物品则停止搜寻，返回该单元格的物品
	 * 
	 * @param itemId
	 * @param bagType
	 * @return
	 */
	public Item searchItem(int itemId, BagType bagType) {
		if (bagType == BagType.MAIN_BAG) {
			return mainBag.searchItem(itemId);
		}
		return null;
	}

	/**
	 * 使用属性重置药水
	 * 
	 * @param human
	 * @param item
	 */
	public void usePropertyPointResetItem(BagType bagType, int bagIndex) {
		Item item = this.getItem(bagType, bagIndex);
		if (item == null || item.getItemId() != ItemConstantId.RESET_PROP_ITEM_ID) {
			return;
		}
		int totlePoint = human.getUnDistributePropertyPoint() + human.getSystemPower() + human.getSystemAgile()
				+ human.getSystemIntelligence() + human.getSystemSpirit() + human.getSystemtStamina();
		human.setSystemAgile(0);
		human.setSystemIntelligence(0);
		human.setSystemPower(0);
		human.setSystemSpirit(0);
		human.setSystemStamina(0);
		human.setUnDistributePropertyPoint(totlePoint);
		human.getHumanPropertiesManager().recalculateInitProperties(human);
		removeItem(bagType, bagIndex, 1, ItemLogReason.COMSUME_USE, "");
	}
	
	/**
	 * 装备位强化，属性加成
	 */
	public void admendPropertyByUpgradeEquipBit(int equipBitType){
		Item equipedItem = this.getEquipItem(equipBitType);
		if (equipedItem != null) {
			((EquipItemFeature)equipedItem.getFeature())
				.amendHumanPropertyByGodsoul(human.getHumanPropertiesManager(),PropertyLogReason.GODSOUL_UP,"");	
		}
	}
}
