package com.hifun.soul.gameserver.godsoul.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanGodsoulEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EquipBitUpgradeEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.godsoul.EquipType;
import com.hifun.soul.gameserver.godsoul.GodsoulToEntityConverter;
import com.hifun.soul.gameserver.godsoul.MagicPaperLevel;
import com.hifun.soul.gameserver.godsoul.MagicPaperToEntityConverter;
import com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo;
import com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo;
import com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo;
import com.hifun.soul.gameserver.godsoul.msg.GCUpgradeEquipBit;
import com.hifun.soul.gameserver.godsoul.msg.GCUpgradeMagicPaper;
import com.hifun.soul.gameserver.godsoul.template.EquipBitBuffTemplate;
import com.hifun.soul.gameserver.godsoul.template.EquipBitLevel;
import com.hifun.soul.gameserver.godsoul.template.EquipBitLevelTemplate;
import com.hifun.soul.gameserver.godsoul.template.EquipBitPropertyAmendTemplate;
import com.hifun.soul.gameserver.godsoul.template.MagicPaperBuffTemplate;
import com.hifun.soul.gameserver.godsoul.template.MagicPaperLevelTemplate;
import com.hifun.soul.gameserver.human.AbstractNotifyManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.proto.data.entity.Entity.HumanGodsoul;
import com.hifun.soul.proto.data.entity.Entity.HumanMagicPaper;

/**
 * 角色神魄管理器
 * 
 * @author yandajun
 * 
 */
