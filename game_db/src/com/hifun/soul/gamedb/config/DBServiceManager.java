package com.hifun.soul.gamedb.config;

import java.io.IOException;
import java.util.Map;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;

import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.orm.DBConfiguration;
import com.hifun.soul.core.orm.DBServiceFactory;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.uuid.UUIDService;
import com.hifun.soul.gamedb.DBMessageProcessorDispatcher;
import com.hifun.soul.gamedb.IDBDispatcher;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.agent.CommonEntityAgent;
import com.hifun.soul.gamedb.agent.DBAgent;
import com.hifun.soul.gamedb.agent.HumanAgent;
import com.hifun.soul.gamedb.agent.HumanCacheData;
import com.hifun.soul.gamedb.agent.HumanSubEntityAgent;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.agent.XQLAgent;
import com.hifun.soul.gamedb.agent.query.CommonQueryExecutor;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.agent.query.decorator.BattleInfoQueryDecorator;
import com.hifun.soul.gamedb.agent.query.decorator.GetCharsDecorator;
import com.hifun.soul.gamedb.agent.query.decorator.HumanBaseInfoQueryDecorator;
import com.hifun.soul.gamedb.agent.query.decorator.HumanItemQueryDecorator;
import com.hifun.soul.gamedb.agent.query.decorator.HumanQueryDecorator;
import com.hifun.soul.gamedb.agent.query.decorator.LoginAccountQueryDecorator;
import com.hifun.soul.gamedb.cache.converter.HumanCacheToEntityConverter;
import com.hifun.soul.gamedb.cache.update.AbstractDBUpdateThread;
import com.hifun.soul.gamedb.cache.update.CacheToDBUpdateThreadSynchronizer;
import com.hifun.soul.gamedb.cache.update.DBUpdateBlockingThread;
import com.hifun.soul.gamedb.cache.update.DBUpdateNonBlockingThread;
import com.hifun.soul.gamedb.cache.update.EntityUpdater;
import com.hifun.soul.gamedb.entity.*;
import com.hifun.soul.gamedb.mock.QQOpenIdQuery;
import com.hifun.soul.gamedb.mock.QQPlateLoginStateQuery;
import com.hifun.soul.gamedb.mock.QQRechargeConfirmQuery;
import com.hifun.soul.gamedb.mock.QQRechargeQuery;
import com.hifun.soul.gamedb.monitor.DBOperationType;
import com.hifun.soul.gamedb.msg.handler.DBMessageHandler;

/**
 * 管理DBService实例的类。<br>
 * FIXME: crazyjohn 这里需要详细注释, 如何区分角色处理器和其它处理器; 角色实体：玩家缓存数据相关的实体<br>
 * 其它实体：于玩家缓存数据无关的实体;<br>
 * 
 * @author crazyjohn
 */
public class DBServiceManager {
	/** 数据服务 */
	private IDBService dbService;
	/** 数据代理入口 */
	@Autowired
	private IDBAgent dbAgent;
	/** 角色代理 */
	private HumanAgent humanAgent;
	/** 角色子实体代理 */
	private HumanSubEntityAgent<? extends IHumanSubEntity> subEntityAgent;
	/** 通用代理 */
	private CommonEntityAgent<? extends IEntity> commonAgent;
	private XQLAgent queryAgent;
	/** 数据库更新器 */
	private CacheToDBUpdateThreadSynchronizer cacheToDBUpdateSynchronizer;
	/** 数据库更新线程 */
	private AbstractDBUpdateThread dbUpdateThread;
	/** 数据服务执行器 */
	private IDBDispatcher dbExecutorService;
	private UUIDService uuidService;
	private LocalConfig localConfig;

	/**
	 * 使用DBConfiguration创建DBService的实例
	 * 
	 * @param dbConfig
	 * @throws IOException
	 * @throws ScriptException
	 */
	public DBServiceManager(DBMessageHandler dbMessageHandler,
			DBConfiguration dbConfig, UUIDService uuidService,
			LocalConfig localConfig) {
		this.uuidService = uuidService;
		this.localConfig = localConfig;
		dbService = DBServiceFactory.createDBService(dbConfig);
		dbAgent = new DBAgent();
		dbMessageHandler.setDBAgent(dbAgent);
		// 构建DB分发器
		dbExecutorService = new DBMessageProcessorDispatcher(dbMessageHandler);
		initDBAgent(dbAgent);

	}

