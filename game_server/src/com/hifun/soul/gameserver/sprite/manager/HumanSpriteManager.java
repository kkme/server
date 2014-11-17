package com.hifun.soul.gameserver.sprite.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteBagCellEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteBuffEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteSlotEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.SpriteUpgradeEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.sprite.SpriteType;
import com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteInfo;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.msg.GCActivateBuff;
import com.hifun.soul.gameserver.sprite.msg.GCBuySpriteCell;
import com.hifun.soul.gameserver.sprite.msg.GCDropSprite;
import com.hifun.soul.gameserver.sprite.msg.GCSpriteEquipState;
import com.hifun.soul.gameserver.sprite.msg.GCUpdateSprite;
import com.hifun.soul.gameserver.sprite.template.SpriteLevelupTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;
import com.hifun.soul.proto.data.entity.Entity.HumanSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBagCell;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBuff;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteSlot;

/**
 * 角色精灵管理器;
 * 
 * @author crazyjohn
 * 
 */
public class HumanSpriteManager implements ICachableComponent,
		IHumanPersistenceManager, IHumanPropertiesLoadForBattle {
	private Human owner;
	/** 玩家的精灵集合 */
	private List<SpriteInfo> spriteList = new ArrayList<SpriteInfo>();
	/** 缓存的玩家精灵集合 */
	private CacheEntry<String, SpriteInfo> spriteCache = new CacheEntry<String, SpriteInfo>();
	/** 精灵背包格子信息 */
	private List<SpriteBagCellInfo> cellList = new ArrayList<SpriteBagCellInfo>();
	/** 缓存的玩家精灵背包格子集合 */
	private CacheEntry<Integer, SpriteBagCellInfo> cellCache = new CacheEntry<Integer, SpriteBagCellInfo>();
	/** buff信息 */
	private List<SpriteBuffInfo> buffList;
	/** 缓存的玩家精灵buff集合 */
	private CacheEntry<Integer, SpriteBuffInfo> buffCache = new CacheEntry<Integer, SpriteBuffInfo>();
	/** 模版管理器 */
	private SpriteTemplateManager templateManager;
	/** 装备位列表信息 */
	private List<SpriteEuqipSlotInfo> slotList = new ArrayList<SpriteEuqipSlotInfo>();
	/** 缓存的玩家精灵装备位集合 */
	private CacheEntry<Integer, SpriteEuqipSlotInfo> slotCache = new CacheEntry<Integer, SpriteEuqipSlotInfo>();
	/** 背包最大大小-18 */
	private static final int MAX_BAG_OPEN_SIZE = 18;
	/** 默认装备位大小-5 */
	private static final int DEFAULT_SLOT_OPEN_SIZE = 5;
	/** + */
	private static final int ADD = 1;
	/** - */
	private static final int REDUCE = -1;

	public HumanSpriteManager(Human human) {
		this.owner = human;
		templateManager = GameServerAssist.getSpriteTemplateManager();
		this.owner.registerCachableManager(this);
		this.owner.registerPersistenceManager(this);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		// 精灵数据
		for (SpriteInfo each : this.spriteCache.getAllUpdateData()) {
			HumanSpriteEntity entity = new HumanSpriteEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getSpriteBuilder()
					.setSpriteId(each.getSpriteId());
			entity.getBuilder().getSpriteBuilder()
					.setSpriteUUID(each.getUuid());
			entity.getBuilder().getSpriteBuilder().setLevel(each.getLevel());
			entity.getBuilder().getSpriteBuilder()
					.setEquiped(each.getIsEquip());
			updateList.add(entity);
		}
		// 精灵格子数据
		for (SpriteBagCellInfo each : this.cellCache.getAllUpdateData()) {
			HumanSpriteBagCellEntity entity = new HumanSpriteBagCellEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getCellBuilder().setEquiped(each.getEquip());
			entity.getBuilder().getCellBuilder().setIndex(each.getIndex());
			if (each.getEquip()) {
				entity.getBuilder().getCellBuilder()
						.setSpriteUUID(each.getSpriteUUID());
			}
			updateList.add(entity);
		}
		// 精灵装备位数据
		for (SpriteEuqipSlotInfo each : this.slotCache.getAllUpdateData()) {
			HumanSpriteSlotEntity entity = new HumanSpriteSlotEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getSlotBuilder().setIndex(each.getIndex());
			entity.getBuilder().getSlotBuilder().setEquiped(each.getEquip());
			if (each.getEquip()) {
				entity.getBuilder().getSlotBuilder()
						.setSpriteUUID(each.getSpriteUUID());
			}
			updateList.add(entity);
		}
		// 精灵buff数据
		for (SpriteBuffInfo each : this.buffCache.getAllUpdateData()) {
			HumanSpriteBuffEntity entity = new HumanSpriteBuffEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getBuffBuilder()
					.setActivated(each.getActivated());
			entity.getBuilder().getBuffBuilder().setBuffId(each.getBuffId());
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		// 精灵数据
		for (SpriteInfo each : this.spriteCache.getAllDeleteData()) {
			HumanSpriteEntity entity = new HumanSpriteEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getSpriteBuilder()
					.setSpriteId(each.getSpriteId());
			entity.getBuilder().getSpriteBuilder()
					.setSpriteUUID(each.getUuid());
			entity.getBuilder().getSpriteBuilder().setLevel(each.getLevel());
			entity.getBuilder().getSpriteBuilder()
					.setEquiped(each.getIsEquip());
			deleteList.add(entity);
		}
		// 精灵格子数据
		for (SpriteBagCellInfo each : this.cellCache.getAllDeleteData()) {
			HumanSpriteBagCellEntity entity = new HumanSpriteBagCellEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getCellBuilder().setEquiped(each.getEquip());
			entity.getBuilder().getCellBuilder().setIndex(each.getIndex());
			if (each.getEquip()) {
				entity.getBuilder().getCellBuilder()
						.setSpriteUUID(each.getSpriteUUID());
			}
			deleteList.add(entity);
		}
		// 精灵装备位数据
		for (SpriteEuqipSlotInfo each : this.slotCache.getAllDeleteData()) {
			HumanSpriteSlotEntity entity = new HumanSpriteSlotEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getSlotBuilder().setIndex(each.getIndex());
			entity.getBuilder().getSlotBuilder().setEquiped(each.getEquip());
			if (each.getEquip()) {
				entity.getBuilder().getSlotBuilder()
						.setSpriteUUID(each.getSpriteUUID());
			}
			deleteList.add(entity);
		}
		// 精灵buff数据
		for (SpriteBuffInfo each : this.buffCache.getAllDeleteData()) {
			HumanSpriteBuffEntity entity = new HumanSpriteBuffEntity();
			entity.getBuilder().setHumanGuid(this.owner.getHumanGuid());
			entity.getBuilder().getBuffBuilder()
					.setActivated(each.getActivated());
			entity.getBuilder().getBuffBuilder().setBuffId(each.getBuffId());
			deleteList.add(entity);
		}
		return deleteList;
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 存储精灵数据
		for (SpriteInfo eachInfo : this.spriteList) {
			HumanSprite.Builder eachBuilder = HumanSprite.newBuilder();
			eachBuilder.getSpriteBuilder().setSpriteId(eachInfo.getSpriteId());
			eachBuilder.setHumanGuid(humanEntity.getId());
			eachBuilder.getSpriteBuilder().setEquiped(eachInfo.getIsEquip());
			eachBuilder.getSpriteBuilder().setLevel(eachInfo.getLevel());
			eachBuilder.getSpriteBuilder().setSpriteUUID(eachInfo.getUuid());
			humanEntity.getBuilder().addSprite(eachBuilder);
		}
		// 存储精灵buff数据
		for (SpriteBuffInfo eachInfo : this.buffList) {
			HumanSpriteBuff.Builder eachBuilder = HumanSpriteBuff.newBuilder();
			eachBuilder.getBuffBuilder().setActivated(eachInfo.getActivated());
			eachBuilder.setHumanGuid(humanEntity.getId());
			eachBuilder.getBuffBuilder().setBuffId(eachInfo.getBuffId());
			humanEntity.getBuilder().addBuff(eachBuilder);
		}
		// 存储精灵背包格子数据
		for (SpriteBagCellInfo eachInfo : this.cellList) {
			// 不存储未开启的格子
			if (!eachInfo.getOpen()) {
				continue;
			}
			HumanSpriteBagCell.Builder eachBuilder = HumanSpriteBagCell
					.newBuilder();
			eachBuilder.getCellBuilder().setEquiped(eachInfo.getEquip());
			eachBuilder.setHumanGuid(humanEntity.getId());
			eachBuilder.getCellBuilder().setIndex(eachInfo.getIndex());
			if (eachBuilder.getCellBuilder().getEquiped()) {
				eachBuilder.getCellBuilder().setSpriteUUID(
						eachInfo.getSpriteUUID());
			}
			humanEntity.getBuilder().addCell(eachBuilder);
		}
		// 存储精灵装备位数据
		for (SpriteEuqipSlotInfo eachInfo : this.slotList) {
			HumanSpriteSlot.Builder eachBuilder = HumanSpriteSlot.newBuilder();
			eachBuilder.getSlotBuilder().setEquiped(eachInfo.getEquip());
			eachBuilder.setHumanGuid(humanEntity.getId());
			eachBuilder.getSlotBuilder().setIndex(eachInfo.getIndex());
			if (eachBuilder.getSlotBuilder().getEquiped()) {
				eachBuilder.getSlotBuilder().setSpriteUUID(
						eachInfo.getSpriteUUID());
			}
			humanEntity.getBuilder().addEquipSlot(eachBuilder);
		}
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 构建精灵数据
		for (HumanSprite.Builder eachSpriteBuilder : humanEntity.getBuilder()
				.getSpriteBuilderList()) {
			SpritePubInfo eachInfo = GameServerAssist
					.getTemplateService()
					.get(eachSpriteBuilder.getSprite().getSpriteId(),
							SpriteTemplate.class).toSimpleInfo();
			SpriteInfo sprite = eachInfo.toNewSpriteInfo();
			sprite.setUuid(eachSpriteBuilder.getSprite().getSpriteUUID());
			sprite.setIsEquip(eachSpriteBuilder.getSprite().getEquiped());
			sprite.setLevel(eachSpriteBuilder.getSprite().getLevel());
			// 获取升级模版;
			SpriteLevelupTemplate levelUpTemplate = this.templateManager
					.getSpriteLevelUpTemplate(sprite.getSpriteId(),
							sprite.getLevel());
			levelUpTemplate.setSpriteInfo(sprite);
			this.spriteList.add(sprite);
			// 精灵属性修正
			if (sprite.getIsEquip()) {
				this.amendSpriteProperty(owner.getPropertyManager(), sprite,
						ADD);
			}
		}
		// 构建背包数据
		initCellList();
		for (HumanSpriteBagCell.Builder eachCell : humanEntity.getBuilder()
				.getCellBuilderList()) {
			SpriteBagCellInfo cell = this.getBagCellByIndex(eachCell.getCell()
					.getIndex());
			cell.setEquip(eachCell.getCell().getEquiped());
			if (cell.getEquip()) {
				cell.setSpriteUUID(eachCell.getCell().getSpriteUUID());
			}
		}
		// 构建buff数据
		// 从模版数据构建buff信息
		buildAllSpriteBuffInfoFromTemplate();
		for (HumanSpriteBuff.Builder eachBuff : humanEntity.getBuilder()
				.getBuffBuilderList()) {
			SpriteBuffInfo buff = this.getSpriteBuffById(eachBuff.getBuff()
					.getBuffId());
			buff.setActivated(eachBuff.getBuff().getActivated());
			// buff属性修正
			if (buff.getActivated()) {
				this.amendBuffProperty(owner.getPropertyManager(), buff, ADD);
			}
		}
		// 构建装备位数据
		// initSlotList
		initSlotList();
		for (HumanSpriteSlot.Builder eachSlot : humanEntity.getBuilder()
				.getEquipSlotBuilderList()) {
			SpriteEuqipSlotInfo slot = this.getSpirteSlotByIndex(eachSlot
					.getSlot().getIndex());
			slot.setEquip(eachSlot.getSlot().getEquiped());
			if (slot.getEquip()) {
				slot.setSpriteUUID(eachSlot.getSlot().getSpriteUUID());
			}
		}

	}

	/**
	 * 根据索引获取背包格子信息;
	 * 
	 * @param index
	 * @return
	 */
	private SpriteBagCellInfo getBagCellByIndex(int index) {
		for (SpriteBagCellInfo eachCell : this.cellList) {
			if (eachCell.getIndex() == index) {
				return eachCell;
			}
		}
		return null;
	}

	/**
	 * 根据装备位索引获取装备位槽;
	 * 
	 * @param index
	 * @return
	 */
	private SpriteEuqipSlotInfo getSpirteSlotByIndex(int index) {
		for (SpriteEuqipSlotInfo eachSlot : this.slotList) {
			if (eachSlot.getIndex() == index) {
				return eachSlot;
			}
		}
		return null;
	}

	/**
	 * 根据buffid获取buff信息;
	 * 
	 * @param buffId
	 * @return
	 */
	private SpriteBuffInfo getSpriteBuffById(int buffId) {
		for (SpriteBuffInfo buff : this.buffList) {
			if (buff.getBuffId() == buffId) {
				return buff;
			}
		}
		return null;
	}

	/**
	 * 初始化装备位;
	 */
	private void initSlotList() {
		for (int i = 0; i < DEFAULT_SLOT_OPEN_SIZE; i++) {
			SpriteEuqipSlotInfo slot = new SpriteEuqipSlotInfo();
			slot.setEquip(false);
			slot.setIndex(i);
			this.slotList.add(slot);
		}
	}

	/**
	 * 购买精灵格子;
	 * 
	 * @param openIndex
	 */
	public void buySpriteCell(int openIndex) {
		int currentOpenIndex = owner.getSpriteBagSize() - 1;
		if (openIndex <= currentOpenIndex) {
			return;
		}
		int costCrystal = (openIndex - currentOpenIndex)
				* GameServerAssist.getGameConstants()
						.getPerSpriteBagCellCostCrystal();
		// 是否有足够的钱
		if (!owner.getWallet().isEnough(CurrencyType.CRYSTAL, costCrystal)) {
			owner.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
					CurrencyType.CRYSTAL.getDesc());
			return;
		}
		// 扣钱
		if (!owner.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.SPRITE_BAG_CELL_BUY, "")) {
			return;
		}
		// 开背包格子
		openBagCell(openIndex);
		// 发送通知
		GCBuySpriteCell buyMessage = new GCBuySpriteCell();
		buyMessage.setOpenIndex(openIndex);
		owner.sendMessage(buyMessage);

	}

	/**
	 * 开背包格子
	 * 
	 * @param openIndex
	 * @param openIndex2
	 */
	private void openBagCell(int openIndex) {
		for (int i = owner.getSpriteBagSize(); i <= openIndex; i++) {
			SpriteBagCellInfo cell = this.cellList.get(i);
			cell.setOpen(true);
			// 添加到缓存
			this.cellCache.addUpdate(cell.getIndex(), cell);
		}
		// 设置精灵背包大小;
		owner.setSpriteBagSize(openIndex + 1);
	}

	/**
	 * 初始化背包格子信息;
	 */
	private void initCellList() {
		// 初始化背包格子大小
		if (owner.getSpriteBagSize() < SharedConstants.INIT_HUMAN_SPRITE_BAG_SIZE) {
			owner.setSpriteBagSize(SharedConstants.INIT_HUMAN_SPRITE_BAG_SIZE);
		}
		for (int i = 0; i < MAX_BAG_OPEN_SIZE; i++) {
			SpriteBagCellInfo cell = new SpriteBagCellInfo();
			cell.setEquip(false);
			cell.setIndex(i);
			if (i < owner.getSpriteBagSize()) {
				cell.setOpen(true);
			}
			this.cellList.add(cell);
		}
	}

	/**
	 * 从模版中构建数据;
	 */
	private void buildAllSpriteBuffInfoFromTemplate() {
		this.buffList = this.templateManager.getSpriteBuffList();
	}

	/**
	 * 精灵背包是否还有空间;
	 * 
	 * @return
	 */
	public boolean haveSpriteCellSpace() {
		return getEmptyCell() != null;
	}

	/**
	 * 把精灵发到背包中;
	 * 
	 * @param pubSprite
	 * @param notify
	 */
	public void putToBag(SpritePubInfo pubSprite, boolean notify) {
		// 1. 检查背包是否有空地;
		if (!haveSpriteCellSpace()) {
			return;
		}
		// 2. 构建格子和精灵的关联;
		SpriteInfo sprite = pubSprite.toNewSpriteInfo();
		this.spriteList.add(sprite);
		SpriteBagCellInfo cell = getEmptyCell();
		cell.attachSprite(sprite);
		// 悬浮提示
		if (notify) {
			owner.sendSuccessMessage(LangConstants.SPRITE_PUB_PUT_SUCCESS,
					sprite.getName());
		}
		// 添加到缓存
		this.spriteCache.addUpdate(sprite.getUuid(), sprite);
		this.cellCache.addUpdate(cell.getIndex(), cell);
		// 入包以后的套装buff加成逻辑;
		SpriteBuffInfo activateBuff = null;
		if ((activateBuff = canActivateNewBuff(sprite)) != null
				&& !activateBuff.getActivated()) {
			// 激活该buff
			activateBuff.setActivated(true);
			// 添加到缓存
			this.buffCache.addUpdate(activateBuff.getBuffId(), activateBuff);
			// buff的属性激活加成
			this.amendBuffProperty(owner.getPropertyManager(), activateBuff,
					ADD);
			// 通知客户端
			GCActivateBuff activateBuffMessage = new GCActivateBuff();
			activateBuffMessage.setBuffId(activateBuff.getBuffId());
			owner.sendMessage(activateBuffMessage);
		}
	}

	/**
	 * 根据buff修正属性;
	 * 
	 * @param activateBuff
	 * @param operation
	 */
	private void amendBuffProperty(HumanPropertyManager propertyManager,
			SpriteBuffInfo activateBuff, int operation) {
		propertyManager.amendProperty(owner,
				AmendMethod.valueOf(activateBuff.getAmendType()),
				activateBuff.getPropId(),
				operation * activateBuff.getPropValue(),
				PropertyLogReason.SPRITE_BUFF, "");
	}

	/**
	 * 根据精灵修正属性值;
	 * 
	 * @param sprite
	 */
	private void amendSpriteProperty(HumanPropertyManager propertyManager,
			SpriteInfo sprite, int method) {
		propertyManager.amendProperty(owner,
				AmendMethod.valueOf(sprite.getAmendType()), sprite.getPropId(),
				method * sprite.getPropValue(), PropertyLogReason.SPRITE, "");
	}

	/**
	 * 是否可以激活新buff;
	 * 
	 * @param sprite
	 * @return
	 */
	private SpriteBuffInfo canActivateNewBuff(SpriteInfo sprite) {
		// 取出當前品质
		int quality = sprite.getQuality();
		// 目前拥有的当前品质集合
		if (isQualitySpriteAllGet(quality)) {
			return getBuffByQuality(quality);
		}
		return null;
	}

	/**
	 * 根据品质获取buff类型;
	 * 
	 * @param quality
	 * @return
	 */
	private SpriteBuffInfo getBuffByQuality(int quality) {
		for (SpriteBuffInfo each : this.buffList) {
			if (each.getActivateQuality() == quality) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 当前品质的精灵是否都收集齐了;
	 * 
	 * @param quality
	 * @return
	 */
	private boolean isQualitySpriteAllGet(int quality) {
		Set<Integer> spriteTypeSet = new HashSet<Integer>();
		for (SpriteInfo each : this.spriteList) {
			if (each.getQuality() == quality) {
				spriteTypeSet.add(each.getSpriteType());
			}
		}
		// 检验type是否集齐
		return SpriteType.isAllGet(spriteTypeSet);
	}

	/**
	 * 从背包中移除精灵;
	 * 
	 * @param sprite
	 */
	private void removeSpriteFromBag(SpriteInfo sprite) {
		SpriteBagCellInfo cell = getSpriteBagCell(sprite.getUuid());
		if (cell != null) {
			cell.unAttachSprite(sprite);
		}
		// 从列表中移除
		this.spriteList.remove(sprite);
		// 添加到缓存
		this.spriteCache.addDelete(sprite.getUuid(), sprite);
		this.cellCache.addUpdate(cell.getIndex(), cell);
	}

	/**
	 * 获取指定的精灵背包格子;
	 * 
	 * @param spriteId
	 * @return
	 */
	private SpriteBagCellInfo getSpriteBagCell(String uuid) {
		for (SpriteBagCellInfo eachCell : this.cellList) {
			if (!eachCell.isEmpty() && eachCell.getSpriteUUID().equals(uuid)) {
				return eachCell;
			}
		}
		return null;
	}

	/**
	 * 获取空的精灵格子;
	 * 
	 * @return
	 */
	private SpriteBagCellInfo getEmptyCell() {
		for (SpriteBagCellInfo eachCell : this.cellList) {
			if (eachCell.getOpen() && eachCell.isEmpty()) {
				return eachCell;
			}
		}
		return null;
	}

	/**
	 * 获取空余的精灵格子数量
	 * 
	 * @return
	 */
	public int getEmptyCellSize() {
		int emptySize = 0;
		for (SpriteBagCellInfo eachCell : this.cellList) {
			if (eachCell.getOpen() && eachCell.isEmpty()) {
				emptySize++;
			}
		}
		return emptySize;
	}

	/**
	 * 装备指定的精灵;
	 * 
	 * @param uuid
	 */
	public void equipSprite(String uuid) {
		SpriteInfo sprite = getSpriteByUUID(uuid);
		if (sprite == null) {
			return;
		}
		// 如果已经装备;
		if (sprite.getIsEquip()) {
			return;
		}
		// 如果已经有了同类型的精灵
		if (alreadyEquipSameTypeSprite(sprite.getSpriteType())) {
			owner.sendWarningMessage(LangConstants.CAN_NOT_EQUIP_MORE_SAME_TYPE_SPRITE);
			return;
		}
		// 如果装备位已经满了
		if (!this.haveSpriteSlotSpace()) {
			return;
		}
		SpriteEuqipSlotInfo slot = this.getEmptySlot();
		slot.attachSprite(sprite);
		// 添加到缓存
		this.slotCache.addUpdate(slot.getIndex(), slot);
		this.spriteCache.addUpdate(sprite.getUuid(), sprite);
		// 添加装备以后的效果;
		amendSpriteProperty(owner.getPropertyManager(), sprite, ADD);
		// 发送通知
		GCSpriteEquipState equipMessage = new GCSpriteEquipState();
		equipMessage.setIsEquip(true);
		equipMessage.setSpriteUUID(sprite.getUuid());
		this.owner.sendMessage(equipMessage);
	}

	/**
	 * 是否已经装备了相同类型的精灵;
	 * 
	 * @param spriteType
	 * @return
	 */
	private boolean alreadyEquipSameTypeSprite(int spriteType) {
		for (SpriteInfo eachSprite : this.spriteList) {
			if (eachSprite.getIsEquip()
					&& (eachSprite.getSpriteType() == spriteType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 卸下指定的精灵;
	 * 
	 * @param uuid
	 */
	public void unEquipSprite(String uuid) {
		SpriteInfo sprite = getSpriteByUUID(uuid);
		if (sprite == null) {
			return;
		}
		// 如果已经处于卸下状态;
		if (!sprite.getIsEquip()) {
			return;
		}
		// 取出装备他的位置
		SpriteEuqipSlotInfo slot = getSpriteSlot(uuid);
		slot.unAttachSprite(sprite);
		// 添加到缓存
		this.slotCache.addUpdate(slot.getIndex(), slot);
		this.spriteCache.addUpdate(sprite.getUuid(), sprite);
		// 去除装备的效果;
		amendSpriteProperty(owner.getPropertyManager(), sprite, REDUCE);
		// 发送通知
		GCSpriteEquipState equipMessage = new GCSpriteEquipState();
		equipMessage.setIsEquip(false);
		equipMessage.setSpriteUUID(uuid);
		this.owner.sendMessage(equipMessage);
	}

	/**
	 * 是否有装备位空间;
	 * 
	 * @return
	 */
	private boolean haveSpriteSlotSpace() {
		return getEmptySlot() != null;
	}

	/**
	 * 获取空装备位;
	 * 
	 * @return
	 */
	private SpriteEuqipSlotInfo getEmptySlot() {
		for (SpriteEuqipSlotInfo eachSlot : this.slotList) {
			if (eachSlot.isEmpty()) {
				return eachSlot;
			}
		}
		return null;
	}

	/**
	 * 根据id获取精灵;
	 * 
	 * @param uuid
	 * @return
	 */
	private SpriteInfo getSpriteByUUID(String uuid) {
		for (SpriteInfo each : this.spriteList) {
			if (each.getUuid().equals(uuid)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 获取指定装备的装备位置;
	 * 
	 * @param uuid
	 * @return
	 */
	private SpriteEuqipSlotInfo getSpriteSlot(String uuid) {
		for (SpriteEuqipSlotInfo each : this.slotList) {
			if (!each.isEmpty() && each.getSpriteUUID().equals(uuid)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 丢弃指定的精灵;
	 * 
	 * @param string
	 */
	public void dropSprite(String string) {
		SpriteInfo sprite = getSpriteByUUID(string);
		if (sprite == null) {
			return;
		}
		// 当前等级丢弃返回的灵气值
		dropSprite(sprite);
		// 发通知
		GCDropSprite dropMessage = new GCDropSprite();
		dropMessage.setSpriteUUID(sprite.getUuid());
		owner.sendMessage(dropMessage);
	}

	/**
	 * 丢弃精灵;
	 * 
	 * @param sprite
	 */
	private void dropSprite(SpriteInfo sprite) {
		SpriteLevelupTemplate levelUpTemplate = this.templateManager
				.getSpriteLevelUpTemplate(sprite.getSpriteId(),
						sprite.getLevel());
		int returnAura = levelUpTemplate.getDropReturnAura();
		owner.setAura(owner.getAura() + returnAura);
		// 1.从装备栏清除
		this.unEquipSprite(sprite.getUuid());
		// 2.从背包中清除
		this.removeSpriteFromBag(sprite);
	}

	/**
	 * 升级指定的精灵;
	 * 
	 * @param uuid
	 */
	public void levelUpSprite(String uuid) {
		SpriteInfo sprite = getSpriteByUUID(uuid);
		if (sprite == null) {
			return;
		}
		// 获取升级消耗
		int currentLevel = sprite.getLevel();
		SpriteLevelupTemplate levelUpTemplate = this.templateManager
				.getSpriteLevelUpTemplate(sprite.getSpriteId(), currentLevel);
		if (levelUpTemplate == null) {
			return;
		}
		// 检查是否精力达到了顶级
		int nextLevel = currentLevel + 1;
		SpriteLevelupTemplate nextLevelUpTemplate = this.templateManager
				.getSpriteLevelUpTemplate(sprite.getSpriteId(), nextLevel);
		if (nextLevelUpTemplate == null) {
			// 提示精灵已经达到满级;
			owner.sendErrorMessage(LangConstants.SPRITE_MAX_LEVEL);
			return;
		}
		int costAura = levelUpTemplate.getCostAura();
		// 当前灵气是否足够
		if (costAura > owner.getAura()) {
			// 提示灵气不足
			owner.sendErrorMessage(LangConstants.AURA_NOT_ENOUTH);
			return;
		}
		// 扣去灵气值
		owner.setAura(owner.getAura() - costAura);
		// 精灵升级
		spriteLevelUp(sprite);
		// 添加到缓存
		this.spriteCache.addUpdate(sprite.getUuid(), sprite);
		// 发通知
		GCUpdateSprite updateSpriteMessage = new GCUpdateSprite();
		updateSpriteMessage.setSprite(sprite);
		owner.sendMessage(updateSpriteMessage);
	}

	/**
	 * 精灵升级;
	 * 
	 * @param sprite
	 */
	private void spriteLevelUp(SpriteInfo sprite) {
		// 脱下原来属性, 换上升级后属性
		this.amendSpriteProperty(owner.getPropertyManager(), sprite, REDUCE);
		// 加等级，属性
		sprite.setLevel(sprite.getLevel() + 1);
		SpriteLevelupTemplate levelUpTemplate = this.templateManager
				.getSpriteLevelUpTemplate(sprite.getSpriteId(),
						sprite.getLevel());
		if (levelUpTemplate == null) {
			return;
		}
		levelUpTemplate.setSpriteInfo(sprite);
		this.amendSpriteProperty(owner.getPropertyManager(), sprite, ADD);
		// 发送精灵升级事件
		owner.getEventBus().fireEvent(new SpriteUpgradeEvent());
	}

	/**
	 * 获取精灵列表;
	 * 
	 * @return
	 */
	public List<SpriteInfo> getSpirteList() {
		return spriteList;
	}

	/**
	 * 获取buff列表;
	 * 
	 * @return
	 */
	public List<SpriteBuffInfo> getBuffList() {
		return buffList;
	}

	/**
	 * 获取精灵背包列表;
	 * 
	 * @return
	 */
	public List<SpriteBagCellInfo> getBagCellList() {
		return cellList;
	}

	/**
	 * 获取装备为列表;
	 * 
	 * @return
	 */
	public List<SpriteEuqipSlotInfo> getEquipSlotList() {
		return this.slotList;
	}

	@Override
	public Human getOwner() {
		return owner;
	}

	/**
	 * 战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		// 精灵属性加成
		for (HumanSprite.Builder eachSpriteBuilder : humanEntity.getBuilder()
				.getSpriteBuilderList()) {
			SpritePubInfo eachInfo = GameServerAssist
					.getTemplateService()
					.get(eachSpriteBuilder.getSprite().getSpriteId(),
							SpriteTemplate.class).toSimpleInfo();
			SpriteInfo sprite = eachInfo.toNewSpriteInfo();
			sprite.setUuid(eachSpriteBuilder.getSprite().getSpriteUUID());
			sprite.setIsEquip(eachSpriteBuilder.getSprite().getEquiped());
			sprite.setLevel(eachSpriteBuilder.getSprite().getLevel());
			SpriteLevelupTemplate levelUpTemplate = this.templateManager
					.getSpriteLevelUpTemplate(sprite.getSpriteId(),
							sprite.getLevel());
			levelUpTemplate.setSpriteInfo(sprite);
			if (sprite.getIsEquip()) {
				this.amendSpriteProperty(propertyManager, sprite, ADD);
			}
		}
		// 精灵buff属性加成
		buildAllSpriteBuffInfoFromTemplate();
		for (HumanSpriteBuff.Builder eachBuff : humanEntity.getBuilder()
				.getBuffBuilderList()) {
			SpriteBuffInfo buff = this.getSpriteBuffById(eachBuff.getBuff()
					.getBuffId());
			buff.setActivated(eachBuff.getBuff().getActivated());
			if (buff.getActivated()) {
				this.amendBuffProperty(propertyManager, buff, ADD);
			}
		}
	}
}
