package com.hifun.soul.gameserver.human;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hifun.soul.common.LogReasons.AuraLogReason;
import com.hifun.soul.common.LogReasons.BasicPlayerLogReason;
import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.LogReasons.OnlineTimeLogReason;
import com.hifun.soul.common.LogReasons.PrestigeLogReason;
import com.hifun.soul.common.LogReasons.SkillPointLogReason;
import com.hifun.soul.common.LogReasons.StarSoulLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.constants.SystemMessageType;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.abattoir.manager.HumanAbattoirManager;
import com.hifun.soul.gameserver.activity.HumanActivityManager;
import com.hifun.soul.gameserver.antiindulge.HumanAntiIndulgeManager;
import com.hifun.soul.gameserver.arena.manager.HumanArenaManager;
import com.hifun.soul.gameserver.autobattle.manager.HumanAutoBattleManager;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleUtil;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.ai.IBattleAI;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.unit.HumanGuarder;
import com.hifun.soul.gameserver.betareward.manager.HumanBetaRewardManager;
import com.hifun.soul.gameserver.bloodtemple.manager.HumanBloodTempleManager;
import com.hifun.soul.gameserver.boss.manager.HumanBossManager;
import com.hifun.soul.gameserver.building.manager.HumanBuildingManager;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.text.LinkType;
import com.hifun.soul.gameserver.common.text.RichTextHelper;
import com.hifun.soul.gameserver.costnotify.manager.HumanCostNotifyManager;
import com.hifun.soul.gameserver.crystalexchange.manager.HumanCrystalExchangeManager;
import com.hifun.soul.gameserver.currency.manager.IWallet;
import com.hifun.soul.gameserver.currency.manager.Wallet;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.escort.manager.HumanEscortManager;
import com.hifun.soul.gameserver.event.EventManager;
import com.hifun.soul.gameserver.event.IEventBus;
import com.hifun.soul.gameserver.foster.manager.HumanFosterManager;
import com.hifun.soul.gameserver.friend.manager.HumanFriendManager;
import com.hifun.soul.gameserver.function.manager.HumanFunctionManager;
import com.hifun.soul.gameserver.gem.HumanGemManager;
import com.hifun.soul.gameserver.gift.manager.HumanGiftManager;
import com.hifun.soul.gameserver.godsoul.manager.HumanGodsoulManager;
import com.hifun.soul.gameserver.guide.manager.HumanGuideManager;
import com.hifun.soul.gameserver.helper.manager.HumanHelperManager;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.human.quest.manager.HumanQuestManager;
import com.hifun.soul.gameserver.item.manager.HumanForgeManager;
import com.hifun.soul.gameserver.legion.manager.HumanLegionManager;
import com.hifun.soul.gameserver.legionboss.manager.HumanLegionBossManager;
import com.hifun.soul.gameserver.legionmine.manager.HumanLegionMineWarManager;
import com.hifun.soul.gameserver.levy.HumanLevyManager;
import com.hifun.soul.gameserver.loginreward.manager.HumanLoginRewardManager;
import com.hifun.soul.gameserver.mail.manager.HumanMailManager;
import com.hifun.soul.gameserver.mars.manager.HumanMarsManager;
import com.hifun.soul.gameserver.matchbattle.manager.HumanMatchBattleManager;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.nostrum.manager.HumanNostrumManager;
import com.hifun.soul.gameserver.onlinereward.manager.HumanOnlineRewardManager;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.predict.manager.HumanPredictManager;
import com.hifun.soul.gameserver.prison.manager.HumanPrisonManager;
import com.hifun.soul.gameserver.rank.manager.HumanHonorRankManager;
import com.hifun.soul.gameserver.rank.manager.HumanLevelRankManager;
import com.hifun.soul.gameserver.rank.manager.HumanTitleRankManager;
import com.hifun.soul.gameserver.rank.manager.HumanVipRankManager;
import com.hifun.soul.gameserver.recharge.manager.HumanRechargeManager;
import com.hifun.soul.gameserver.rechargetx.manager.HumanRechargeTxManager;
import com.hifun.soul.gameserver.refine.manager.HumanRefineManager;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.RoleType;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.IntPropertyCacheSet;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.shop.manager.HumanSpecialShopManager;
import com.hifun.soul.gameserver.sign.manager.HumanSignManager;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillDevelopType;
import com.hifun.soul.gameserver.sprite.manager.HumanSpriteManager;
import com.hifun.soul.gameserver.sprite.manager.HumanSpritePubManager;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.target.manager.HumanTargetManager;
import com.hifun.soul.gameserver.technology.manager.HumanTechnologyManager;
import com.hifun.soul.gameserver.timetask.manager.HumanTimeTaskManager;
import com.hifun.soul.gameserver.title.manager.HumanTitleManager;
import com.hifun.soul.gameserver.training.manager.HumanTrainingManager;
import com.hifun.soul.gameserver.turntable.manager.HumanTurntableManager;
import com.hifun.soul.gameserver.vip.HumanVipManager;
import com.hifun.soul.gameserver.warrior.manager.HumanWarriorManager;
import com.hifun.soul.gameserver.yellowvip.manager.HumanYellowVipManager;

/**
 * 
 * 玩家角色对象
 * 
 * @author magicstone
 * 
 */