	/**
	 * 构建数据库代理;
	 * 
	 * @param dbAgent
	 */
	private void initDBAgent(IDBAgent dbAgent) {
		// ========== 装配数据库代理 ==========
		// 构建查询代理对象和实体代理对象
		queryAgent = new XQLAgent(dbService);
		humanAgent = new HumanAgent(SharedConstants.MAX_CACHE_SIZE, dbService,
				queryAgent);
		// 1. 构建查询代理
		registerQueryAgent();
		// 2. 构建实体代理
		registerEntityAgent();
		// 3. 構建緩存代理
		registerCacheAgent();
		// 4. 构建数据库更新线程
		buildUpdateThread(false, 200);
		// 5. 构建特殊查询
		buildSpecialQuery();
		// 6. 注册实体处理器
		registerEntityProcessor();
	}

	/**
	 * 注册实体消息处理器;
	 */
	private void registerEntityProcessor() {
		// 玩家相关的实体
		this.dbExecutorService.registerHumanEntityProcessor(this.humanAgent
				.getBindClasses());
		this.dbExecutorService.registerHumanEntityProcessor(this.subEntityAgent
				.getBindClasses());
		// 玩家无关的实体
		this.dbExecutorService.registerCommonXXEntityProcessor(this.commonAgent
				.getBindClasses());
	}

	/**
	 * 构建特殊查询;
	 */
	private void buildSpecialQuery() {
		this.dbExecutorService
				.registerSpecialQuery(DataQueryConstants.QUERY_ACCOUNT_BY_NAME_AND_PWD);
		// 根據openId查詢;
		this.dbExecutorService
				.registerSpecialQuery(DataQueryConstants.QUERY_ACCOUNT_BY_OPENID);
		// QQ腾讯充值
		this.dbExecutorService
				.registerSpecialQuery(DataQueryConstants.QUERY_RECHARGE);
		// QQ腾讯充值确认(执行给玩家添加物品操作)
		this.dbExecutorService
				.registerSpecialQuery(DataQueryConstants.QUERY_RECHARGE_CONFIRM);
		// QQ登录状态保持
		this.dbExecutorService
				.registerSpecialQuery(DataQueryConstants.QUERY_PLATE_LOGIN_STATE);
	}

	private void buildUpdateThread(boolean isDBUpdateBlocking,
			int maxQueueSizeOfDBUpdate) {
		// 构建更新线程
		// TODO 以下的参数要配置化
		if (isDBUpdateBlocking) {
			dbUpdateThread = new DBUpdateBlockingThread(maxQueueSizeOfDBUpdate);
		} else {
			dbUpdateThread = new DBUpdateNonBlockingThread(
					maxQueueSizeOfDBUpdate, 1000, maxQueueSizeOfDBUpdate);
		}
		// editby:crazyjohn 2013-12-27之前的差异更新有问题,这里暂时注释掉改为使用全部更新;
		// HumanEntityDiffUpdater humanUpdater = new
		// HumanEntityDiffUpdater(dbService);
		dbUpdateThread.regisgerUpdator(HumanEntity.class, new EntityUpdater(
				dbService));

		cacheToDBUpdateSynchronizer = new CacheToDBUpdateThreadSynchronizer(
				dbAgent, dbUpdateThread, maxQueueSizeOfDBUpdate);
		cacheToDBUpdateSynchronizer.registEntityConverter(HumanCacheData.class,
				new HumanCacheToEntityConverter());
	}

	/**
	 * 構建緩存代理;
	 */
	private void registerCacheAgent() {
		// 角色缓存代理
		dbAgent.registerCacheableAgent(humanAgent);
	}