public class HumanGodsoulManager extends AbstractNotifyManager implements
		IHumanPersistenceManager, ICachableComponent,
		IHumanPropertiesLoadForBattle {
	private Human human;
	private Map<Integer, Integer> equipBitTypeLevelMap = new HashMap<Integer, Integer>();
	private CacheEntry<Integer, EquipBitInfo> equipBitCaches = new CacheEntry<Integer, EquipBitInfo>();
	private Map<Integer, Integer> magicPaperLevelMap = new HashMap<Integer, Integer>();
	private CacheEntry<Integer, IEntity> magicPaperCaches = new CacheEntry<Integer, IEntity>();
	/** 激活过的装备位buff */
	private List<Integer> equipBitValidBuffIds = new ArrayList<Integer>();
	/** 激活过的灵图buff */
	private List<Integer> magicPaperValidBuffIds = new ArrayList<Integer>();

	private GodsoulTemplateManager godsoulTemplateManager;
	private GodsoulToEntityConverter equipBitConverter;
	private MagicPaperToEntityConverter magciPaperConverter;

	public HumanGodsoulManager(Human human) {
		super(human);
		this.human = human;
		human.registerPersistenceManager(this);
		human.registerCachableManager(this);
		godsoulTemplateManager = GameServerAssist.getGodsoulTemplateManager();
		equipBitConverter = new GodsoulToEntityConverter(human);
		magciPaperConverter = new MagicPaperToEntityConverter(human);
		this.human.addNotifyManager(this);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 加载装备位信息
		for (HumanGodsoul.Builder builder : humanEntity.getBuilder()
				.getGodsoulBuilderList()) {
			equipBitTypeLevelMap.put(builder.getEquipBit().getEquipBitType(),
					builder.getEquipBit().getCurrentLevel());
		}
		// 加载灵图信息
		for (HumanMagicPaper.Builder builder : humanEntity.getBuilder()
				.getMagicPaperBuilderList()) {
			magicPaperLevelMap.put(builder.getMagicPaper().getPaperId(),
					builder.getMagicPaper().getLevel());
			// 灵图属性加成
			MagicPaperLevelTemplate magicPaperTemplate = godsoulTemplateManager
					.getMagicPaperTemplate(
							builder.getMagicPaper().getPaperId(), builder
									.getMagicPaper().getLevel());
			human.getPropertyManager().amendProperty(human,
					AmendMethod.valueOf(magicPaperTemplate.getAmendMethod()),
					magicPaperTemplate.getPropertyId(),
					magicPaperTemplate.getAmendEffect(),
					PropertyLogReason.MAGIC_PAPER, "");
		}
		// 装备位buff属性加成
		amendEquipBitBuffProperties(human.getPropertyManager());
		// 灵图buff属性加成
		amendMagicPaperBuffProperties(human.getPropertyManager());
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 保存装备位信息
		for (Integer equipBitType : equipBitTypeLevelMap.keySet()) {
			EquipBitInfo equipBitInfo = new EquipBitInfo();
			equipBitInfo
					.setCurrentLevel(equipBitTypeLevelMap.get(equipBitType));
			equipBitInfo.setEquipBitType(equipBitType);
			humanEntity.getBuilder().addGodsoul(
					this.equipBitConverter.convert(equipBitInfo).getBuilder());
		}
		// 保存灵图信息
		for (Integer paperId : magicPaperLevelMap.keySet()) {
			MagicPaperLevel magicPaperLevel = new MagicPaperLevel();
			magicPaperLevel.setPaperId(paperId);
			magicPaperLevel.setLevel(magicPaperLevelMap.get(paperId));
			humanEntity.getBuilder().addMagicPaper(
					this.magciPaperConverter.convert(magicPaperLevel)
							.getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (EquipBitInfo equipBitInfo : equipBitCaches.getAllUpdateData()) {
			HumanGodsoulEntity entity = this.equipBitConverter
					.convert(equipBitInfo);
			updateList.add(entity);
		}
		updateList.addAll(magicPaperCaches.getAllUpdateData());
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		for (EquipBitInfo equipBitInfo : equipBitCaches.getAllDeleteData()) {
			HumanGodsoulEntity entity = this.equipBitConverter
					.convert(equipBitInfo);
			deleteList.add(entity);
		}
		deleteList.addAll(magicPaperCaches.getAllDeleteData());
		return deleteList;
	}

	@Override
	public Human getOwner() {
		return this.human;
	}

	/**
	 * 生成所有装备位信息
	 */
	public List<EquipBitInfo> generateEquipBitInfoList() {
		List<EquipBitInfo> equipBitInfoList = new ArrayList<EquipBitInfo>();

		for (EquipType type : EquipType.values()) {
			int equipBitType = type.getIndex();
			EquipBitInfo equipBitInfo = generateEquipBitInfo(equipBitType);
			equipBitInfoList.add(equipBitInfo);

		}
		return equipBitInfoList;
	}

	/**
	 * 是否有可强化的装备位
	 */
	private boolean hasUpgradeEquipBit() {
		// 没满级、没达到灵图上限、等级足够、金币足够
		for (int equipType : equipBitTypeLevelMap.keySet()) {
			int currentEquipBitLevel = human.getHumanGodsoulManager()
					.getEquipBitLevel(equipType);
			if (currentEquipBitLevel >= godsoulTemplateManager
					.getEquipBitMaxLevel()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 装备位强化升级
	 */
	public void upgradeEquipBit(int equipBitType, boolean isSelectedCrystal) {
		int currentLevel = this.getEquipBitLevel(equipBitType);
		EquipBitLevelTemplate equipBitLevelTemplate = godsoulTemplateManager
				.getEquipBitLevelTemplate(currentLevel + 1);
		// 根据魔晶选择成功率
		int successRate = equipBitLevelTemplate.getSuccessRate();
		int amendSucessRate = GameServerAssist.getGameConstants()
				.getAmendSuccessRate();
		if (isSelectedCrystal) {
			successRate += amendSucessRate;
		}
		// 概率强化升级
		if (MathUtils.shake(successRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
			equipBitTypeLevelMap.put(equipBitType, currentLevel + 1);
			// 显示下一级别的信息
			EquipBitInfo equipBitInfo = generateEquipBitInfo(equipBitType);
			equipBitCaches.addUpdate(equipBitType, equipBitInfo);
			// 通知装备管理器，装备位已经强化升级，需要属性加成
			human.getBagManager().admendPropertyByUpgradeEquipBit(equipBitType);
			// 装备位buff属性加成
			amendEquipBitBuffProperties(human.getPropertyManager());
			// 发送响应装备位强化消息
			GCUpgradeEquipBit gcMsg = new GCUpgradeEquipBit();
			List<GodsoulBuffInfo> buffInfoList = generateEquipBitBuffInfoList();
			gcMsg.setGodsoulBuffInfos(buffInfoList
					.toArray(new GodsoulBuffInfo[0]));
			gcMsg.setEquipBitInfo(equipBitInfo);
			human.sendMessage(gcMsg);
		} else {
			human.sendErrorMessage(LangConstants.UPGRADE_EQUIP_BIT_FAILED);
			return;
		}
		// 发送装备位升级事件
		human.getEventBus().fireEvent(new EquipBitUpgradeEvent());
	}

	/**
	 * 生成装备位信息
	 */
	private EquipBitInfo generateEquipBitInfo(int equipBitType) {
		int currentLevel = getEquipBitLevel(equipBitType);
		EquipBitInfo equipBitInfo = new EquipBitInfo();
		EquipBitLevelTemplate currentTempalte = godsoulTemplateManager
				.getEquipBitLevelTemplate(currentLevel);
		if (currentTempalte != null) {
			equipBitInfo.setSuccessRate(currentTempalte.getSuccessRate());
			equipBitInfo
					.setCurrentEffect(getEffect(equipBitType, currentLevel));
			equipBitInfo
					.setNextEffect(getEffect(equipBitType, currentLevel + 1));
			equipBitInfo.setNeedHumanLevel(currentTempalte.getNeedHumanLevel());
			equipBitInfo.setNeedCoin(getNeedCoin(equipBitType, currentLevel));
			equipBitInfo.setIsMaxLevel(currentLevel >= godsoulTemplateManager
					.getEquipBitMaxLevel());
			equipBitInfo.setEquipBitType(equipBitType);
			equipBitInfo.setCurrentLevel(currentLevel);
			equipBitInfo.setNeedCrystalNum(GameServerAssist.getGameConstants()
					.getUpgradeCostCrystal());
			equipBitInfo.setAmendSuccessRate(GameServerAssist
					.getGameConstants().getAmendSuccessRate());
		}
		return equipBitInfo;
	}

	/**
	 * 获取装备位等级
	 */
	public int getEquipBitLevel(int equipBitType) {
		Integer currentLevel = equipBitTypeLevelMap.get(equipBitType);
		if (currentLevel == null) {
			return 0;
		}
		return currentLevel.intValue();
	}

	/**
	 * 获取强化装备位需要的金币
	 */
	public int getNeedCoin(int equipBitType, int level) {
		EquipBitLevelTemplate equipBitLevelTemplate = godsoulTemplateManager
				.getEquipBitLevelTemplate(level);
		EquipBitLevel equipBitLevel = equipBitLevelTemplate.getEquipBitLevels()[equipBitType - 1];
		int needCoin = equipBitLevel.getNeedCoin();
		return needCoin;
	}

	/**
	 * 获取强化效果
	 */
	private int getEffect(int equipBitType, int level) {
		EquipBitPropertyAmendTemplate equipBitPropertyAmendTemplate = godsoulTemplateManager
				.getEquipBitPropertyAmendTemplate(equipBitType);
		int effect = equipBitPropertyAmendTemplate.getAmendValue() * level;
		return effect;
	}

	/**
	 * 获取当前强化效果
	 */
	public int getCurrentEffect(int equipBitType) {
		int currentLevel = this.getEquipBitLevel(equipBitType);
		EquipBitPropertyAmendTemplate equipBitPropertyAmendTemplate = godsoulTemplateManager
				.getEquipBitPropertyAmendTemplate(equipBitType);
		int effect = equipBitPropertyAmendTemplate.getAmendValue()
				* currentLevel;
		return effect;
	}

	/**
	 * 获取每级强化效果
	 */
	public int getPerLevelEffect(int equipBitType) {
		EquipBitPropertyAmendTemplate equipBitPropertyAmendTemplate = godsoulTemplateManager
				.getEquipBitPropertyAmendTemplate(equipBitType);
		int effect = equipBitPropertyAmendTemplate.getAmendValue();
		return effect;
	}

	/**
	 * 获取灵图当前等级
	 */
	public int getMagicPaperCurrentLevel(int paperId) {
		Integer level = magicPaperLevelMap.get(paperId);
		if (level == null) {
			level = 0;
		}
		return level;
	}

	/**
	 * 生成灵图列表信息
	 */
	public List<MagicPaperInfo> generateMagicPaperInfoList() {
		List<MagicPaperInfo> paperInfoList = new ArrayList<MagicPaperInfo>();
		for (EquipType type : EquipType.values()) {
			int paperId = type.getIndex();
			MagicPaperInfo paperInfo = generateMagicPaperInfo(paperId);
			paperInfoList.add(paperInfo);
		}
		return paperInfoList;
	}

	/**
	 * 生成灵图信息
	 */
	private MagicPaperInfo generateMagicPaperInfo(int paperId) {
		int level = getMagicPaperCurrentLevel(paperId);
		MagicPaperLevelTemplate currentTemplate = godsoulTemplateManager
				.getMagicPaperTemplate(paperId, level);
		MagicPaperLevelTemplate nextTemplate = godsoulTemplateManager
				.getMagicPaperTemplate(paperId, level + 1);
		MagicPaperInfo paperInfo = new MagicPaperInfo();
		paperInfo.setPaperId(paperId);
		paperInfo.setCurrentLevel(level);
		if (currentTemplate != null) {
			paperInfo.setPropertyId(currentTemplate.getPropertyId());
			paperInfo.setAmendType(currentTemplate.getAmendMethod());
			paperInfo.setCurrentEffect(currentTemplate.getAmendEffect());
			paperInfo.setCurrentEquipBitMaxLevel(currentTemplate
					.getMaxEquipBitLevel());
			CommonItem costItem = CommonItemBuilder
					.genCommonItem(currentTemplate.getCostItemId());
			paperInfo.setCostItem(costItem);
			paperInfo.setCostItemNum(currentTemplate.getCostItemNum());
			paperInfo.setHasItemNum(human.getBagManager().getItemCount(
					currentTemplate.getCostItemId()));
		}
		if (nextTemplate != null) {
			paperInfo.setPropertyId(nextTemplate.getPropertyId());
			paperInfo.setAmendType(nextTemplate.getAmendMethod());
			paperInfo.setNextEffect(nextTemplate.getAmendEffect());
			paperInfo.setNextEquipBitMaxLevel(nextTemplate
					.getMaxEquipBitLevel());
		}
		if (paperInfo.getCostItem() == null) {
			paperInfo.setCostItem(new CommonItem());
		}
		paperInfo.setIsMaxLevel(godsoulTemplateManager
				.getMagicPaperMaxLevell(paperId) == level);
		return paperInfo;
	}

	/**
	 * 生成装备位buff列表信息
	 */
	public List<GodsoulBuffInfo> generateEquipBitBuffInfoList() {
		List<GodsoulBuffInfo> buffInfoList = new ArrayList<GodsoulBuffInfo>();
		for (Integer buffId : godsoulTemplateManager.getEquipBitBuffTemplates()
				.keySet()) {
			EquipBitBuffTemplate template = godsoulTemplateManager
					.getEquipBitBuffTemplates().get(buffId);
			GodsoulBuffInfo buffInfo = new GodsoulBuffInfo();
			buffInfo.setBuffId(buffId);
			buffInfo.setNeedUpgradeLevel(template.getNeedEquipBitLevel());
			buffInfo.setPropertyId(template.getPropertyId());
			buffInfo.setAmendEffect(template.getAmendEffect());
			buffInfo.setAmendType(template.getAmendMethod());
			// 判断是否激活
			boolean valid = true;
			for (EquipType type : EquipType.values()) {
				Integer level = getEquipBitLevel(type.getIndex());
				if (level < template.getNeedEquipBitLevel()) {
					valid = false;
				}
			}
			buffInfo.setValid(valid);
			buffInfoList.add(buffInfo);
		}
		return buffInfoList;
	}

	/**
	 * 生成灵图buff列表信息
	 */
	public List<GodsoulBuffInfo> generateMagicPaperBuffInfoList() {
		List<GodsoulBuffInfo> buffInfoList = new ArrayList<GodsoulBuffInfo>();
		for (Integer buffId : godsoulTemplateManager
				.getMagicPaperBuffTemplates().keySet()) {
			MagicPaperBuffTemplate template = godsoulTemplateManager
					.getMagicPaperBuffTemplates().get(buffId);
			GodsoulBuffInfo buffInfo = new GodsoulBuffInfo();
			buffInfo.setBuffId(buffId);
			buffInfo.setNeedUpgradeLevel(template.getNeedEquipBitLevel());
			buffInfo.setPropertyId(template.getPropertyId());
			buffInfo.setAmendEffect(template.getAmendEffect());
			buffInfo.setAmendType(template.getAmendMethod());
			// 判断是否激活
			boolean valid = true;
			for (EquipType type : EquipType.values()) {
				int paperId = type.getIndex();
				Integer level = getMagicPaperCurrentLevel(paperId);
				if (level == null) {
					level = 0;
				}
				if (level < template.getNeedEquipBitLevel()) {
					valid = false;
				}
			}
			buffInfo.setValid(valid);
			buffInfoList.add(buffInfo);
		}
		return buffInfoList;
	}

	/**
	 * 是否有可升级的灵图
	 */
	private boolean hasUpgradeMagicPaper() {
		for (int paperId : magicPaperLevelMap.keySet()) {
			int currentLevel = getMagicPaperCurrentLevel(paperId);
			MagicPaperLevelTemplate currentTemplate = godsoulTemplateManager
					.getMagicPaperTemplate(paperId, currentLevel);
			// 没有达到满级，且材料充足
			if (currentLevel < godsoulTemplateManager
					.getMagicPaperMaxLevell(paperId)
					&& currentTemplate != null
					&& human.getBagManager().getItemCount(
							currentTemplate.getCostItemId()) < currentTemplate
							.getCostItemNum()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 升级灵图
	 */
	public void upgradeMagicPaper(int paperId) {
		MagicPaperInfo paperInfo = generateMagicPaperInfo(paperId);
		// 消耗材料
		int itemId = paperInfo.getCostItem().getItemId();
		int removeNum = paperInfo.getCostItemNum();
		if (human.getBagManager().removeItemByItemId(itemId, removeNum,
				ItemLogReason.UPGRADE_MAGIC_PAPER, "")) {
			int currentLevel = getMagicPaperCurrentLevel(paperId);
			magicPaperLevelMap.put(paperId, currentLevel + 1);
			MagicPaperLevel magicPaper = new MagicPaperLevel();
			magicPaper.setPaperId(paperId);
			magicPaper.setLevel(currentLevel + 1);
			magicPaperCaches.addUpdate(paperId,
					magciPaperConverter.convert(magicPaper));
			// 灵图属性加成(升级加成-上级加成)
			human.getPropertyManager().amendProperty(human,
					AmendMethod.valueOf(paperInfo.getAmendType()),
					paperInfo.getPropertyId(),
					paperInfo.getNextEffect() - paperInfo.getCurrentEffect(),
					PropertyLogReason.MAGIC_PAPER, "");
			// 灵图buff属性加成
			amendMagicPaperBuffProperties(human.getPropertyManager());
			// 发送升级消息
			GCUpgradeMagicPaper msg = new GCUpgradeMagicPaper();
			msg.setMagicPaperInfo(generateMagicPaperInfo(paperId));
			msg.setGodsoulBuffInfos(generateMagicPaperBuffInfoList().toArray(
					new GodsoulBuffInfo[0]));
			human.sendMessage(msg);
		}
	}

	/**
	 * 战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		// 首先加载数据
		onLoad(humanEntity);
		// 灵图属性加成
		for (HumanMagicPaper.Builder builder : humanEntity.getBuilder()
				.getMagicPaperBuilderList()) {
			MagicPaperLevelTemplate magicPaperTemplate = godsoulTemplateManager
					.getMagicPaperTemplate(
							builder.getMagicPaper().getPaperId(), builder
									.getMagicPaper().getLevel());
			propertyManager.amendProperty(human,
					AmendMethod.valueOf(magicPaperTemplate.getAmendMethod()),
					magicPaperTemplate.getPropertyId(),
					magicPaperTemplate.getAmendEffect(),
					PropertyLogReason.MAGIC_PAPER, "");
		}
		// 装备位buff属性加成
		amendEquipBitBuffProperties(propertyManager);
		// 灵图buff属性加成
		amendMagicPaperBuffProperties(propertyManager);
	}

	/**
	 * 装备位buff属性加成
	 */
	private void amendEquipBitBuffProperties(
			HumanPropertyManager propertyManager) {
		List<GodsoulBuffInfo> buffInfoList = generateEquipBitBuffInfoList();
		for (GodsoulBuffInfo info : buffInfoList) {
			// 如果已经被激活，且没有加成过
			if (info.getValid()
					&& !equipBitValidBuffIds.contains(info.getBuffId())) {
				propertyManager.amendProperty(human,
						AmendMethod.valueOf(info.getAmendType()),
						info.getPropertyId(), info.getAmendEffect(),
						PropertyLogReason.GODSOUL_BUFF, "");
				equipBitValidBuffIds.add(info.getBuffId());
			}
		}
	}

	/**
	 * 灵图buff属性加成
	 */
	private void amendMagicPaperBuffProperties(
			HumanPropertyManager propertyManager) {
		List<GodsoulBuffInfo> buffInfoList = generateMagicPaperBuffInfoList();
		for (GodsoulBuffInfo info : buffInfoList) {
			// 如果已经被激活，且没有加成过
			if (info.getValid()
					&& !magicPaperValidBuffIds.contains(info.getBuffId())) {
				propertyManager.amendProperty(human,
						AmendMethod.valueOf(info.getAmendType()),
						info.getPropertyId(), info.getAmendEffect(),
						PropertyLogReason.GODSOUL_BUFF, "");
				magicPaperValidBuffIds.add(info.getBuffId());
			}
		}
	}

	@Override
	public GameFuncType getFuncType() {
		return GameFuncType.GODSOUL;
	}

	@Override
	public boolean isNotify() {
		// 灵图可升级或神魄可强化
		if (hasUpgradeMagicPaper() || hasUpgradeEquipBit()) {
			return true;
		}
		return false;
	}
}