public class Human extends Role<HumanPropertyManager> implements
		ICachableComponent, IBattleUnit {
	/** 角色对应的玩家对象 */
	private volatile Player player;
	private long humanGuid;
	/** 角色类型 */
	private Occupation occupation;
	/** 角色任务管理器 */
	private HumanQuestManager humanQuestManager;
	/** 角色建筑管理器 */
	private HumanBuildingManager humanBuildingManager;
	/** 角色神魄管理器 */
	private HumanGodsoulManager humanGodsoulManager;
	/** 角色背包管理器 */
	private HumanBagManager humanBagManager;
	/** cd管理器 */
	private HumanCdManager humanCdManager;
	/** 定时任务管理器 */
	private HumanTimeTaskManager humanTimeTaskManager;
	/** 钱包(货币管理器) */
	private IWallet wallet;
	/** 税收管理器 */
	private HumanLevyManager levyManager;
	/** 好友管理器 */
	private HumanFriendManager humanFriendManager;
	/** 邮件管理器 */
	private HumanMailManager humanMailManager;
	/** 星运管理器 */
	private HumanHoroscopeManager humanHoroscopeManager;
	/** 宝石管理器 */
	private HumanGemManager humanGemManager;
	/** 科技管理器 */
	private HumanTechnologyManager humanTechnologyManager;
	/** 训练管理器 */
	private HumanTrainingManager humanTrainingManager;
	/** 魔晶兑换管理器 */
	private HumanCrystalExchangeManager humanCrystalExchangeManager;
	/** 连续登陆奖励 */
	private HumanLoginRewardManager humanLoginRewardManager;
	/** 在线奖励 */
	private HumanOnlineRewardManager humanOnlineRewardManager;
	/** 大转盘抽奖 */
	private HumanTurntableManager humanTurntableManager;
	/** 新手引导 */
	private HumanGuideManager humanGuideManager;
	/** 玩家技能管理器 */
	private HumanSkillManager skillManager;
	/** 关卡管理器 */
	private HumanStageManager humanStageManager;
	/** 防沉迷管理器 */
	private HumanAntiIndulgeManager humanAntiIndulgeManager;
	/** 活动管理器 */
	private HumanActivityManager humanActivityManager;
	/** 升级管理器 */
	private HumanLevelUpManager humanLevelUpManager;
	/** 消费通知管理器 */
	private HumanCostNotifyManager humanCostNotifyManager;
	/** 竞技场管理器 */
	private HumanArenaManager humanArenaManager;
	/** 冥想管理器 */
	private HumanMeditationManager humanMeditationManager;
	/** boss战管理器 */
	private HumanBossManager humanBossManager;
	/** VIP管理器 */
	private HumanVipManager humanVipManager;
	/** 精英副本管理器 */
	private HumanEliteStageManager humanEliteStageManager;
	/** 功能开放管理器 */
	private HumanFunctionManager humanFunctionManager;
	/** 矿场管理器 */
	private HumanMineManager humanMineManager;
	/** 玩家神秘商店管理器 */
	private HumanSpecialShopManager humanSpecialShopManager;
	/** 角色等级排行管理器 */
	private HumanLevelRankManager humanLevelRankManager;
	/** 角色军衔排行管理器 */
	private HumanTitleRankManager humanTitleRankManager;
	/** 角色荣誉排行管理器 */
	private HumanHonorRankManager humanHonorRankManager;
	/** 角色VIP排行管理器 */
	private HumanVipRankManager humanVipRankManager;
	/** 小助手管理器 */
	private HumanHelperManager humanHelperManager;
	/** 扫荡管理器 */
	private HumanAutoBattleManager humanAutoBattleManager;
	/** 天赋管理器 */
	private HumanGiftManager humanGiftManager;
	/** 培养管理器 */
	private HumanFosterManager humanFosterManager;
	/** 装备洗炼管理器 */
	private HumanForgeManager humanForgeManager;
	/** 试炼管理器 */
	private HumanRefineManager humanRefineManager;
	/** 匹配战管理器 */
	private HumanMatchBattleManager humanMatchBattleManager;
	/** 封测奖励管理器 */
	private HumanBetaRewardManager humanBetaRewardManager;
	/** 勇者之路管理器 */
	private HumanWarriorManager humanWarriorManager;
	/** qq黄钻管理器 */
	private HumanYellowVipManager yellowVipManager;
	/** qq充值管理器 */
	private HumanRechargeTxManager humanRechargeTxManager;
	/** 角色军衔管理器 */
	private HumanTitleManager humanTitleManager;
	/** 角色军团管理器 */
	private HumanLegionManager humanLegionManager;
	/** 角色精灵酒馆管理器 */
	private HumanSpritePubManager humanSpritePubManager;
	/** 角色角斗场管理器 */
	private HumanAbattoirManager humanAbattoirManager;
	/** 角色精良管理器 */
	private HumanSpriteManager humanSpriteManager;
	/** 角色嗜血神殿管理器 */
	private HumanBloodTempleManager humanBloodTempleManager;
	/** 角色星座管理器 */
	private HumanSignManager humanSignManager;
	/** 角色充值管理器 */
	private HumanRechargeManager humanRechargeManager;
	/** 角色战俘营管理器 */
	private HumanPrisonManager humanPrisonManager;
	/** 角色战神之巅管理器 */
	private HumanMarsManager humanMarsManager;
	/** 角色个人目标管理器 */
	private HumanTargetManager humanTargetManager;
	/** 角色军团boss战管理器 */
	private HumanLegionBossManager humanLegionBossManager;
	/** 军团矿战管理器 */
	HumanLegionMineWarManager humanLegionMineWarManager;
	/** 角色秘药管理器 */
	HumanNostrumManager humanNostrumManager;
	/** 角色押运管理器 */
	HumanEscortManager humanEscortManager;
	/** 角色预见管理器 */
	HumanPredictManager humanPredictManager;

	/** 事件总线 */
	private IEventBus eventBus;
	/** 角色身上所有持久化需求的管理器 */
	private List<IHumanPersistenceManager> needPersistenceManagers = new ArrayList<IHumanPersistenceManager>();
	/** 角色身上所有有缓存需求的管理器 */
	private List<ICachableComponent> needCacheManagers = new ArrayList<ICachableComponent>();
	/** 所有需要在登陆时特殊处理的管理器 */
	private Set<ILoginManager> needLoginManagers = new HashSet<ILoginManager>();
	/** 所有需要在登陆时添加提示特效的管理器 */
	private Set<INotifyManager> needNotifyManagers = new HashSet<INotifyManager>();

	public Human(Player player) {
		eventBus = new EventManager();
		this.player = player;
		// 先初始化功能管理器，保证登陆时先下发功能列表
		humanFunctionManager = new HumanFunctionManager(this);
		propertyManager = new HumanPropertyManager(this);
		humanQuestManager = new HumanQuestManager(this);
		humanBuildingManager = new HumanBuildingManager(this);
		humanGodsoulManager = new HumanGodsoulManager(this);
		humanBagManager = new HumanBagManager(this);
		humanCdManager = new HumanCdManager(this);
		wallet = new Wallet(this);
		levyManager = new HumanLevyManager(this);
		humanFriendManager = new HumanFriendManager(this);
		humanMailManager = new HumanMailManager(this);
		humanHoroscopeManager = new HumanHoroscopeManager(this);
		humanGemManager = new HumanGemManager(this);
		humanTechnologyManager = new HumanTechnologyManager(this);
		humanTrainingManager = new HumanTrainingManager(this);
		humanCrystalExchangeManager = new HumanCrystalExchangeManager(this);
		humanLoginRewardManager = new HumanLoginRewardManager(this);
		humanOnlineRewardManager = new HumanOnlineRewardManager(this);
		humanTurntableManager = new HumanTurntableManager(this);
		humanGuideManager = new HumanGuideManager(this);
		skillManager = new HumanSkillManager(this);
		humanStageManager = new HumanStageManager(this);
		humanAntiIndulgeManager = new HumanAntiIndulgeManager(this);
		humanLevelUpManager = new HumanLevelUpManager(this);
		humanCostNotifyManager = new HumanCostNotifyManager(this);
		humanArenaManager = new HumanArenaManager(this);
		humanMeditationManager = new HumanMeditationManager(this);
		humanBossManager = new HumanBossManager(this);
		humanVipManager = new HumanVipManager(this);
		humanEliteStageManager = new HumanEliteStageManager(this);
		humanMineManager = new HumanMineManager(this);
		humanSpecialShopManager = new HumanSpecialShopManager(this);
		humanLevelRankManager = new HumanLevelRankManager(this);
		humanTitleRankManager = new HumanTitleRankManager(this);
		humanHonorRankManager = new HumanHonorRankManager(this);
		humanVipRankManager = new HumanVipRankManager(this);
		humanAutoBattleManager = new HumanAutoBattleManager(this);
		humanGiftManager = new HumanGiftManager(this);
		humanFosterManager = new HumanFosterManager(this);
		humanForgeManager = new HumanForgeManager(this);
		humanRefineManager = new HumanRefineManager(this);
		humanMatchBattleManager = new HumanMatchBattleManager(this);
		humanBetaRewardManager = new HumanBetaRewardManager(this);
		humanWarriorManager = new HumanWarriorManager(this);
		yellowVipManager = new HumanYellowVipManager(this);
		humanRechargeTxManager = new HumanRechargeTxManager(this);
		humanTitleManager = new HumanTitleManager(this);
		humanLegionManager = new HumanLegionManager(this);
		humanSpritePubManager = new HumanSpritePubManager(this);
		humanAbattoirManager = new HumanAbattoirManager(this);
		humanSpriteManager = new HumanSpriteManager(this);
		humanBloodTempleManager = new HumanBloodTempleManager(this);
		humanSignManager = new HumanSignManager(this);
		humanRechargeManager = new HumanRechargeManager(this);
		humanPrisonManager = new HumanPrisonManager(this);
		humanMarsManager = new HumanMarsManager(this);
		humanTargetManager = new HumanTargetManager(this);
		humanLegionBossManager = new HumanLegionBossManager(this);
		humanLegionMineWarManager = new HumanLegionMineWarManager(this);
		humanNostrumManager = new HumanNostrumManager(this);
		humanEscortManager = new HumanEscortManager(this);
		humanPredictManager = new HumanPredictManager(this);
		// 初始化时需要其他相关管理器已经初始化完成
		humanActivityManager = new HumanActivityManager(this);
		// 初始化时需要其他相关管理器已经初始化完成
		humanHelperManager = new HumanHelperManager(this);
		// TODO: cfh timeTaskManager依赖于其他管理器，放到最后.结构调整？
		humanTimeTaskManager = new HumanTimeTaskManager(this);
	}

	/**
	 * 发送GC消息;
	 * 
	 * @param msg
	 */
	@Override
	public void sendMessage(IMessage msg) {
		if (player == null) {
			return;
		}
		player.sendMessage(msg);
	}

	@Override
	public void heartBeat() {
		// 心跳的时候属性同步
		propertyManager.heartBeat();
		// 心跳时定时任务执行判断
		humanTimeTaskManager.onHeartBeat();
	}

	/**
	 * 发送系统消息
	 * 
	 * @param systemMessageType
	 *            系统消息类型枚举
	 * @param key
	 *            多语言键值Id
	 */
	private void sendSystemMessage(SystemMessageType systemMessageType,
			Integer key) {
		String content = GameServerAssist.getSysLangService().read(key);
		player.sendSystemMessage(systemMessageType, content);
	}

	/**
	 * 发送系统消息
	 * 
	 * @param systemMessageType
	 *            系统消息类型枚举
	 * @param key
	 *            多语言键值Id
	 * @param args
	 *            参数
	 */
	private void sendSystemMessage(SystemMessageType systemMessageType,
			Integer key, Object... args) {
		String content = GameServerAssist.getSysLangService().read(key, args);
		player.sendSystemMessage(systemMessageType, content);
	}

	public void sendGenericMessage(Integer key) {
		sendSystemMessage(SystemMessageType.GENERIC, key);
	}

	public void sendGenericMessage(Integer key, Object... args) {
		sendSystemMessage(SystemMessageType.GENERIC, key, args);
	}

	public void sendErrorMessage(Integer key) {
		sendSystemMessage(SystemMessageType.ERROR, key);
	}

	public void sendErrorMessage(Integer key, Object... args) {
		sendSystemMessage(SystemMessageType.ERROR, key, args);
	}

	public void sendWarningMessage(Integer key) {
		sendSystemMessage(SystemMessageType.WARNING, key);
	}

	public void sendWarningMessage(Integer key, Object... args) {
		sendSystemMessage(SystemMessageType.WARNING, key, args);
	}

	public void sendImportantMessage(Integer key) {
		sendSystemMessage(SystemMessageType.IMPORTANT, key);
	}

	public void sendImportantMessage(Integer key, Object... args) {
		sendSystemMessage(SystemMessageType.IMPORTANT, key, args);
	}

	public void sendSuccessMessage(Integer key) {
		sendSystemMessage(SystemMessageType.SUCCESS, key);
	}

	public void sendSuccessMessage(Integer key, Object... args) {
		sendSystemMessage(SystemMessageType.SUCCESS, key, args);
	}

	/**
	 * 发送系统消息到聊天框区域
	 * 
	 * @param key
	 * @param args
	 */
	public void sendChatMessage(Integer key) {
		sendSystemMessage(SystemMessageType.CHAT, key);
	}

	/**
	 * 发送系统消息到聊天框区域
	 * 
	 * @param key
	 * @param args
	 */
	public void sendChatMessage(Integer key, Object... args) {
		sendSystemMessage(SystemMessageType.CHAT, key, args);
	}

	public Player getPlayer() {
		return player;
	}

	public int getLevel() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.LEVEL);
	}

	/**
	 * 获取vip等级（真实vip等级与使用体验卡所获得的vip等级两者中的较大者）
	 * 
	 * @return
	 */
	public int getVipLevel() {
		return humanVipManager.getVipLevel();
	}

	/**
	 * 玩家充值金额
	 * 
	 * @return
	 */
	public int getRechargeNum() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.RECHARGED_NUM);
	}

	public int getCoin() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.COIN);
	}

	public void setCoin(int coin) {
		if (coin > SharedConstants.MAX_HUMAN_MONEY) {
			this.sendErrorMessage(LangConstants.REACH_TO_MAX, GameServerAssist
					.getSysLangService().read(LangConstants.COIN));
			this.propertyManager.getIntPropertySet(
					PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
					HumanIntProperty.COIN, SharedConstants.MAX_HUMAN_MONEY);
			return;
		}
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.COIN, coin);
	}

	public int getCrystal() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.CRYSTAL);
	}

	public boolean isTitleGetSalary() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.CRYSTAL) == 0;
	}

	public void setCrystal(int crystal) {
		if (crystal > SharedConstants.MAX_HUMAN_MONEY) {
			this.sendErrorMessage(LangConstants.REACH_TO_MAX, GameServerAssist
					.getSysLangService().read(LangConstants.CRYSTAL));
			propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(HumanIntProperty.CRYSTAL,
							SharedConstants.MAX_HUMAN_MONEY);
			return;
		}
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.CRYSTAL, crystal);
	}

	/**
	 * 获取玩家的经验值
	 * 
	 * @return
	 */
	public int getExperience() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.EXPERIENCE);
	}

	/**
	 * 设置玩家的经验值
	 * 
	 * @param experience
	 * @param notify
	 *            TODO
	 */
	public void addExperience(int experience, boolean notify,
			ExperienceLogReason reason, String param) {
		// 之前是否到满级
		if (getLevel() >= SharedConstants.MAX_HUMAN_LEVEL) {
			return;
		}
		if (experience < 0) {
			throw new IllegalArgumentException(
					"Experience must be larger than 0, experience: "
							+ experience);
		}
		if (experience == 0) {
			return;
		}
		int addExp = experience;
		experience += getExperience();
		if (experience < 0) {
			throw new IllegalStateException("Add experience error");
		}
		while (experience >= this.getLevelExp()) {
			// 消耗经验
			experience -= this.getLevelExp();
			// 升级动作
			humanLevelUpManager.onLevelUp();
		}
		// FIXME: crazyjohn 此时越界了,超过了Integer.MaxValue 需要谨慎处理;
		if (this.getExperience() > SharedConstants.MAX_EXP) {
			setExperience(SharedConstants.MAX_EXP);
		} else {
			setExperience(experience);
		}
		// 发送属性变更日志
		GameServerAssist.getLogService().sendExperienceLog(this, reason, param,
				addExp, experience);
		if (notify) {
			// 发送浮动提示消息
			String langExp = GameServerAssist.getSysLangService().read(
					LangConstants.EXPERIENCE);
			String operate = experience >= 0 ? "+" : "-";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, addExp, langExp,
					operate);
		}
		// 之后是否到满级
		if (getLevel() >= SharedConstants.MAX_HUMAN_LEVEL) {
			// 置零
			setExperience(SharedConstants.MAX_HUMAN_LEVEL_EXP);
			return;
		}
	}

	public void setExperience(int experience) {
		if (experience > SharedConstants.MAX_HUMAN_EXP) {
			this.sendErrorMessage(LangConstants.REACH_TO_MAX, GameServerAssist
					.getSysLangService().read(LangConstants.EXPERIENCE));
			propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(HumanIntProperty.EXPERIENCE,
							SharedConstants.MAX_HUMAN_EXP);
			return;
		}
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.EXPERIENCE, experience);
	}

	/**
	 * 升级到下一级需要的exp;
	 * 
	 * @return
	 */
	public int getLevelExp() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.MAX_EXPERIENCE);
	}

	public void setLevel(int level) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.LEVEL, level);
	}

	public int getUnDistributePropertyPoint() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.UNDISTRIBUTED_POINT);
	}

	public void setUnDistributePropertyPoint(int propertyPoint) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.UNDISTRIBUTED_POINT,
						propertyPoint);
	}

	public int getPower() {
		return propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.getPropertyValue(Level1Property.FIRE);
	}

	public void setPower(int power) {
		propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.setPropertyValue(Level1Property.FIRE, power);
	}

	public int getSystemPower() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SYSTEM_FIRE);
	}

	public void setSystemPower(int power) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SYSTEM_FIRE, power);
	}

	public int getAgile() {
		return propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.getPropertyValue(Level1Property.ICE);
	}

	public void setAgile(int agile) {
		propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.setPropertyValue(Level1Property.ICE, agile);
	}

	public int getSystemAgile() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SYSTEM_ICE);
	}

	public void setSystemAgile(int agile) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SYSTEM_ICE, agile);
	}

	public int getStamina() {
		return propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.getPropertyValue(Level1Property.LIGHT);
	}

	public void setStamina(int stamina) {
		propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.setPropertyValue(Level1Property.LIGHT, stamina);
	}

	public int getSystemtStamina() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SYSTEM_LIGHT);
	}

	public void setSystemStamina(int stamina) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SYSTEM_LIGHT, stamina);
	}

	public int getIntelligence() {
		return propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.getPropertyValue(Level1Property.SHADOW);
	}

	public void setIntelligence(int intelligence) {
		propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.setPropertyValue(Level1Property.SHADOW, intelligence);
	}

	public int getSystemIntelligence() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SYSTEM_SHADOW);
	}

	public void setSystemIntelligence(int intelligence) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SYSTEM_SHADOW, intelligence);
	}

	public int getSpirit() {
		return propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.getPropertyValue(Level1Property.NATURE);
	}

	public void setSpirit(int spirit) {
		propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
				.setPropertyValue(Level1Property.NATURE, spirit);
	}

	public int getSystemSpirit() {
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SYSTEM_NATURE);
	}

	public void setSystemSpirit(int spirit) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SYSTEM_NATURE, spirit);
	}

	public long getLastChatTime() {
		return propertyManager
				.getLongPropertyValue(HumanLongProperty.LAST_CHAT_TIME);
	}

	public void setLastChatTime(long lastChatTime) {
		propertyManager.setLongPropertyValue(HumanLongProperty.LAST_CHAT_TIME,
				lastChatTime);
	}

	public String toLogString() {
		return new StringBuilder().append("[").append("name=")
				.append(getName()).append(", guid=").append(getHumanGuid())
				.append("]").toString();
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
		this.setId(humanGuid);
	}

	public HumanPropertyManager getHumanPropertiesManager() {
		return propertyManager;
	}

	public HumanQuestManager getHumanQuestManager() {
		return humanQuestManager;
	}

	public HumanBuildingManager getHumanBuildingManager() {
		return humanBuildingManager;
	}

	public IEventBus getEventBus() {
		return eventBus;
	}

	public HumanBagManager getBagManager() {
		return this.humanBagManager;
	}

	public HumanCdManager getHumanCdManager() {
		return humanCdManager;
	}

	public HumanTimeTaskManager getHumanTimeTaskManager() {
		return humanTimeTaskManager;
	}

	public HumanFriendManager getHumanFriendManager() {
		return humanFriendManager;
	}

	public HumanHoroscopeManager getHumanHoroscopeManager() {
		return humanHoroscopeManager;
	}

	public HumanTechnologyManager getHumanTechnologyManager() {
		return humanTechnologyManager;
	}

	public HumanLoginRewardManager getHumanLoginRewardManager() {
		return humanLoginRewardManager;
	}

	public HumanOnlineRewardManager getHumanOnlineRewardManager() {
		return humanOnlineRewardManager;
	}

	public HumanTurntableManager getHumanTurntableManager() {
		return humanTurntableManager;
	}

	public HumanGuideManager getHumanGuideManager() {
		return humanGuideManager;
	}

	public HumanStageManager getHumanStageManager() {
		return humanStageManager;
	}

	public HumanCostNotifyManager getHumanCostNotifyManager() {
		return humanCostNotifyManager;
	}

	public HumanForgeManager getHumanForgeManager() {
		return humanForgeManager;
	}

	public HumanRefineManager getHumanRefineManager() {
		return humanRefineManager;
	}

	public HumanRechargeTxManager getHumanRechargeTxManager() {
		return humanRechargeTxManager;
	}

	public HumanTitleManager getHumanTitleManager() {
		return humanTitleManager;
	}

	public HumanLegionManager getHumanLegionManager() {
		return humanLegionManager;
	}

	public HumanGodsoulManager getHumanGodsoulManager() {
		return humanGodsoulManager;
	}

	public void setHumanGodsoulManager(HumanGodsoulManager humanGodsoulManager) {
		this.humanGodsoulManager = humanGodsoulManager;
	}

	public HumanAbattoirManager getHumanAbattoirManager() {
		return humanAbattoirManager;
	}

	public HumanBloodTempleManager getHumanBloodTempleManager() {
		return humanBloodTempleManager;
	}

	public HumanRechargeManager getHumanRechargeManager() {
		return humanRechargeManager;
	}
	
	public HumanPrisonManager getHumanPrisonManager() {
		return humanPrisonManager;
	}

	public HumanMarsManager getHumanMarsManager() {
		return humanMarsManager;
	}
	
	/**
	 * 获取钱包（货币管理器）
	 * 
	 * @return
	 */
	public IWallet getWallet() {
		return this.wallet;
	}

	/**
	 * 获取税收管理器
	 * 
	 * @return
	 */
	public HumanLevyManager getLevyManager() {
		return this.levyManager;
	}

	/**
	 * 获取邮件管理器
	 * 
	 * @return
	 */
	public HumanMailManager getHumanMailManager() {
		return humanMailManager;
	}

	/**
	 * 获取宝石收获管理器
	 * 
	 * @return
	 */
	public HumanGemManager getHumanGemManager() {
		return this.humanGemManager;
	}

	/**
	 * 训练管理器
	 * 
	 * @return
	 */
	public HumanTrainingManager getHumanTrainingManager() {
		return humanTrainingManager;
	}

	/**
	 * 获取魔晶兑换管理器
	 * 
	 * @return
	 */
	public HumanCrystalExchangeManager getHumanCrystalExchangeManager() {
		return humanCrystalExchangeManager;
	}

	/**
	 * 获取防沉迷管理器
	 * 
	 * @return
	 */
	public HumanAntiIndulgeManager getHumanAntiIndulgeManager() {
		return humanAntiIndulgeManager;
	}

	public HumanActivityManager getHumanActivityManager() {
		return this.humanActivityManager;
	}

	public HumanSkillManager getSkillManager() {
		return skillManager;
	}

	public HumanArenaManager getHumanArenaManager() {
		return humanArenaManager;
	}

	public HumanMeditationManager getHumanMeditationManager() {
		return this.humanMeditationManager;
	}

	public HumanBossManager getHumanBossManager() {
		return humanBossManager;
	}

	public HumanVipManager getHumanVipManager() {
		return this.humanVipManager;
	}

	public HumanEliteStageManager getHumanEliteStageManager() {
		return this.humanEliteStageManager;
	}

	public HumanFunctionManager getHumanFunctionManager() {
		return this.humanFunctionManager;
	}

	public HumanMineManager getHumanMineManager() {
		return this.humanMineManager;
	}

	public HumanSpecialShopManager getHumanSpecialShopManager() {
		return this.humanSpecialShopManager;
	}

	public HumanLevelRankManager getHumanLevelRankManager() {
		return humanLevelRankManager;
	}

	public HumanTitleRankManager getHumanTitleRankManager() {
		return humanTitleRankManager;
	}

	public HumanHonorRankManager getHumanHonorRankManager() {
		return humanHonorRankManager;
	}

	public HumanVipRankManager getHumanVipRankManager() {
		return humanVipRankManager;
	}

	public HumanHelperManager getHumanHelperManager() {
		return humanHelperManager;
	}

	public HumanAutoBattleManager getHumanAutoBattleManager() {
		return humanAutoBattleManager;
	}

	public HumanGiftManager getHumanGiftManager() {
		return humanGiftManager;
	}

	public HumanFosterManager getHumanFosterManager() {
		return humanFosterManager;
	}

	public HumanMatchBattleManager getHumanMatchBattleManager() {
		return this.humanMatchBattleManager;
	}

	public HumanBetaRewardManager getHumanBetaRewardManager() {
		return humanBetaRewardManager;
	}

	public HumanWarriorManager getHumanWarriorManager() {
		return humanWarriorManager;
	}

	public HumanYellowVipManager getHumanYellowVipManager() {
		return yellowVipManager;
	}

	public HumanTargetManager getHumanTargetManager() {
		return humanTargetManager;
	}

	public HumanLegionBossManager getHumanLegionBossManager() {
		return humanLegionBossManager;
	}

	public HumanLegionMineWarManager getHumanLegionMineWarManager() {
		return humanLegionMineWarManager;
	}

	public HumanNostrumManager getHumanNostrumManager() {
		return humanNostrumManager;
	}

	public HumanEscortManager getHumanEscortManager() {
		return humanEscortManager;
	}

	public HumanPredictManager getHumanPredictManager() {
		return humanPredictManager;
	}
	
	public Set<INotifyManager> getNeedNotifyManagers() {
		return needNotifyManagers;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> returnList = new ArrayList<IEntity>();
		// 添加需要更新的缓存数据
		for (ICachableComponent component : this.needCacheManagers) {
			List<IEntity> updateEntity = component.getUpdateEntities();
			if (updateEntity == null) {
				continue;
			}
			returnList.addAll(updateEntity);
		}
		return returnList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> returnList = new ArrayList<IEntity>();
		// 添加需要删除的缓存数据
		for (ICachableComponent component : this.needCacheManagers) {
			List<IEntity> deleteEntity = component.getDeleteEntities();
			if (deleteEntity == null) {
				continue;
			}
			returnList.addAll(deleteEntity);
		}
		return returnList;
	}

	/**
	 * <font color='red'><b>注意, 如果在子业务系统的onLoad的时候调用db服务, 请仔细阅读;</b></font>
	 * <p>
	 * 为了避免并发异常, 如果角色子业务系统是分开表存储的, 也就是不在t_human里以大字段存储, 请注意<br>
	 * 1. 如果需要在进场景前就构造好数据, 请参考{@code HumanQueryDecorator} 的注释;<br>
	 * 2. 其它情况, 例如延时加载的可以自己处理;<br>
	 * 3. 请服务端人员仔细考虑;<br>
	 * 
	 * @param result
	 */
	public void onLoad(HumanEntity result) {
		// 从数据库实体初始化角色所有管理器信息
		setHumanGuid(result.getId());
		this.setName(result.getName());
		Occupation occupation = Occupation.typeOf(result.getBuilder()
				.getBaseProperties().getOccupation());
		if (occupation != null) {
			this.setOccupation(occupation);
		}
		for (IHumanPersistenceManager manager : this.needPersistenceManagers) {
			manager.onLoad(result);
		}
	}

	public void onLogin() {
		// 登录之前执行一次角色身上的定时任务逻辑
		this.humanTimeTaskManager.onHeartBeat();
		for (ILoginManager manager : this.needLoginManagers) {
			manager.onLogin();
		}
		long now = GameServerAssist.getSystemTimeService().now();
		propertyManager.setLongPropertyValue(HumanLongProperty.LOGIN_TIME, now);
		// 发送登陆日志
		GameServerAssist.getLogService().sendBasicPlayerLog(this,
				BasicPlayerLogReason.REASON_NORMAL_LOGIN, "",
				this.getPlayer().getSession().getIp(), this.getCrystal(),
				this.getCoin(), this.getExperience(), this.getEnergy(), 0);
		GameServerAssist.getLogService().sendOnlineTimeLog(this,
				OnlineTimeLogReason.GM_KICK, "", 0, 0, now, now);

	}

	public void registerPersistenceManager(IHumanPersistenceManager subManager) {
		this.needPersistenceManagers.add(subManager);
	}

	public void registerCachableManager(ICachableComponent component) {
		this.needCacheManagers.add(component);
	}

	public void registerLoginManager(ILoginManager loginManager) {
		this.needLoginManagers.add(loginManager);
	}
	
	public void addNotifyManager(INotifyManager notifyManager) {
		this.needNotifyManagers.add(notifyManager);
	}

	/**
	 * 持久化动作;
	 */
	public HumanEntity onPersistence() {
		HumanEntity humanEntity = new HumanEntity();

		humanEntity.setId(getHumanGuid());
		humanEntity.setPassportId(this.getPlayer().getPassportId());
		for (IHumanPersistenceManager manager : this.needPersistenceManagers) {
			manager.onPersistence(humanEntity);
		}
		return humanEntity;
	}

	public int getEnergy() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.ENERGY);
	}

	public void setEnergy(int energy, EnergyLogReason reason, String param) {
		IntPropertyCacheSet propertySet = this.propertyManager
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY);
		int oldValue = propertySet.getPropertyValue(HumanIntProperty.ENERGY);
		propertySet.setPropertyValue(HumanIntProperty.ENERGY, energy);
		GameServerAssist.getLogService().sendEnergyLog(this, reason, param,
				energy - oldValue, energy);
	}

	public int getHomeLevel() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.HOME_LEVEL);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation roleType) {
		this.occupation = roleType;
	}

	public int getDailyQuestScore() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.DAILY_SCORE);
	}

	public void setDailyQuestScore(int score) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.DAILY_SCORE, score);
	}

	public int getArenaBattleTime() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.ARENA_BATTLE_TIME);
	}

	public void setArenaBattleTime(int arenaBattleTime) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.ARENA_BATTLE_TIME,
						arenaBattleTime);
	}

	public int getArenaHonor() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.ARENA_HONOR);
	}

	public void setArenaHonor(int arenaHonour) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.ARENA_HONOR, arenaHonour);
	}

	public int getCurrentTitle() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.CURRENT_TITLE);
	}

	public void setCurrentTitle(int currentTitle) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.CURRENT_TITLE, currentTitle);
	}

	public int getTitleSkillNum() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.TITLE_SKILL_NUM);
	}

	public void setTitleSkillNum(int skillNum) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.TITLE_SKILL_NUM, skillNum);
	}

	public boolean getTitleIsGotSalary() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.TITLE_IS_GOT_SALARY) == 1;
	}

	public void setTitleIsGotSalary(boolean isGotSalary) {
		int titleIsGotSalary = isGotSalary ? 1 : 0;
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.TITLE_IS_GOT_SALARY,
						titleIsGotSalary);
	}

	public int getAbattoirBuyNum() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.ABATTOIR_BUY_NUM);
	}

	public void setAbattoirBuyNum(int buyNum) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.ABATTOIR_BUY_NUM, buyNum);
	}

	public int getAbattoirRemainNum() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.ABATTOIR_REMAIN_NUM);
	}

	public void setAbattoirRemainNum(int remainNum) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.ABATTOIR_REMAIN_NUM,
						remainNum);
	}

	public int getBloodTempleBuyNum() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.BLOOD_TEMPLE_BUY_NUM);
	}

	public void setBloodTempleBuyNum(int buyNum) {
		this.propertyManager
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.BLOOD_TEMPLE_BUY_NUM, buyNum);
	}

	public int getBloodTempleRemainNum() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.BLOOD_TEMPLE_REMAIN_NUM);
	}

	public void setBloodTempleRemainNum(int remainNum) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.BLOOD_TEMPLE_REMAIN_NUM,
						remainNum);
	}

	public void addArenaHonor(int addHonor, boolean notify,
			HonourLogReason reason, String param) {
		if (addHonor <= 0) {
			return;
		}
		// 军衔荣誉上限
		int maxHonor = getTitleMaxHonor();
		if (getArenaHonor() >= maxHonor) {
			return;
		}
		int honor = getArenaHonor() + addHonor;
		if (honor > maxHonor) {
			honor = maxHonor;
			addHonor = maxHonor - getArenaHonor();
		}
		setArenaHonor(honor);
		if (notify) {
			// 发送浮动提示消息
			String langHonor = GameServerAssist.getSysLangService().read(
					LangConstants.HONOR);
			String operate = addHonor >= 0 ? "+" : "-";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, addHonor,
					langHonor, operate);
		}
		// 发送属性变更日志
		GameServerAssist.getLogService().sendHonourLog(this, reason, param,
				addHonor, honor);
		// 荣誉值增加，会给所在军团增加经验值
		GameServerAssist.getGlobalLegionManager().addExperienceByHonor(this,
				addHonor);
		// 荣誉值增加，荣誉排行榜数据更新
		this.humanHonorRankManager.updateHonorRankData();
	}

	/**
	 * 获取角色当前军衔的荣誉上限
	 */
	public int getTitleMaxHonor() {
		return GameServerAssist.getTitleTemplateManager().getTitleMaxHonor(getCurrentTitle());
	}
	
	public int getSkillPoints() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SKILL_POINTS);
	}

	public void setSkillPoints(int skillPoints) {
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SKILL_POINTS, skillPoints);
	}

	public void addSkillPoint(int skillPoint, boolean notify,
			SkillPointLogReason reason, String param) {
		int point = getSkillPoints() + skillPoint;
		setSkillPoints(point);
		if (notify && skillPoint > 0) {
			// 发送浮动提示消息
			String langSkillPoint = GameServerAssist.getSysLangService().read(
					LangConstants.SKILLPOINT);
			String operate = skillPoint >= 0 ? "+" : "";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, skillPoint,
					langSkillPoint, operate);
		}
		// 发送属性变更日志
		GameServerAssist.getLogService().sendSkillPointLog(this, reason, param,
				skillPoint, point);
	}

	/**
	 * 获取最后一次登出时间;
	 * 
	 * @return
	 */
	public long getLastLogoutTime() {
		return this.propertyManager
				.getLongPropertyValue(HumanLongProperty.LAST_LOGOUT_TIME);
	}

	public void setLastLogoutTime(long time) {
		this.propertyManager.setLongPropertyValue(
				HumanLongProperty.LAST_LOGOUT_TIME, time);
	}

	public int getHp() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.MAX_HP);
	}

	public void setHp(int hp) {
		propertyManager.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.setPropertyValue(Level2Property.MAX_HP, hp);
	}

	@Override
	public void enterBattleState() {
		this.player.transferStateTo(PlayerState.BATTLING);
	}

	@Override
	public void exitBattleState() {
		this.player.transferStateTo(PlayerState.GAMEING);
	}

	@Override
	public void onNormalActionInvalid(int row1, int col1, int row2, int col2) {
		// 移动非法, 可能是外挂绕过client检查
		if (this.context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 关掉, 认为是外挂
		// battle.onBattleUnitExit();
		// 打印出此时的棋盘快照
		Loggers.Battle_Logger.error("Battle snap: \n" + battle.toString());
		// 关闭连接
		Loggers.Battle_Logger.error("Invalid move,(" + row1 + "," + col1 + ")("
				+ row2 + "," + col2 + ") humanGuid: " + humanGuid + ", name: "
				+ this.getName());
		context.getInvalidMoveHandler().handleInvalidAction(this);
	}

	@Override
	public void onExitBattle() {
		// 切换状态
		this.player.transferStateTo(PlayerState.GAMEING);
		// 销毁战斗上下文
		this.context = null;
		this.resetFinishActionState();
	}

	@Override
	public boolean isInBattleState() {
		if (this.player == null) {
			// 此时玩家已经推出登录了
			return false;
		}
		return PlayerState.isInBattleState(this.getPlayer().getState());
	}

	@Override
	public ISkill getComboAttackSkill() {
		return this.skillManager.getComboAttackSkill();
	}

	@Override
	public ISkill getNormalAttackSkill() {
		return this.skillManager.getNormalAttackSkill();
	}

	@Override
	public int getUnitModelId() {
		return GameServerAssist.getOccupationTemplateManager()
				.getOccupationTemplateByOccupation(this.occupation)
				.getResourceId();
	}

	@Override
	public List<ISkill> getCarriedSkills() {
		List<ISkill> skills = super.getCarriedSkills();
		skills.addAll(this.skillManager.getCarriedSkills());
		return skills;
	}

	/**
	 * 获取带链接的角色名
	 * 
	 * @return
	 */
	public String getLinkedName() {
		return RichTextHelper.addLink(this.getName(),
				LinkType.CHARACTER.getIndex(), Long.toHexString(humanGuid),
				Long.toHexString(humanGuid), this.getName());
	}

	public HumanGuarder getBattleGuarder() {
		HumanGuarder guarder = new HumanGuarder(this.humanGuid,
				this.propertyManager, this.skillManager);
		guarder.setName(this.getName());
		return guarder;
	}

	@Override
	public void notifyAction() {
		// 判断是否有需要做的战斗引导
		this.getHumanGuideManager().checkGuide();
		super.notifyAction();
		// 是否在托管状态
		if (this.getPlayer().getState() == PlayerState.HOSTING_BATTLING) {
			// ai动作
			this.battleAI.action(BattleUtil.getHostingHumanThinkTimes());
		}
	}

	/**
	 * 进行一次战斗行动;
	 */
	public void doBattleAction() {
		this.battleAI.action(SharedConstants.BATTLE_MONSTER_THINK_TIMES);
	}

	@Override
	public RoleType getRoleType() {
		return RoleType.HUMAN;
	}

	/**
	 * 是否达到顶级
	 * 
	 * @return
	 */
	public boolean maxLevelReached() {
		return humanLevelUpManager.maxLevelReached();
	}

	public IBattleAI getBattleAI() {
		return this.battleAI;
	}

	@Override
	public int getUnitHeadId() {
		return this.getUnitModelId();
	}

	/**
	 * 找到释放这个技能还不够的魔法
	 * 
	 * @param skill
	 * @return
	 */
	public List<EnergyType> getNotEnoughEnergyTypes(ISkill skill) {
		List<EnergyType> energyTypes = new ArrayList<EnergyType>();
		for (MagicSlotInfo slot : this.slots) {
			int needValue = EnergyType.typeOf(slot.getEnergyType())
					.getTemplateEnergyValue(skill.getSkillTemplate());
			if (slot.getCurrentSize() < needValue) {
				energyTypes.add(EnergyType.typeOf(slot.getEnergyType()));
			}
		}
		return energyTypes;
	}

	@Override
	public int getDefaultActionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public HumanSpritePubManager getHumanSpritePubManager() {
		return humanSpritePubManager;
	}

	public HumanSpriteManager getHumanSpriteManager() {
		return humanSpriteManager;
	}

	/**
	 * 获取角色当前灵气值;
	 * 
	 * @return
	 */
	public int getAura() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.AURA);
	}

	public void setAura(int aura) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.AURA, aura);
	}
	
	/**
	 * 增加灵气值
	 */
	public void addAura(int addAura, boolean notify, AuraLogReason reason,
			String param) {
		if (addAura <= 0) {
			return;
		}
		setAura(getAura() + addAura);
		// 悬浮提示
		if (notify) {
			// 发送浮动提示消息
			String langDesc = GameServerAssist.getSysLangService().read(
					LangConstants.AURA);
			String operate = addAura >= 0 ? "+" : "-";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, addAura,
					langDesc, operate);
		}
	}
	
	/**
	 * 消耗灵气值
	 */
	public boolean costAura(int costAura, boolean notify, AuraLogReason reason,
			String param) {
		if (getAura() < costAura) {
			String langDesc = GameServerAssist.getSysLangService().read(
					LangConstants.AURA);
			sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, langDesc);
			return false;
		}
		setAura(getAura() - costAura);
		return true;
	}

	/**
	 * 获取角色当前精灵背包大小;
	 * 
	 * @return
	 */
	public int getSpriteBagSize() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.SPRITE_BAG_CELL_SIZE);
	}

	public void setSpriteBagSize(int size) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SPRITE_BAG_CELL_SIZE, size);
	}

	public HumanSignManager getHumanSignManager() {
		return humanSignManager;
	}

	/**
	 * 获取当前的星魂值;
	 * 
	 * @return
	 */
	public int getStarSoul() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.STAR_SOUL);
	}

	public void setStarSoul(int starSoul) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.STAR_SOUL, starSoul);
	}

	/**
	 * 增加星魂
	 */
	public void addStarSoul(int addStarSoul, boolean notify,
			StarSoulLogReason reason, String param) {
		if (addStarSoul <= 0) {
			return;
		}
		setStarSoul(getStarSoul() + addStarSoul);
		// 悬浮提示
		if (notify) {
			// 发送浮动提示消息
			String langDesc = GameServerAssist.getSysLangService().read(
					LangConstants.STAR_SOUL);
			String operate = addStarSoul >= 0 ? "+" : "-";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, addStarSoul,
					langDesc, operate);
		}
	}

	/**
	 * 消耗星魂
	 */
	public boolean costStarSoul(int costStarSoul, boolean notify,
			StarSoulLogReason reason, String param) {
		if (getStarSoul() < costStarSoul) {
			String langDesc = GameServerAssist.getSysLangService().read(
					LangConstants.STAR_SOUL);
			sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, langDesc);
			return false;
		}
		setStarSoul(getStarSoul() - costStarSoul);
		return true;
	}
	
	public int getFirstRechargeState() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.FIRST_RECHARGE_STATE);
	}

	public void setFirstRechargeState(int state) {
		this.propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.FIRST_RECHARGE_STATE, state);
	}

	public int getNormalRechargeTimes() {
		return this.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.NORMAL_RECHARGE_TIMES);
	}

	public void setNormalRechargeTimes(int normalRechargeTimes) {
		this.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.NORMAL_RECHARGE_TIMES,
						normalRechargeTimes);
	}

	public int getWeekTotalRechargeNum() {
		return this.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.WEEK_TOTAL_RECHARGE_NUM);
	}

	public void setWeekTotalRechargeNum(int weekTotalRechargeNum) {
		this.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.WEEK_TOTAL_RECHARGE_NUM,
						weekTotalRechargeNum);
	}

	public SkillDevelopType getskillDevelopType() {
		SkillDevelopType type = SkillDevelopType.typeOf(this
				.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.SKILL_DEVELOP_TYPE));
		if (type == null) {
			this.setSkillDevelopType(SkillDevelopType.GEM.getIndex());
			return SkillDevelopType.GEM;
		}
		return type;
	}

	public void setSkillDevelopType(int type) {
		this.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SKILL_DEVELOP_TYPE, type);
	}
	
	public int getPrestige() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.PRESTIGE);
	}
	
	public void setPrestige(int prestige) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.PRESTIGE, prestige);
	}
	
	/**
	 * 增加威望
	 */
	public void addPrestige(int addPrestige, boolean notify,
			PrestigeLogReason reason, String param) {
		if (addPrestige <= 0) {
			return;
		}
		setPrestige(getPrestige() + addPrestige);
		if (notify) {
			// 发送浮动提示消息
			String langPrestige = GameServerAssist.getSysLangService().read(
					LangConstants.PRESTIGE);
			String operate = addPrestige >= 0 ? "+" : "-";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, addPrestige,
					langPrestige, operate);
		}
		getHumanTitleManager().sendNotify();
	}
	
	public int getMarsTodayKillValue() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_TODAY_KILL_VALUE);
	}
	
	public void setMarsTodayKillValue(int killValue) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_TODAY_KILL_VALUE, killValue);
	}
	
	public int getMarsTodayKillCoin() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_TODAY_KILL_COIN);
	}
	
	public void setMarsTodayKillCoin(int killValue) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_TODAY_KILL_COIN, killValue);
	}
	
	public int getMarsRemainKillNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_REMAIN_KILL_NUM);
	}
	
	public void setMarsRemainKillNum(int remainKillNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_REMAIN_KILL_NUM, remainKillNum);
	}
	
	public int getMarsRemainMultipleNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_REMAIN_MULTIPLE_NUM);
	}
	
	public void setMarsRemainMultipleNum(int remainMultipleNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_REMAIN_MULTIPLE_NUM, remainMultipleNum);
	}
	
	public int getMarsBuyMultipleNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_BUY_MULTIPLE_NUM);
	}
	
	public void setMarsBuyMultipleNum(int buyMultipleNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_BUY_MULTIPLE_NUM, buyMultipleNum);
	}
	
	public int getMarsRewardCoin() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_REWARD_COIN);
	}
	
	public void setMarsRewardCoin(int rewardCoin) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_REWARD_COIN, rewardCoin);
	}
	
	public int getMarsRewardPrestige() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_REWARD_PRESTIGE);
	}
	
	public void setMarsRewardPrestige(int rewardPrestige) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_REWARD_PRESTIGE, rewardPrestige);
	}
	
	public int getMarsRewardState() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_REWARD_STATE);
	}
	
	public void setMarsRewardState(int rewardPrestige) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_REWARD_STATE, rewardPrestige);
	}
	
	public int getTrainCoin(){
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.TRAIN_COIN);
	}
	
	public void setTrainCoin(int trainCoin) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.TRAIN_COIN, trainCoin);
	}
	
	public int getLevyBetRemainNum(){
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEVY_BET_REMAIN_TIMES);
	}
	
	public void setLevyBetRemainNum(int betRemainNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEVY_BET_REMAIN_TIMES, betRemainNum);
	}
	
	public int getLevyCertainWinUsedNum(){
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEVY_CERTAIN_WIN_USED_TIMES);
	}
	
	public void setLevyCertainWinUsedNum(int certianWinRemainNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEVY_CERTAIN_WIN_USED_TIMES, certianWinRemainNum);
	}
	
	/**
	 * 增加训练币
	 */
	public void addTrainCoin(int addCoin, boolean notify,
			TrainCoinLogReason reason, String param) {
		if (addCoin <= 0) {
			return;
		}
		int trainCoin = getTrainCoin() + addCoin;
		setTrainCoin(trainCoin);
		if (notify) {
			// 发送浮动提示消息
			String langTrainCoin = GameServerAssist.getSysLangService().read(
					LangConstants.TRAIN_COIN);
			String operate = addCoin >= 0 ? "+" : "-";
			sendImportantMessage(LangConstants.COMMON_OBTAIN, addCoin,
					langTrainCoin, operate);
		}
	}
	
	/**
	 * 消耗培养币
	 */
	public boolean costTrainCoin(int costCoin, TrainCoinLogReason reason,
			String param){
		if (costCoin == 0) {
			return true;
		}
		if (getTrainCoin() < costCoin) {
			String langTrainCoin = GameServerAssist.getSysLangService().read(
					LangConstants.TRAIN_COIN);
			sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,langTrainCoin);
			return false;
		}
		setTrainCoin(getTrainCoin() - costCoin);
		return true;
	}
	
	public int getEscortRemainNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.ESCORT_REMAIN_TIMES);
	}
	
	public void setEscortRemainNum(int escortRemainNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.ESCORT_REMAIN_TIMES, escortRemainNum);
	}
	
	public int getEscortRobRemainNum( ){
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.ESCORT_REMAIN_ROB_TIMES);
	}
	
	public void setEscortRobRemainNum(int robRemainNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.ESCORT_REMAIN_ROB_TIMES, robRemainNum);
	}
	
	public int getEscortRobBuyNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.ESCORT_BUY_ROB_TIMES);
	}
	
	public void setEscortRobBuyNum(int robBuyNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.ESCORT_BUY_ROB_TIMES, robBuyNum);
	}
	
	public int getEscortHelpRemainNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.ESCORT_REMAIN_HELP_TIMES);
	}
	
	public void setEscortHelpRemainNum(int helpRemainNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.ESCORT_REMAIN_HELP_TIMES, helpRemainNum);
	}
	
	public int getEscortRefreshMonsterNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.ESCORT_REFRESH_MONSTER_TIMES);
	}
	
	public void setEscortRefreshMonsterNum(int refreshNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.ESCORT_REFRESH_MONSTER_TIMES, refreshNum);
	}
	
	public boolean isLegionMeditationed() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEGION_MEDITATION_STATE) == 1;
	}
	
	public void setIsLegionMeditationed(boolean isMeditationed) {
		int state = isMeditationed ? 1 : 0;
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEGION_MEDITATION_STATE, state);
	}
	
	public int getLegionTitleId() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEGION_TITLE_ID);
	}
	
	public void setLegionTitleId(int titleId) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEGION_TITLE_ID, titleId);
	}
	
	public boolean isLegionTitleValid() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEGION_TITLE_STATE) == 1;
	}
	
	public void setLegionTitleValid(boolean valid) {
		int state = valid ? 1 : 0;
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEGION_TITLE_STATE, state);
	}
	
	public int getLegionTaskThemeType() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEGION_TASK_THEME_TYPE);
	}
	
	public void setLegionTaskThemeType(int themeType) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEGION_TASK_THEME_TYPE, themeType);
	}
	
	public int getLegionReceivedTaskNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEGION_RECEIVED_TASK_NUM);
	}
	
	public void setLegionReceivedTaskNum(int num) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEGION_RECEIVED_TASK_NUM, num);
	}
	
	public boolean isGodLegionTaskRankReward() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.LEGION_TASK_RANK_REWARD_STATE) == 1;
	}
	
	public void setIsGodLegionTaskRankReward(boolean isGot) {
		int state = isGot ? 1 : 0;
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.LEGION_TASK_RANK_REWARD_STATE, state);
	}
	
	public int getCurrentPredictId() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.CURRENT_PREDICT_ID);
	}
	
	public void setCurrentPredictId(int predictId) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.CURRENT_PREDICT_ID, predictId);
	}
	
	public int getMainCityMonsterRemainNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MAIN_CITY_MONSTER_REMAIN_NUM);
	}
	
	public void setMainCityMonsterRemainNum(int remainNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MAIN_CITY_MONSTER_REMAIN_NUM, remainNum);
	}
	
	public int getMainCityMonsterLevelId() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MAIN_CITY_MONSTER_LEVEL_ID);
	}
	
	public void setMainCityMonsterLevelId(int monsterId) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MAIN_CITY_MONSTER_LEVEL_ID, monsterId);
	}
	
	public int getDailyQuestReceivedNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.DAILY_QUEST_RECEIVED_NUM);
	}
	
	public void setDailyQuestReceivedNum(int receivedNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.DAILY_QUEST_RECEIVED_NUM, receivedNum);
	}
	
	public int getDailyQuestBuyNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.DAILY_QUEST_BUY_NUM);
	}
	
	public void setDailyQuestBuyNum(int buyNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.DAILY_QUEST_BUY_NUM, buyNum);
	}
	
	public int getSpritePubWinUsedNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.SPRITE_PUB_WIN_USED_NUM);
	}
	
	public void setSpritePubWinUsedNum(int usedNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.SPRITE_PUB_WIN_USED_NUM, usedNum);
	}
	
	public int getSpritePubGuessType() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.SPRITE_PUB_GUESS_TYPE);
	}
	
	public void setSpritePubGuessType(int guessType) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.SPRITE_PUB_GUESS_TYPE, guessType);
	}
	
	public int getDayRecoverEnergyNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.DAY_RECOVER_ENERGY_NUM);
	}
	
	public void setDayRecoverEnergyNum(int recoverNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.DAY_RECOVER_ENERGY_NUM, recoverNum);
	}
	
	public int getMarsAcceptRewardNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.MARS_ACCEPT_REWARD_NUM);
	}
	
	public void setMarsAcceptRewardNum(int rewardNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.MARS_ACCEPT_REWARD_NUM, rewardNum);
	}
	
	public int getTotalRecoverEnergyNum() {
		return this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.TOTAL_RECOVER_ENERGY_NUM);
	}
	
	public void setTotalRecoverEnergyNum(int totalNum) {
		this.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
						HumanIntProperty.TOTAL_RECOVER_ENERGY_NUM, totalNum);
	}
}