	/**
	 * 构建实体代理;
	 * 
	 * @param queryAgent
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void registerEntityAgent() {
		// 角色子实体代理
		subEntityAgent = new HumanSubEntityAgent(humanAgent, dbService,
				new Class[] { HumanQuestDataEntity.class,
						HumanQuestFinishDataEntity.class,
						HumanBasePropertiesEntity.class,
						HumanOtherPropertiesEntity.class,
						HumanItemEntity.class, HumanHoroscopeEntity.class,
						HumanStargazerEntity.class, HumanCdEntity.class,
						HumanGuideEntity.class, HumanStageDramaEntity.class,
						HumanStageMapStateEntity.class,
						HumanStageRewardEntity.class,
						HumanTechnologyEntity.class,
						HumanLoginRewardEntity.class,
						HumanCarriedSkillEntity.class,
						HumanCostNotifyEntity.class,
						HumanMeditationEntity.class,
						HumanEliteStageEntity.class,
						HumanDailyQuestRewardBoxEntity.class,
						HumanMineEntity.class,
						HumanSpecialShopItemEntity.class,
						HumanStageStarEntity.class, HumanMainCityEntity.class,
						HumanQuestionEntity.class, HumanSkillSlotEntity.class,
						HumanStageStarRewardEntity.class,
						HumanGiftEntity.class, HumanRefineMapEntity.class,
						HumanRefineStageEntity.class,
						HumanMatchBattleEntity.class,
						HumanSpecialLoginRewardEntity.class,
						HumanWarriorEntity.class,
						HumanYellowVipRewardStateEntity.class,
						HumanGodsoulEntity.class, HumanPubSpriteEntity.class,
						HumanSpriteEntity.class, HumanSpriteBuffEntity.class,
						HumanSpriteBagCellEntity.class,
						HumanSpriteSlotEntity.class, HumanStarMapEntity.class,
						HumanSignEntity.class,
						HumanSingleRechargeRewardEntity.class,
						HumanTotalRechargeRewardEntity.class,
						HumanMarsRoomEntity.class, HumanMarsLoserEntity.class,
						HumanTargetEntity.class, HumanNostrumEntity.class,
						HumanLegionTaskEntity.class, HumanMagicPaperEntity.class,
						HumanLegionMineBattleRewardEntity.class});
		// 通用代理
		commonAgent = new CommonEntityAgent<IEntity>(dbService, new Class[] {
				RankEntity.class, SentMailEntity.class,
				ReceivedMailEntity.class, BulletinEntity.class,
				RechargeEntity.class, TurntableEntity.class,
				ArenaMemberEntity.class, ArenaNoticeEntity.class,
				GlobalKeyValueEntity.class, BossEntity.class,
				BossRoleEntity.class, ActivityEntity.class,
				QuestionEntity.class, SpecialShopNotifyEntity.class,
				MarketActivitySettingEntity.class, FriendEntity.class,
				FriendBattleEntity.class, MailDraftEntity.class,
				QQRechargeEntity.class, TencentUserInfoEntity.class,
				TitleRankEntity.class, LegionEntity.class,
				HonorRankEntity.class, VipRankEntity.class,
				LegionMemberEntity.class, LegionApplyEntity.class,
				LegionLogEntity.class, PrisonerEntity.class,
				PrisonLogEntity.class, AbattoirRoomEntity.class,
				AbattoirLogEntity.class, HumanAbattoirPrestigeEntity.class,
				BloodTempleRoomEntity.class, BloodTempleLogEntity.class,
				HumanBloodTemplePrestigeEntity.class, FactionMemberEntity.class,
				MarsMemberEntity.class, LegionBossEntity.class, LegionBossRoleEntity.class,
				LegionMineMemberEntity.class, LegionMineEntity.class,
				EscortEntity.class, EscortInviteEntity.class, EscortLegionPrayEntity.class,
				EscortLogEntity.class, EscortRobRankEntity.class, EscortHelpEntity.class,
				LegionBuildingEntity.class, LegionMeditationLogEntity.class,
				LegionShopEntity.class, LegionTechnologyEntity.class, LegionHonorEntity.class});
		dbAgent.registerEntityAgent(humanAgent);
		dbAgent.registerEntityAgent(commonAgent);
		dbAgent.registerEntityAgent(subEntityAgent);
	}

	/**
	 * 构建查询代理;
	 * 
	 * @return
	 */
	private void registerQueryAgent() {
		// 构建查询代理
		IXQLExecutor commonQuery = new CommonQueryExecutor(dbService);
		LoginAccountQueryDecorator loginAccountQuery = new LoginAccountQueryDecorator(
				commonQuery);
		// openId查询
		QQOpenIdQuery openIdQuery = new QQOpenIdQuery(localConfig, dbService,
				uuidService);
		// 充值
		QQRechargeQuery rechargeQuery = new QQRechargeQuery(localConfig);
		// 充值确认
		QQRechargeConfirmQuery rechargeConfirmQuery = new QQRechargeConfirmQuery(
				localConfig, dbService);
		// QQ登录状态保持
		QQPlateLoginStateQuery plateLoginStateQuery = new QQPlateLoginStateQuery(
				localConfig);

		// 角色战斗信息查询
		BattleInfoQueryDecorator battleInfoQuery = new BattleInfoQueryDecorator(
				commonQuery, humanAgent);
		GetCharsDecorator getCharsQuery = new GetCharsDecorator(commonQuery);
		HumanItemQueryDecorator humanItemQuery = new HumanItemQueryDecorator(
				commonQuery);
		// 角色基本信息查询（属性、物品、星运）
		HumanBaseInfoQueryDecorator humanBaseInfoQuery = new HumanBaseInfoQueryDecorator(
				commonQuery, humanAgent);
		dbAgent.registerXQLAgent(queryAgent);

		// !!!!!!注意：這裡的原則是要保证数据的读写都是一个线程;
		// FIXME: crazyjohn !!!!!!程序員写这里的代码理解成本较高需要重构;

		// ========== 1. 构建角色相关的查询 ==========
		// 账号查询
		registerHumanQuery(DataQueryConstants.QUERY_ACCOUNT_BY_NAME_AND_PWD,
				loginAccountQuery);
		// openId查询
		registerHumanQuery(DataQueryConstants.QUERY_ACCOUNT_BY_OPENID,
				openIdQuery);

		// 查询玩家名字
		registerHumanQuery(DataQueryConstants.QUERY_HUMAN_BY_NAME, commonQuery);
		// 根据ID查询玩家数据
		registerHumanQuery(DataQueryConstants.QUERY_HUMAN_BY_ID,
				new HumanQueryDecorator(commonQuery, dbService));
		// 查询玩家所有角色
		registerHumanQuery(DataQueryConstants.QUERY_ALL_CHARS, getCharsQuery);
		// 查询玩家物品
		registerHumanQuery(DataQueryConstants.QUERY_ITEMS_BY_HUMAN_ID,
				humanItemQuery);
		// 查询玩家名字
		registerHumanQuery(DataQueryConstants.QUERY_HUMAN_NAME_BY_ID,
				commonQuery);
		// 查询玩家的装备星运属性等信息
		registerHumanQuery(
				DataQueryConstants.QUERY_PORPERTY_AND_ITEM_BY_HUMAN_ID,
				humanBaseInfoQuery);
		// 查询玩家战斗信息
		registerHumanQuery(DataQueryConstants.QUERY_CHARACTER_BATTLE_IFNO,
				battleInfoQuery);

		// ========== 2. 构建角色无关的其它查询 ==========
		// 查询无效的公告
		registerCommonEntityQuery(DataQueryConstants.QUERY_VALID_BULLETIN,
				commonQuery);
		// 查询所有的玩家id
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_HUMAN_ID,
				commonQuery);
		// 查询gm问答信息1
		registerCommonEntityQuery(DataQueryConstants.QUERY_GM_QUESTIONS_BY_ID,
				commonQuery);
		// 查询gm问答信息2
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_QUESTION_INFO_BY_HUMAN_ID, commonQuery);
		// 查询所有竞技场成员信息
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ARENA_MEMBER,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_GLOBAL_KEY_VALUE,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_BOSS_INFO,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_BOSS_ROLE_INFO,
				commonQuery);
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_LEVEL_RANK_BY_HUMAN_ID, commonQuery);
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_MARKET_ACTIVITY_SETTING, commonQuery);
		// 好友相关的查询
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_FRIEND,
				commonQuery);
		// 有效的定时邮件
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_VALID_TIMING_MAIL_LIST, commonQuery);
		// 查询是否有未完成的充值订单
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_UNRECHARGED_BY_HUMANID, commonQuery);
		// QQ充值
		registerCommonEntityQuery(DataQueryConstants.QUERY_RECHARGE,
				rechargeQuery);
		// QQ充值确认
		registerCommonEntityQuery(DataQueryConstants.QUERY_RECHARGE_CONFIRM,
				rechargeConfirmQuery);
		// QQ登录状态保持
		registerCommonEntityQuery(DataQueryConstants.QUERY_PLATE_LOGIN_STATE,
				plateLoginStateQuery);
		// 查询所有腾讯用户信息
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_ALL_TENCENT_USER_INFO, commonQuery);
		// 查出所有军团
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION,
				commonQuery);
		// 查出所有军团成员
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_MEMBER,
				commonQuery);
		// 查出所有军团申请
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_APPLY,
				commonQuery);
		// 查出所有军团日志
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_LOG,
				commonQuery);
		// 查出所有军团冥想日志
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_MEDITATION_LOG,
				commonQuery);
		// 查出所有军团建筑等级
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_BUILDING,
				commonQuery);
		// 查出所有军团商品信息
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_SHOP,
				commonQuery);
		// 查出所有军团科技信息
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_TECHNOLOGY,
				commonQuery);
		// 查出所有军团头衔信息
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_HONOR,
				commonQuery);
		// 查询所有的战俘营角色
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_PRISONER,
				commonQuery);
		// 查询所有的战俘营日志
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_PRISON_LOG,
				commonQuery);
		// 查询出所有角斗场房间信息
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ABATTOIR_ROOM,
				commonQuery);
		// 查询出角色相关的角斗场日志
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_ABATTOIR_LOG_BY_HUMAN_ID, commonQuery);
		// 查询出所有角色的角斗场荣誉
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ABATTOIR_PRESTIGE,
				commonQuery);
		// 查询出所有嗜血神殿房间信息
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_ALL_BLOOD_TEMPLE_ROOM, commonQuery);
		// 查询出角色相关的嗜血神殿日志
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_BLOOD_TEMPLE_LOG_BY_HUMAN_ID,
				commonQuery);
		// 查询出所有角色的嗜血神殿荣誉
		registerCommonEntityQuery(
				DataQueryConstants.QUERY_ALL_BLOOD_TEMPLE_PRESTIGE, commonQuery);
		// 查询所有的阵营成员信息
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_Faction_Member,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_LEGION_BOSS_INFO,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_LEGION_BOSS_ROLE_INFO,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_MINE_MEMBER,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_LEGION_MINE,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ESCORT,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ESCORT_INVITE,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ESCORT_ROB_RANK,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ESCORT_LEGION_PRAY,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ESCORT_LOG,
				commonQuery);
		registerCommonEntityQuery(DataQueryConstants.QUERY_ALL_ESCORT_HELP,
				commonQuery);

		// ========== 以下是要分页查询的需要特殊处理 ==========
		GameConstants constants = (GameConstants) ApplicationContext
				.getApplicationContext().getDefaultListableBeanFactory()
				.getSingleton(GameConstants.class.getSimpleName());
		// 构建limit query
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_TURNTABLE_REWARD,
				constants.getTurntableRewardShowNum());

		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_HUMAN_LEVEL_RANK,
				constants.getLevelRankNum());
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_HUMAN_TITLE_RANK,
				constants.getTitleRankNum());
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_HUMAN_HONOR_RANK,
				constants.getTitleRankNum());
		registerCommonEntityLimitQuery(DataQueryConstants.QUERY_HUMAN_VIP_RANK,
				constants.getTitleRankNum());
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_SPECIAL_SHOP_BUY_INFO,
				SharedConstants.SPECIAL_SHOP_NOTIFY_NUM);
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_ARENA_NOTICE_BY_HUMANGUID,
				SharedConstants.ARENA_NOTICE_NUM);
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_MAIL_LIST_BY_HUMAN_ID,
				constants.getMaxRecievedMailNum());
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_BLOOD_TEMPLE_LOG_BY_HUMAN_ID,
				SharedConstants.BLOOD_TEMPLE_ENEMY_NUM);
		// 查询好友战斗信息
		registerCommonEntityLimitQuery(
				DataQueryConstants.QUERY_FRIEND_BATTLE_BY_HUMANGUID,
				constants.getMaxFriendBattleInfoSize());
	}

	private void registerCommonEntityLimitQuery(String queryName, int limitNum) {
		queryAgent.registerLimitXQLExecutor(queryName, limitNum);
		this.dbExecutorService.registerCommonXXEntityQuery(queryName);
	}

	/**
	 * 注册角色相关的查询, 由角色处理器处理;
	 * 
	 * @param queryName
	 * @param executor
	 */
	private void registerHumanQuery(String queryName, IXQLExecutor executor) {
		queryAgent.registerXQLExecutor(queryName, executor);
		this.dbExecutorService.registerHumanEntityQuery(queryName);
	}

	/**
	 * 注册角色无关的实体查询, 由其他实体处理器处理;
	 * 
	 * @param queryName
	 * @param executor
	 */
	private void registerCommonEntityQuery(String queryName,
			IXQLExecutor executor) {
		queryAgent.registerXQLExecutor(queryName, executor);
		this.dbExecutorService.registerCommonXXEntityQuery(queryName);
	}

	public IDBService getDBService() {
		return dbService;
	}

	public IDBAgent getDBAgent() {
		return dbAgent;
	}

	public CacheToDBUpdateThreadSynchronizer getDBSynchronizer() {
		return cacheToDBUpdateSynchronizer;
	}

	public IDBDispatcher getDBMessageProcessor() {
		return dbExecutorService;
	}

	public AbstractDBUpdateThread getUpdateThread() {
		return this.dbUpdateThread;
	}

	public Map<Class<?>, Map<DBOperationType, Integer>> getEntityDBOperationInfos() {
		return dbAgent.getEntityDBOperationInfos();
	}

	public Map<String, Integer> getDbQueryTimesInfo() {
		return dbAgent.getDbQueryTimesInfo();
	}
}
