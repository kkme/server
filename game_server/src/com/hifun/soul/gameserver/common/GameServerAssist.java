package com.hifun.soul.gameserver.common;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.script.ScriptException;

import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.LocalConstants;
import com.hifun.soul.common.service.DirtFilterService;
import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.i18n.impl.SysLangServiceImpl;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.uuid.UUIDService;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.cache.CacheCfg;
import com.hifun.soul.gamedb.config.DBServiceManager;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.abattoir.manager.AbattoirTemplateManager;
import com.hifun.soul.gameserver.abattoir.manager.GlobalAbattoirManager;
import com.hifun.soul.gameserver.activity.ActivityTemplateManager;
import com.hifun.soul.gameserver.activity.GlobalActivityManager;
import com.hifun.soul.gameserver.activity.question.QuestionTemplateManager;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.arena.service.ArenaTemplateManager;
import com.hifun.soul.gameserver.bag.manager.BagTemplateManager;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.battle.template.ComboTemplateManager;
import com.hifun.soul.gameserver.betareward.service.BetaRewardTemplateManager;
import com.hifun.soul.gameserver.bloodtemple.manager.BloodTempleTemplateManager;
import com.hifun.soul.gameserver.bloodtemple.manager.GlobalBloodTempleManager;
import com.hifun.soul.gameserver.boss.service.BossWarService;
import com.hifun.soul.gameserver.boss.service.BossWarTemplateManager;
import com.hifun.soul.gameserver.building.service.BuildingTemplateManager;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.cd.service.CdTemplateManager;
import com.hifun.soul.gameserver.chat.WordFilterServiceImpl;
import com.hifun.soul.gameserver.chat.service.ChatService;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.hifun.soul.gameserver.common.log.LogService;
import com.hifun.soul.gameserver.compass.ICompassService;
import com.hifun.soul.gameserver.compass.QQCompassService;
import com.hifun.soul.gameserver.crystalexchange.service.CrystalExchangeTemplateManager;
import com.hifun.soul.gameserver.elitestage.EliteStageTemplateManager;
import com.hifun.soul.gameserver.escort.manager.EscortTemplateManager;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.faction.GlobalFactionManager;
import com.hifun.soul.gameserver.foster.manager.FosterTemplateManager;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.functionhelper.service.FunctionHelperService;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.gem.GemTemplateManager;
import com.hifun.soul.gameserver.gift.manager.GiftTemplateManager;
import com.hifun.soul.gameserver.gm.service.GmCommandService;
import com.hifun.soul.gameserver.godsoul.manager.GodsoulTemplateManager;
import com.hifun.soul.gameserver.guide.service.GuideTemplateManager;
import com.hifun.soul.gameserver.helper.service.HelperTemplateManager;
import com.hifun.soul.gameserver.honourshop.service.HonourShopTemplateManager;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.EnergyTemplateManager;
import com.hifun.soul.gameserver.human.level.LevelUpTemplateManager;
import com.hifun.soul.gameserver.human.occupation.OccupationTemplateManager;
import com.hifun.soul.gameserver.human.quest.manager.QuestTemplateManager;
import com.hifun.soul.gameserver.item.service.EquipMakeTemplateManager;
import com.hifun.soul.gameserver.item.service.EquipUpgradeTemplateManager;
import com.hifun.soul.gameserver.item.service.ForgeTemplateManager;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.item.service.SpreeTemplateManager;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legionboss.service.LegionBossService;
import com.hifun.soul.gameserver.legionboss.service.LegionBossTemplateManager;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.manager.LegionMineWarTemplateManager;
import com.hifun.soul.gameserver.levy.LevyTemplateManager;
import com.hifun.soul.gameserver.loginreward.service.LoginRewardTemplateManager;
import com.hifun.soul.gameserver.mail.manager.TimingMailManager;
import com.hifun.soul.gameserver.mall.service.MallTemplateManager;
import com.hifun.soul.gameserver.marketact.setting.MarketActivitySetting;
import com.hifun.soul.gameserver.mars.manager.GlobalMarsManager;
import com.hifun.soul.gameserver.mars.manager.MarsTemplateManager;
import com.hifun.soul.gameserver.matchbattle.manager.MatchBattleTemplateManager;
import com.hifun.soul.gameserver.matchbattle.service.MatchBattleService;
import com.hifun.soul.gameserver.meditation.MeditationTemplateManager;
import com.hifun.soul.gameserver.mine.manager.MineTemplateManager;
import com.hifun.soul.gameserver.monster.MonsterFactory;
import com.hifun.soul.gameserver.name.NameTemplateManager;
import com.hifun.soul.gameserver.onlinereward.service.OnlineRewardTemplateManager;
import com.hifun.soul.gameserver.predict.manager.PredictTemplateManager;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.manager.PrisonTemplateManager;
import com.hifun.soul.gameserver.rank.RankTemplateManager;
import com.hifun.soul.gameserver.rank.manager.RankManager;
import com.hifun.soul.gameserver.recharge.RechargeTemplateManager;
import com.hifun.soul.gameserver.rechargetx.service.RechargeTXTemplateManager;
import com.hifun.soul.gameserver.refine.service.RefineTemplateManager;
import com.hifun.soul.gameserver.reward.RewardTemplateManager;
import com.hifun.soul.gameserver.reward.service.RewardService;
import com.hifun.soul.gameserver.role.properties.manager.PropertyConverterRateManager;
import com.hifun.soul.gameserver.shop.service.ShopTemplateManager;
import com.hifun.soul.gameserver.shop.service.SpecialShopNotifyService;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.target.manager.TargetTemplateManager;
import com.hifun.soul.gameserver.technology.service.TechnologyTemplateManager;
import com.hifun.soul.gameserver.tencent.TencentUserInfoManager;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;
import com.hifun.soul.gameserver.timetask.service.TimeTaskTemplateManager;
import com.hifun.soul.gameserver.title.TitleTemplateManager;
import com.hifun.soul.gameserver.training.TrainingTemplateManager;
import com.hifun.soul.gameserver.turntable.manager.TurntableTemplateManager;
import com.hifun.soul.gameserver.turntable.service.TurntableDataService;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;
import com.hifun.soul.gameserver.warrior.manager.WarriorTemplateManager;
import com.hifun.soul.gameserver.yellowvip.manager.YellowVipTemplateManager;

/**
 * 服务的初始化、启动、停止
 * 
 * @author magicstone
 */
public class GameServerAssist {
	/** 游戏配置 */
	private static GameServerConfig gameServerConfig;
	/** 游戏常用配置 */
	private static GameConstants gameConstants;
	/** 模版管理器 */
	private static TemplateService templateService;
	/** 多语言管理器 */
	private static SysLangService sysLangService;
	/** 游戏世界 */
	private static GameWorld gameWorld;
	/** cd模版管理器 */
	private static CdTemplateManager cdTemplateManager;
	/** 聊天服务 */
	private static ChatService chatService;
	/** 任务模版管理器 */
	private static QuestTemplateManager questTemplateManager;
	/** 职业模版管理器 */
	private static OccupationTemplateManager occupationTemplateManager;
	/** gm命令 */
	private static GmCommandService gmCommandService;
	/** 物品模版管理器 */
	private static ItemTemplateManager itemTemplateManager;
	/** 建筑模版管理器 */
	private static BuildingTemplateManager buildingTemplateManager;
	/** 商城管理器 */
	private static MallTemplateManager mallTemplateManager;
	/** 商店管理器 */
	private static ShopTemplateManager shopTemplateManager;
	/** 排行榜管理器 */
	private static RankTemplateManager rankTemplateManager;
	/** 宝石模版管理器 */
	private static GemTemplateManager gemTemplateManager;
	/** 定时任务管理器 */
	private static TimeTaskTemplateManager timeTaskTemplateManager;
	/** 装备强化模版管理器 */
	private static EquipUpgradeTemplateManager equipUpgradeTemplateManager;
	/** 星运模版管理器 */
	private static HoroscopeTemplateManager horoscopeTemplateManager;
	/** 科技模版管理器 */
	private static TechnologyTemplateManager technologyTemplateManager;
	/** 属性管理器 */
	private static PropertyConverterRateManager propertyConverterRateManager;
	/** 魔晶兑换管理器 */
	private static CrystalExchangeTemplateManager crystalExchangeTemplateManager;
	/** 连续登陆管理器 */
	private static LoginRewardTemplateManager loginRewardTemplateManager;
	/** 装备制作管理器 */
	private static EquipMakeTemplateManager equipMakeTemplateManager;
	/** 新手引导管理器 */
	private static GuideTemplateManager guideTemplateManager;
	/** 连击模版 */
	private static ComboTemplateManager comboTemplateManager;
	/** 关卡模版 */
	private static StageTemplateManager stageTemplateManager;
	/** 技能模版 */
	private static SkillTemplateManager skillTemplateManager;
	/** 充值模版 */
	private static RechargeTemplateManager rechargeTemplateManager;
	/** 问答模版 */
	private static QuestionTemplateManager questionTemplateManager;
	/** 礼包模版 */
	private static SpreeTemplateManager spreeTemplateManager;
	/** 训练模版 */
	private static TrainingTemplateManager trainingTemplateManager;
	/** vip模版 */
	private static VipPrivilegeTemplateManager vipPrivilegeTemplateManager;
	/** 升级模版 */
	private static LevelUpTemplateManager levelUpTemplateManager;
	/** 背包模版 */
	private static BagTemplateManager bagTemplateManager;
	/** 命名模版 */
	private static NameTemplateManager nameTemplateManager;
	/** 体力购买 */
	private static EnergyTemplateManager energyTemplateManager;
	/** 冥想 */
	private static MeditationTemplateManager meditationTemplateManager;
	/** boss战 */
	private static BossWarService bossWarService;
	/** 活动模版 */
	private static ActivityTemplateManager activityTemplateManager;
	/** 精英副本 */
	private static EliteStageTemplateManager eliteStageTemplateManager;
	/** 排行榜 */
	private static RankManager rankManager;
	/** 脏字过滤 */
	private static DirtFilterService dirtFilterService;
	/** 奖励模版 */
	private static RewardTemplateManager rewardTemplateManager;
	/** 矿场模版 */
	private static MineTemplateManager mineTemplateManager;
	/** 税收模版 */
	private static LevyTemplateManager levyTemplateManager;
	/** 在线奖励模版 */
	private static OnlineRewardTemplateManager onlineRewardTemplateManager;
	/** 小助手模版 */
	private static HelperTemplateManager helperTemplateManager;
	/** 荣誉商店模版 */
	private static HonourShopTemplateManager honourShopTemplateManager;
	/** 好友管理器 */
	private static FriendService friendService;
	/** 竞技场管理器 */
	private static ArenaService arenaService;
	/** 日志服务 */
	private static LogService logService;
	/** 运营活动模版 */
	private static MarketActivitySetting marketActivitySetting;
	/** 大转盘抽奖模版 */
	private static TurntableDataService turntableDataService;
	/** 公告模版 */
	private static BulletinManager bulletinManager;
	/** 全局活动模版 */
	private static GlobalActivityManager globalActivityManager;
	/** 神秘商店公告模版 */
	private static SpecialShopNotifyService specialShopNotifyService;
	/** 系统时间 */
	private static SystemTimeService systemTimeService;
	/** 竞技场模版 */
	private static ArenaTemplateManager arenaTemplateManager;
	/** 数据管理器 */
	private static IDataService dataService;
	/** 游戏功能 */
	private static GameFuncService gameFuncService;
	/** 聊天过滤器 */
	private static WordFilterServiceImpl wordFilterService;
	/** 战斗管理器 */
	private static BattleManager battleManager;
	/** UUID服务 */
	private static UUIDService uuidService;
	/** 通用奖励 */
	private static RewardService rewardService;
	/** boss战模版管理 */
	private static BossWarTemplateManager bossWarTemplateManager;
	/** 怪物工厂 */
	private static MonsterFactory monsterFactory;
	/** 数据服务管理器 */
	private static DBServiceManager _dbServiceManager;
	/** 罗盘服务 */
	private static ICompassService compassService;
	/** 天赋模板管理器 */
	private static GiftTemplateManager giftTemplateManager;
	/** 培养模板管理器 */
	private static FosterTemplateManager fosterTemplateManager;
	/** 洗炼模版管理器 */
	private static ForgeTemplateManager forgeTemplateManager;
	/** 试炼模版管理器 */
	private static RefineTemplateManager refineTemplateManager;
	/** 匹配战模板管理器 */
	private static MatchBattleTemplateManager matchBattleTemplateManager;
	/** 匹配战服务 */
	private static MatchBattleService matchBattleService;
	/** 封测奖励 */
	private static BetaRewardTemplateManager betaRewardTemplateManager;
	/** 功能助手服务 */
	private static FunctionHelperService functionHelperService;
	/** 勇者之路模板管理器 */
	private static WarriorTemplateManager warriorTemplateManager;
	/** 抽奖模板管理器 */
	private static TurntableTemplateManager turntableTemplateManager;
	/** 定时邮件管理器 */
	private static TimingMailManager timingMailManager;
	/** 腾讯充值模版 */
	private static RechargeTXTemplateManager rechargeTXTemplateManager;
	/** qq黄钻模板管理器 */
	private static YellowVipTemplateManager yellowVipTemplateManager;
	/** 腾讯开放平台用户信息管理器 */
	private static TencentUserInfoManager tencentUserInfoManager;
	/** 角色军衔模板管理器 */
	private static TitleTemplateManager titleTemplateManager;
	/** 全局军团管理器 */
	private static GlobalLegionManager globalLegionManager;
	/** 军团模板管理器 */
	private static LegionTemplateManager legionTemplateManager;
	/** 神魄模板管理器 */
	private static GodsoulTemplateManager godsoulTemplateManager;
	/** 战俘营模板管理器 */
	private static PrisonTemplateManager prisonTemplateManager;
	/** 全局战俘营管理器 */
	private static GlobalPrisonManager globalPrisonManager;
	/** 角斗场模板管理器 */
	private static AbattoirTemplateManager abattoirTemplateManager;
	/** 全局角斗场管理器 */
	private static GlobalAbattoirManager globalAbattoirManager;
	/** 角斗场模板管理器 */
	private static BloodTempleTemplateManager bloodTempleTemplateManager;
	/** 全局角斗场管理器 */
	private static GlobalBloodTempleManager globalBloodTempleManager;
	/** 全局精灵模版管理器 */
	private static SpriteTemplateManager spriteTemplateManager;
	/** 全局的阵营业务管理器 */
	private static GlobalFactionManager globalFactionManager;
	/** 战神之巅模板管理器 */
	private static MarsTemplateManager marsTemplateManager;
	/** 全局战神之巅业务管理器 */
	private static GlobalMarsManager globalMarsManager;
	/** 个人目标模板管理器 */
	private static TargetTemplateManager targetTemplateManager;
	/** 军团战模板管理器 */
	private static LegionBossTemplateManager legionBossTemplateManager;
	/** 军团boss战 */
	private static LegionBossService legionBossService;
	/** 军团矿战模板管理器 */
	private static LegionMineWarTemplateManager legionMineWarTemplateManager;
	/** 全局军团矿战管理器 */
	private static GlobalLegionMineWarManager globalLegionMineWarManager;
	/** 押运模板管理器 */
	private static EscortTemplateManager escortTemplateManager;
	/** 全局押运管理器 */
	private static GlobalEscortManager globalEscortManager;
	/** 预见模板管理器 */
	private static PredictTemplateManager predictTemplateManager;
	/** 全局定时任务管理器 */
	private static GlobalTimeTaskManager globalTimeTaskManager;
	
	
	/**
	 * 服务的初始化
	 * 
	 * @throws IOException
	 * @throws ScriptException
	 */
	public static void init(GameServerConfig cfg, LocalConfig localConfig)
			throws ScriptException, IOException {
		gameServerConfig = cfg;
		CacheCfg.config(cfg.isCacheToDBTurnOn());

		// 初始化全局的游戏参数
		gameConstants = initConstants(cfg);
		ApplicationContext
				.getApplicationContext()
				.getDefaultListableBeanFactory()
				.registerSingleton(GameConstants.class.getSimpleName(),
						gameConstants);
		// 模版初始化
		templateService = ApplicationContext.getApplicationContext().getBean(
				TemplateService.class);
		templateService.setResourceFolder(cfg.getScriptDirFullPath());
		templateService.setDebug(cfg.getIsDebug());
		templateService.init(ConfigUtil.getConfigURL("templates.xml"));
		// UUID
		uuidService = ApplicationContext.getApplicationContext().getBean(
				UUIDService.class);
		// 定时任务模版初始化
		timeTaskTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(TimeTaskTemplateManager.class);
		timeTaskTemplateManager.init();
		// 系统时间
		systemTimeService = ApplicationContext.getApplicationContext().getBean(
				SystemTimeService.class);
		// 多语言管理器初始化
		sysLangService = ApplicationContext.getApplicationContext().getBean(
				SysLangServiceImpl.class);
		String langBasePath = cfg.getResourceFullPath(cfg.getI18nDir(),
				cfg.getLanguage());
		String sysLangConfigFile = langBasePath + File.separator
				+ "sys_lang.xls";
		sysLangService.setSysLangConfigFile(sysLangConfigFile);
		sysLangService.init();
		// 数据管理器
		dataService = ApplicationContext.getApplicationContext().getBean(
				IDataService.class);
		// 场景管理器
		gameWorld = ApplicationContext.getApplicationContext().getBean(
				GameWorld.class);
		gameWorld.init(cfg.getMaxOnlineUsers(),
				cfg.getTickTimesToSynchronizationDBForHuman(),
				cfg.getTickTimesToSynchronizationDBForSystem());
		gameWorld.registerMainMessageProcessor(cfg.getMainProcessor());
		// 游戏功能管理器
		gameFuncService = ApplicationContext.getApplicationContext().getBean(
				GameFuncService.class);
		gameFuncService.init();
		// 聊天过滤
		wordFilterService = ApplicationContext.getApplicationContext().getBean(
				WordFilterServiceImpl.class);
		// 怪物工厂
		monsterFactory = ApplicationContext.getApplicationContext().getBean(
				MonsterFactory.class);
		monsterFactory.init();
		// 战斗管理器
		battleManager = ApplicationContext.getApplicationContext().getBean(
				BattleManager.class);
		// 竞技场模版
		arenaTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ArenaTemplateManager.class);
		arenaTemplateManager.init();
		// Cd模版初始化
		cdTemplateManager = ApplicationContext.getApplicationContext().getBean(
				CdTemplateManager.class);
		cdTemplateManager.init();
		// 聊天初始化
		chatService = ApplicationContext.getApplicationContext().getBean(
				ChatService.class);
		chatService.init();
		// 任务模版数据管理器
		questTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(QuestTemplateManager.class);
		questTemplateManager.init();
		// 职业数据管理器初始化
		occupationTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(OccupationTemplateManager.class);
		occupationTemplateManager.init();
		// GM服务初始化
		gmCommandService = ApplicationContext.getApplicationContext().getBean(
				GmCommandService.class);
		gmCommandService.init();
		// 物品模版初始化
		itemTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ItemTemplateManager.class);
		itemTemplateManager.init();
		// 装备强化
		equipUpgradeTemplateManager = ApplicationContext
				.getApplicationContext().getBean(
						EquipUpgradeTemplateManager.class);
		equipUpgradeTemplateManager.init();
		// 建筑模版初始化
		buildingTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(BuildingTemplateManager.class);
		buildingTemplateManager.init();
		// 商城模版初始化
		mallTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(MallTemplateManager.class);
		mallTemplateManager.init();
		// 商店模版初始化
		shopTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ShopTemplateManager.class);
		shopTemplateManager.init();
		// 排行榜模版初始化
		rankTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(RankTemplateManager.class);
		rankTemplateManager.init();
		// 宝石相关模板初始化
		gemTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(GemTemplateManager.class);
		gemTemplateManager.init();
		// 星运初始化
		horoscopeTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(HoroscopeTemplateManager.class);
		horoscopeTemplateManager.init();
		// 科技系统
		technologyTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(TechnologyTemplateManager.class);
		technologyTemplateManager.init();
		// 角色属性转换管理器
		propertyConverterRateManager = ApplicationContext
				.getApplicationContext().getBean(
						PropertyConverterRateManager.class);
		propertyConverterRateManager.init();
		// 魔晶兑换模版
		crystalExchangeTemplateManager = ApplicationContext
				.getApplicationContext().getBean(
						CrystalExchangeTemplateManager.class);
		crystalExchangeTemplateManager.init();
		// 连续登陆奖励模版
		loginRewardTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(LoginRewardTemplateManager.class);
		loginRewardTemplateManager.init();
		// 装备制作模版
		equipMakeTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(EquipMakeTemplateManager.class);
		equipMakeTemplateManager.init();
		// 新手引导模版
		guideTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(GuideTemplateManager.class);
		guideTemplateManager.init();
		guideTemplateManager.setOpened(cfg.getGuideOpened());
		// 连击模版
		comboTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ComboTemplateManager.class);
		comboTemplateManager.init();
		// 关卡模版
		stageTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(StageTemplateManager.class);
		stageTemplateManager.init();
		// 技能模版
		skillTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(SkillTemplateManager.class);
		skillTemplateManager.init();
		// 充值模版
		rechargeTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(RechargeTemplateManager.class);
		rechargeTemplateManager.init();
		// 问答模版
		questionTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(QuestionTemplateManager.class);
		questionTemplateManager.init();
		// 礼包模板
		spreeTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(SpreeTemplateManager.class);
		spreeTemplateManager.init();
		// 训练模板
		trainingTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(TrainingTemplateManager.class);
		trainingTemplateManager.init();
		// VIP与魔晶花费模板
		vipPrivilegeTemplateManager = ApplicationContext
				.getApplicationContext().getBean(
						VipPrivilegeTemplateManager.class);
		vipPrivilegeTemplateManager.init();
		// 升级模版
		levelUpTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(LevelUpTemplateManager.class);
		levelUpTemplateManager.init();
		// 背包模版
		bagTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(BagTemplateManager.class);
		bagTemplateManager.init();
		// 自动命名模板
		nameTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(NameTemplateManager.class);
		nameTemplateManager.init();
		// 购买体力值模板
		energyTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(EnergyTemplateManager.class);
		energyTemplateManager.init();
		// 冥想模板
		meditationTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(MeditationTemplateManager.class);
		meditationTemplateManager.init();
		// boss战服务
		bossWarService = ApplicationContext.getApplicationContext().getBean(
				BossWarService.class);
		bossWarService.init();
		// 活动模板
		activityTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ActivityTemplateManager.class);
		activityTemplateManager.init();
		// 精英副本模板
		eliteStageTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(EliteStageTemplateManager.class);
		eliteStageTemplateManager.init();
		// 初始化排行榜管理器
		rankManager = ApplicationContext.getApplicationContext().getBean(
				RankManager.class);
		rankManager.init();
		// 初始化内容过滤管理器
		dirtFilterService = ApplicationContext.getApplicationContext().getBean(
				DirtFilterService.class);
		dirtFilterService.init();
		// 初始化奖励推送模板
		rewardTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(RewardTemplateManager.class);
		rewardTemplateManager.init();
		// 初始化矿场模板
		mineTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(MineTemplateManager.class);
		mineTemplateManager.init();
		// 初始化主城相关模板
		levyTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(LevyTemplateManager.class);
		levyTemplateManager.init();
		// 在线奖励模版
		onlineRewardTemplateManager = ApplicationContext
				.getApplicationContext().getBean(
						OnlineRewardTemplateManager.class);
		onlineRewardTemplateManager.init();
		// 小助手模版
		helperTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(HelperTemplateManager.class);
		helperTemplateManager.init();
		// 荣誉商店
		honourShopTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(HonourShopTemplateManager.class);
		honourShopTemplateManager.init();
		// 好友
		friendService = ApplicationContext.getApplicationContext().getBean(
				FriendService.class);
		friendService.init();
		// 竞技场
		arenaService = ApplicationContext.getApplicationContext().getBean(
				ArenaService.class);
		arenaService.init();
		// 转盘数据初始化
		turntableDataService = ApplicationContext.getApplicationContext()
				.getBean(TurntableDataService.class);
		// 运营
		marketActivitySetting = ApplicationContext.getApplicationContext()
				.getBean(MarketActivitySetting.class);
		// 公告
		bulletinManager = ApplicationContext.getApplicationContext().getBean(
				BulletinManager.class);
		// 神秘商店提醒
		specialShopNotifyService = ApplicationContext.getApplicationContext()
				.getBean(SpecialShopNotifyService.class);
		// boss战模版
		bossWarTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(BossWarTemplateManager.class);
		// 通用奖励
		rewardService = ApplicationContext.getApplicationContext().getBean(
				RewardService.class);
		// 天赋模板管理器
		giftTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(GiftTemplateManager.class);
		giftTemplateManager.init();
		// 培养模板管理器
		fosterTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(FosterTemplateManager.class);
		fosterTemplateManager.init();
		// 洗炼模版管理器
		forgeTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ForgeTemplateManager.class);
		forgeTemplateManager.init();
		// 试炼模版管理器
		refineTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(RefineTemplateManager.class);
		refineTemplateManager.init();
		// 匹配战服务
		matchBattleTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(MatchBattleTemplateManager.class);
		matchBattleTemplateManager.init();
		// 匹配战服务
		matchBattleService = ApplicationContext.getApplicationContext()
				.getBean(MatchBattleService.class);
		matchBattleService.init();
		// 封测奖励
		betaRewardTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(BetaRewardTemplateManager.class);
		betaRewardTemplateManager.init();
		// 功能助手
		functionHelperService = ApplicationContext.getApplicationContext()
				.getBean(FunctionHelperService.class);
		functionHelperService.init();
		// 勇者之路模板管理器
		warriorTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(WarriorTemplateManager.class);
		warriorTemplateManager.init();
		// 抽奖模板管理器
		turntableTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(TurntableTemplateManager.class);
		turntableTemplateManager.init();
		// 腾讯充值模版
		rechargeTXTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(RechargeTXTemplateManager.class);
		rechargeTXTemplateManager.init();
		// qq黄钻模板管理器
		yellowVipTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(YellowVipTemplateManager.class);
		yellowVipTemplateManager.init();
		// 定时邮件
		timingMailManager = ApplicationContext.getApplicationContext().getBean(
				TimingMailManager.class);
		// 全局活动
		globalActivityManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalActivityManager.class);
		// 腾讯开放平台用户信息管理器
		tencentUserInfoManager = ApplicationContext.getApplicationContext()
				.getBean(TencentUserInfoManager.class);
		// 角色军衔管理器
		titleTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(TitleTemplateManager.class);
		titleTemplateManager.init();
		// 军团模板管理器
		legionTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(LegionTemplateManager.class);
		legionTemplateManager.init();
		// 全局军团管理器
		globalLegionManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalLegionManager.class);
		globalLegionManager.init();
		// 神魄模板管理器
		godsoulTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(GodsoulTemplateManager.class);
		godsoulTemplateManager.init();
		// 战俘营模板管理器
		prisonTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(PrisonTemplateManager.class);
		prisonTemplateManager.init();
		// 全局战俘营管理器
		globalPrisonManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalPrisonManager.class);
		globalPrisonManager.init();
		// 角斗场模板管理器
		abattoirTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(AbattoirTemplateManager.class);
		abattoirTemplateManager.init();
		// 全局角斗场管理器
		globalAbattoirManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalAbattoirManager.class);
		globalAbattoirManager.init();
		// 嗜血神殿模板管理器
		bloodTempleTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(BloodTempleTemplateManager.class);
		bloodTempleTemplateManager.init();
		// 全局嗜血神殿管理器
		globalBloodTempleManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalBloodTempleManager.class);
		globalBloodTempleManager.init();
		// 精灵模版管理器
		spriteTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(SpriteTemplateManager.class);
		spriteTemplateManager.init();
		// 全局阵营管理器
		globalFactionManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalFactionManager.class);
		globalFactionManager.init();
		// 战神之巅模板管理器
		marsTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(MarsTemplateManager.class);
		marsTemplateManager.init();
		// 战神之巅模板管理器
		globalMarsManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalMarsManager.class);
		globalMarsManager.init();
		// 个人目标模板管理器
		targetTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(TargetTemplateManager.class);
		targetTemplateManager.init();
		// 军团boss战模板管理器
		legionBossTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(LegionBossTemplateManager.class);
		legionBossTemplateManager.init();
		// 军团boss战服务
		legionBossService = ApplicationContext.getApplicationContext().getBean(
				LegionBossService.class);
		legionBossService.init();
		// 军团矿战模板管理器
		legionMineWarTemplateManager = ApplicationContext.getApplicationContext().getBean(
				LegionMineWarTemplateManager.class);
		legionMineWarTemplateManager.init();
		// 全局军团矿战管理器
		globalLegionMineWarManager = ApplicationContext.getApplicationContext().getBean(
				GlobalLegionMineWarManager.class);
		globalLegionMineWarManager.init();
		// 押运模板管理器
		escortTemplateManager = ApplicationContext.getApplicationContext().getBean(
				EscortTemplateManager.class);
		escortTemplateManager.init();
		// 全局押运管理器
		globalEscortManager = ApplicationContext.getApplicationContext().getBean(
				GlobalEscortManager.class);
		globalEscortManager.init();
		// 预见模板管理器
		predictTemplateManager = ApplicationContext.getApplicationContext().getBean(
				PredictTemplateManager.class);
		predictTemplateManager.init();
		// 全局定时任务管理器
		globalTimeTaskManager = ApplicationContext.getApplicationContext().getBean(
				GlobalTimeTaskManager.class);
		
		// 构建日志服务
		logService = ApplicationContext.getApplicationContext().getBean(
				LogService.class);
		logService.setIsLogServerSwitch(cfg.isLogServerSwitch());
		logService.init(cfg.getLogConfig().getLogServerIp(), cfg.getLogConfig()
				.getLogServerPort(), Integer.parseInt(cfg.getRegionId()),
				Integer.parseInt(cfg.getServerId()));
		// 初始化tgw大小
		if (cfg.isUseLocalAuthorize()) {
			initTGW(cfg, localConfig);
			// 初始化罗盘服务
			initCompassService(cfg, localConfig);
		}

	}

	private static void initCompassService(GameServerConfig cfg,
			LocalConfig localConfig) {
		compassService = new QQCompassService(localConfig.getReportProcessor(),
				cfg, localConfig);
	}

	private static void initTGW(GameServerConfig cfg, LocalConfig localConfig) {
		String realStr = MessageFormat.format(localConfig.getTgwString(),
				cfg.getServerDomain(), cfg.getPorts());
		LocalConstants.TGW_LENGTH = (short) realStr.length();
	}

	/**
	 * 根据constants.js生成GameConstants
	 * 
	 * @param cfg
	 * @param gameConstants
	 * @throws IOException
	 * @throws ScriptException
	 */
	private static GameConstants initConstants(GameServerConfig cfg)
			throws ScriptException, IOException {
		GameConstants gameConstants = new GameConstants();
		// File file = new File(cfg.getScriptDirFullPath() + File.separator
		// + "constants.js");
		// URL url = null;
		// try {
		// url = file.toURI().toURL();
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// }
		//
		// gameConstants = ConfigUtil.buildConfig(GameConstants.class, url);
		ConfigUtil.loadJsConfig(!cfg.getIsDebug(), gameConstants,
				cfg.getScriptDirFullPath() + File.separator + "constants.js");
		return gameConstants;
	}

	/**
	 * 服务的启动
	 * 
	 */
	public static void start(DBServiceManager dbServiceManager) {
		_dbServiceManager = dbServiceManager;
		IDBService dbService = dbServiceManager.getDBService();
		// 加载运营活动设置
		marketActivitySetting.loadSettings(dbService);
		// 大转盘
		turntableDataService.start(dbService);
		// 竞技场成员数据初始化
		arenaService.start(dbService);
		// boss战数据初始化
		bossWarService.start(dbService);
		// 初始化公告数据
		bulletinManager.initData(dbService);
		// 初始化全局活动管理器数据
		globalActivityManager.initData(dbService);
		// 神秘商店
		specialShopNotifyService.start(dbService);
		// 好友
		friendService.start(dbService);
		// 定时邮件
		timingMailManager.start(dbService);
		// 腾讯开放平台用户信息管理器
		tencentUserInfoManager.load(dbService);
		// 军团数据初始化
		globalLegionManager.start(dbService);
		// 战俘营数据初始化
		globalPrisonManager.start(dbService);
		// 角斗场数据初始化
		globalAbattoirManager.start(dbService);
		// 嗜血神殿数据初始化
		globalBloodTempleManager.start(dbService);
		// 全局阵营管理器
		globalFactionManager.loadDataFromDB(dbService);
		// 全局战神之巅业务管理器
		globalMarsManager.start(dbService);
		// 排行榜
		rankManager.start(dbService);
		// 军团boss战数据初始化
		legionBossService.start(dbService);
		// 军团矿战数据初始化
		globalLegionMineWarManager.start(dbService);
		// 押运数据初始化
		globalEscortManager.start(dbService);
		// 全局定时任务数据初始化
		globalTimeTaskManager.start(dbService);
		// 心跳线程启动
		gameWorld.start();

	}

	/**
	 * 服务的停止
	 * 
	 */
	public static void stop() {

	}

	public static GameConstants getGameConstants() {
		return gameConstants;
	}

	public static TemplateService getTemplateService() {
		return templateService;
	}

	public static SysLangService getSysLangService() {
		return sysLangService;
	}

	public static GameWorld getGameWorld() {
		return gameWorld;
	}

	public static CdTemplateManager getCdTemplateManager() {
		return cdTemplateManager;
	}

	public static ChatService getChatService() {
		return chatService;
	}

	public static QuestTemplateManager getQuestTemplateManager() {
		return questTemplateManager;
	}

	public static OccupationTemplateManager getOccupationTemplateManager() {
		return occupationTemplateManager;
	}

	public static GmCommandService getGmCommandService() {
		return gmCommandService;
	}

	public static ItemTemplateManager getItemTemplateManager() {
		return itemTemplateManager;
	}

	public static BuildingTemplateManager getBuildingTemplateManager() {
		return buildingTemplateManager;
	}

	public static MallTemplateManager getMallTemplateManager() {
		return mallTemplateManager;
	}

	public static ShopTemplateManager getShopTemplateManager() {
		return shopTemplateManager;
	}

	public static RankTemplateManager getRankTemplateManager() {
		return rankTemplateManager;
	}

	public static GemTemplateManager getGemTemplateManager() {
		return gemTemplateManager;
	}

	public static TimeTaskTemplateManager getTimeTaskTemplateManager() {
		return timeTaskTemplateManager;
	}

	public static EquipUpgradeTemplateManager getEquipUpgradeTemplateManager() {
		return equipUpgradeTemplateManager;
	}

	public static HoroscopeTemplateManager getHoroscopeTemplateManager() {
		return horoscopeTemplateManager;
	}

	public static TechnologyTemplateManager getTechnologyTemplateManager() {
		return technologyTemplateManager;
	}

	public static PropertyConverterRateManager getPropertyConverterRateManager() {
		return propertyConverterRateManager;
	}

	public static CrystalExchangeTemplateManager getCrystalExchangeTemplateManager() {
		return crystalExchangeTemplateManager;
	}

	public static LoginRewardTemplateManager getLoginRewardTemplateManager() {
		return loginRewardTemplateManager;
	}

	public static EquipMakeTemplateManager getEquipMakeTemplateManager() {
		return equipMakeTemplateManager;
	}

	public static GuideTemplateManager getGuideTemplateManager() {
		return guideTemplateManager;
	}

	public static ComboTemplateManager getComboTemplateManager() {
		return comboTemplateManager;
	}

	public static StageTemplateManager getStageTemplateManager() {
		return stageTemplateManager;
	}

	public static SkillTemplateManager getSkillTemplateManager() {
		return skillTemplateManager;
	}

	public static RechargeTemplateManager getRechargeTemplateManager() {
		return rechargeTemplateManager;
	}

	public static QuestionTemplateManager getQuestionTemplateManager() {
		return questionTemplateManager;
	}

	public static SpreeTemplateManager getSpreeTemplateManager() {
		return spreeTemplateManager;
	}

	public static TrainingTemplateManager getTrainingTemplateManager() {
		return trainingTemplateManager;
	}

	public static VipPrivilegeTemplateManager getVipPrivilegeTemplateManager() {
		return vipPrivilegeTemplateManager;
	}

	public static LevelUpTemplateManager getLevelUpTemplateManager() {
		return levelUpTemplateManager;
	}

	public static BagTemplateManager getBagTemplateManager() {
		return bagTemplateManager;
	}

	public static NameTemplateManager getNameTemplateManager() {
		return nameTemplateManager;
	}

	public static EnergyTemplateManager getEnergyTemplateManager() {
		return energyTemplateManager;
	}

	public static MeditationTemplateManager getMeditationTemplateManager() {
		return meditationTemplateManager;
	}

	public static BossWarService getBossWarService() {
		return bossWarService;
	}

	public static ActivityTemplateManager getActivityTemplateManager() {
		return activityTemplateManager;
	}

	public static EliteStageTemplateManager getEliteStageTemplateManager() {
		return eliteStageTemplateManager;
	}

	public static RankManager getRankManager() {
		return rankManager;
	}

	public static DirtFilterService getDirtFilterService() {
		return dirtFilterService;
	}

	public static RewardTemplateManager getRewardTemplateManager() {
		return rewardTemplateManager;
	}

	public static MineTemplateManager getMineTemplateManager() {
		return mineTemplateManager;
	}

	public static LevyTemplateManager getLevyTemplateManager() {
		return levyTemplateManager;
	}

	public static OnlineRewardTemplateManager getOnlineRewardTemplateManager() {
		return onlineRewardTemplateManager;
	}

	public static HelperTemplateManager getHelperTemplateManager() {
		return helperTemplateManager;
	}

	public static HonourShopTemplateManager getHonourShopTemplateManager() {
		return honourShopTemplateManager;
	}

	public static FriendService getFriendService() {
		return friendService;
	}

	public static ArenaService getArenaService() {
		return arenaService;
	}

	public static LogService getLogService() {
		return logService;
	}

	public static MarketActivitySetting getMarketActivitySetting() {
		return marketActivitySetting;
	}

	public static TurntableDataService getTurntableDataService() {
		return turntableDataService;
	}

	public static BulletinManager getBulletinManager() {
		return bulletinManager;
	}

	public static GlobalActivityManager getGlobalActivityManager() {
		return globalActivityManager;
	}

	public static SpecialShopNotifyService getSpecialShopNotifyService() {
		return specialShopNotifyService;
	}

	public static SystemTimeService getSystemTimeService() {
		return systemTimeService;
	}

	public static ArenaTemplateManager getArenaTemplateManager() {
		return arenaTemplateManager;
	}

	public static IDataService getDataService() {
		return dataService;
	}

	public static GameFuncService getGameFuncService() {
		return gameFuncService;
	}

	public static WordFilterServiceImpl getWordFilterService() {
		return wordFilterService;
	}

	public static BattleManager getBattleManager() {
		return battleManager;
	}

	public static UUIDService getUuidService() {
		return uuidService;
	}

	public static RewardService getRewardService() {
		return rewardService;
	}

	public static BossWarTemplateManager getBossWarTemplateManager() {
		return bossWarTemplateManager;
	}

	public static DBServiceManager getDbServiceManager() {
		return _dbServiceManager;
	}

	public static MonsterFactory getMonsterFactory() {
		return monsterFactory;
	}

	public static ICompassService getCompassService() {
		return compassService;
	}

	public static GiftTemplateManager getGiftTemplateManager() {
		return giftTemplateManager;
	}

	public static FosterTemplateManager getFosterTemplateManager() {
		return fosterTemplateManager;
	}

	public static ForgeTemplateManager getForgeTemplateManager() {
		return forgeTemplateManager;
	}

	public static RefineTemplateManager getRefineTemplateManager() {
		return refineTemplateManager;
	}

	public static MatchBattleTemplateManager getMatchBattleTemplateManager() {
		return matchBattleTemplateManager;
	}

	public static MatchBattleService getMatchBattleService() {
		return matchBattleService;
	}

	public static BetaRewardTemplateManager getBetaRewardTemplateManager() {
		return betaRewardTemplateManager;
	}

	public static FunctionHelperService getFunctionHelperService() {
		return functionHelperService;
	}

	public static WarriorTemplateManager getWarriorTemplateManager() {
		return warriorTemplateManager;
	}

	public static TurntableTemplateManager getTurntableTemplateManager() {
		return turntableTemplateManager;
	}

	public static RechargeTXTemplateManager getRechargeTXTemplateManager() {
		return rechargeTXTemplateManager;
	}

	public static YellowVipTemplateManager getYellowVipTemplateManager() {
		return yellowVipTemplateManager;
	}

	public static GameServerConfig getGameServerConfig() {
		return gameServerConfig;
	}

	public static TencentUserInfoManager getTencentUserInfoManager() {
		return tencentUserInfoManager;
	}

	/**
	 * 设置物品模板管理器【gm后台需要】
	 * 
	 * @param templatemanager
	 */
	public static void setItemTemplateManager(
			ItemTemplateManager templatemanager) {
		itemTemplateManager = templatemanager;
	}

	/**
	 * 设置装备升级模板管理器【gm后台需要】
	 * 
	 * @param templatemanager
	 */
	public static void setEquipUpgradeTemplateManager(
			EquipUpgradeTemplateManager templatemanager) {
		equipUpgradeTemplateManager = templatemanager;
	}

	public static TitleTemplateManager getTitleTemplateManager() {
		return titleTemplateManager;
	}

	public static void setTitleTemplateManager(
			TitleTemplateManager titleTemplateManager) {
		GameServerAssist.titleTemplateManager = titleTemplateManager;
	}

	public static GlobalLegionManager getGlobalLegionManager() {
		return globalLegionManager;
	}

	public static LegionTemplateManager getLegionTemplateManager() {
		return legionTemplateManager;
	}

	public static GodsoulTemplateManager getGodsoulTemplateManager() {
		return godsoulTemplateManager;
	}

	public static PrisonTemplateManager getPrisonTemplateManager() {
		return prisonTemplateManager;
	}

	public static GlobalPrisonManager getGlobalPrisonManager() {
		return globalPrisonManager;
	}

	public static AbattoirTemplateManager getAbattoirTemplateManager() {
		return abattoirTemplateManager;
	}

	public static GlobalAbattoirManager getGlobalAbattoirManager() {
		return globalAbattoirManager;
	}

	public static BloodTempleTemplateManager getBloodTempleTemplateManager() {
		return bloodTempleTemplateManager;
	}

	public static GlobalBloodTempleManager getGlobalBloodTempleManager() {
		return globalBloodTempleManager;
	}

	public static SpriteTemplateManager getSpriteTemplateManager() {
		return spriteTemplateManager;
	}

	public static GlobalFactionManager getGlobalFactionManager() {
		return globalFactionManager;
	}

	public static MarsTemplateManager getMarsTemplateManager() {
		return marsTemplateManager;
	}

	public static GlobalMarsManager getGlobalMarsManager() {
		return globalMarsManager;
	}

	public static TargetTemplateManager getTargetTemplateManager() {
		return targetTemplateManager;
	}

	public static LegionBossService getLegionBossService() {
		return legionBossService;
	}

	public static LegionBossTemplateManager getLegionBossTemplateManager() {
		return legionBossTemplateManager;
	}

	public static GlobalLegionMineWarManager getGlobalLegionMineWarManager() {
		return globalLegionMineWarManager;
	}

	public static LegionMineWarTemplateManager getLegionMineWarTemplateManager() {
		return legionMineWarTemplateManager;
	}

	public static EscortTemplateManager getEscortTemplateManager() {
		return escortTemplateManager;
	}

	public static GlobalEscortManager getGlobalEscortManager() {
		return globalEscortManager;
	}

	public static PredictTemplateManager getPredictTemplateManager() {
		return predictTemplateManager;
	}
	
}
