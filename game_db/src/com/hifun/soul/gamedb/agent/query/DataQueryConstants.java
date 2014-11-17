package com.hifun.soul.gamedb.agent.query;

/**
 * 数据服务查询常量;
 * 
 * @author crazyjohn
 * 
 */
public class DataQueryConstants {
	/** 根据用户名和密码查询账号信息 */
	public static final String QUERY_ACCOUNT_BY_NAME_AND_PWD = "queryAccountByNameAndPwd";
	/** 查询是否有指定名称的玩家 */
	public static final String QUERY_HUMAN_BY_NAME = "queryHumanByName";
	/** 查询玩家的角色列表 */
	public static final String QUERY_ALL_CHARS = "queryChars";
	/** 根据id查询指定角色的名称 */
	public static final String QUERY_HUMAN_NAME_BY_ID = "queryHumanNameById";
	/** 查询玩家等级排行列表 */
	public static final String QUERY_HUMAN_LEVEL_RANK = "queryHumanLevelRank";
	/** 查询玩家军衔排行列表 */
	public static final String QUERY_HUMAN_TITLE_RANK = "queryHumanTitleRank";
	/** 查询玩家荣誉排行列表 */
	public static final String QUERY_HUMAN_HONOR_RANK = "queryHumanHonorRank";
	/** 查询玩家VIP排行列表 */
	public static final String QUERY_HUMAN_VIP_RANK = "queryHumanVipRank";
	/** 根据角色ID查询玩家的排行榜信息 */
	public static final String QUERY_LEVEL_RANK_BY_HUMAN_ID = "queryLevelRankByHumanId";
	/** 根据角色ID查询玩家的邮件列表 */
	public static final String QUERY_MAIL_LIST_BY_HUMAN_ID = "queryMailListByHumanId";
	/** 根据邮件Id和接收人id删除接收邮件列表中的记录 */
	public static final String DELETE_RECEIVED_MAIL_BY_MAILID_AND_HUMANID = "deleteReceivedMailByMailIdAndHumanId";
	/** 根据账号名查询玩家 */
	public static final String QUERY_ACCOUNT_BY_NAME = "queryAccountByName";
	/** 查询所有玩家账号 */
	public static final String QUERY_ACCOUNT_LIST = "queryAccountList";
	/** 查询所有玩家账号总数 */
	public static final String QUERY_ACCOUNT_COUNT = "queryAccountCount";
	/** 更新账号的锁定状态 */
	public static final String UPDATE_ACCOUNT_STATE = "updateAccountState";
	/** 查询角色列表 */
	public static final String QUERY_CHARACTER_LIST_BY_CONDITION = "queryCharacterListByCondition";
	/** 查询角色列表总数 */
	public static final String QUERY_CHARACTER_COUNT_BY_CONDITION = "queryCharacterCountByCondition";
	/** 根据角色Id查询角色信息 */
	public static final String QUERY_HUMAN_BY_ID = "queryHumanById";
	/** 查询过期公告 */
	public static final String QUERY_VALID_BULLETIN = "queryValidBullet";
	/** 查询公告列表 */
	public static final String QUERY_BULLETIN_LIST_BY_CONDITION = "queryBulletinListByCondition";
	/** 查询公告列表总数 */
	public static final String QUERY_BULLETIN_COUNT_BY_CONDITION = "queryBulletinCountByCondition";
	/** 查询公告列表 */
	public static final String QUERY_ALL_BULLETIN_LIST_BY_CONDITION = "queryAllBulletinListByCondition";
	/** 查询公告列表总数 */
	public static final String QUERY_ALL_BULLETIN_COUNT_BY_CONDITION = "queryAllBulletinCountByCondition";
	/** 查询邮件发送列表 */
	public static final String QUERY_SENT_MAIL_LIST_BY_CONDITION = "querySentMailListByCondition";
	/** 查询邮件发送列表 */
	public static final String QUERY_SENT_MAIL_COUNT_BY_CONDITION = "querySentMailCountByCondition";
	/** 查询所有可用角色的Id */
	public static final String QUERY_ALL_HUMAN_ID = "queryAllHumanId";
	/** 根据角色获取问答信息 */
	public static final String QUERY_QUESTION_INFO_BY_HUMAN_ID = "queryQuestionInfoByHumanId";
	/** 查询大转盘奖励信息 */
	public static final String QUERY_TURNTABLE_REWARD = "queryTurntableReward";
	/** 根据角色id查询物品信息 */
	public static final String QUERY_ITEMS_BY_HUMAN_ID = "queryItemsByHumanId";
	/** 根据角色id查询属性、物品和星运信息 */
	public static final String QUERY_PORPERTY_AND_ITEM_BY_HUMAN_ID = "queryPropertyAndItemByHumanId";
	/** 根据角色id查询属性和技能信息 */
	public static final String QUERY_CHARACTER_BATTLE_IFNO = "queryPropertyAndSkills";
	/** 根据ID查询角色信息 */
	public static final String QUERY_CHARACTER_BY_ID = "queryHumanById";
	/** 所有竞技场玩家信息 */
	public static final String QUERY_ALL_ARENA_MEMBER = "queryAllArenaMember";
	/** 某个玩家的竞技场提示信息 */
	public static final String QUERY_ARENA_NOTICE_BY_HUMANGUID = "queryArenaNoticeByHumanGuid";
	/** 查询数据库中不与单独玩家绑定的某个数据 */
	public static final String QUERY_GLOBAL_KEY_VALUE = "queryGlobalKeyValue";
	/** 根据角色id查询角色基本信息 */
	public static final String QUERY_BASEPORPERTY_BY_HUMANID = "queryBasePorpertyByHumanId";
	/** 查询boss信息 */
	public static final String QUERY_BOSS_INFO = "queryBossInfo";
	/** 查询所有参与boss战的玩家信息 */
	public static final String QUERY_BOSS_ROLE_INFO = "queryBossRoleInfo";
	/** 查询gm问答信息 */
	public static final String QUERY_GM_QUESTIONS_BY_ID = "queryGmQuestionsById";
	/** 查询全局活动状态信息 */
	public static final String QUERY_ACTIVITY_STATE = "queryActivityState";
	/** 根据条件查询玩家反馈信息 */
	public static final String QUERY_QUESTION = "queryQuestion";
	/** 根据条件查询玩家反馈信息数量 */
	public static final String QUERY_QUESTION_COUNT = "queryQuestionCount";
	/** 根据条件查询指定类型的玩家反馈信息 */
	public static final String QUERY_QUESTION_BY_TYPE = "queryQuestionByType";
	/** 根据条件查询指定类型的玩家反馈信息数量 */
	public static final String QUERY_QUESTION_COUNT_BY_TYPE = "queryQuestionCountByType";
	/** 查询未处理的玩家反馈信息 */
	public static final String QUERY_UNANSWERED_QUESTION = "queryUnAnsweredQuestion";
	/** 查询未处理的玩家反馈信息数量 */
	public static final String QUERY_UNANSWERED_QUESTION_COUNT = "queryUnAnsweredQuestionCount";
	/** 查询神秘商店的购买信息 */
	public static final String QUERY_SPECIAL_SHOP_BUY_INFO = "querySpecialShopBuyInfo";
	/** 根据账号id查询角色id */
	public static final String QUERY_HUMAN_GUID_BY_PASSPORTID = "queryHumanGuidByPassportId";
	/** 查询所有运营活动的设置 */
	public static final String QUERY_MARKET_ACTIVITY_SETTING = "queryMarketActivitySetting";
	/** 查询所有好友信息 */
	public static final String QUERY_ALL_FRIEND = "queryAllFriend";
	/** 根据角色id查询好友信息 */
	public static final String QUERY_FRIEND_BY_HUMANID = "queryFriendByHumanId";
	/** 根据openId查询用户信息 */
	public static final String QUERY_ACCOUNT_BY_OPENID = "queryAccountByOpenId";
	/** 根据账户名称查询角色信息 */
	public static final String QUERY_CHARACTERINFO_BY_ACCOUNTNAME = "queryCharacterInfoByAccountName";
	/** 某个玩家的好友切磋信息 */
	public static final String QUERY_FRIEND_BATTLE_BY_HUMANGUID = "queryFriendBattleByHumanGuid";
	/** 根据角色获取参与 */
	public static final String QUERY_MATCH_BATTLE_ROLE_BY_HUMAN_ID = "queryMatchBattleRoleByHumanId";
	/** 根据角色名称查询角色id */
	public static final String QUERY_CHARACTER_ID_BY_NAME = "queryCharacterIdByName";
	/** 查询有效的定时邮件 */
	public static final String QUERY_VALID_TIMING_MAIL_LIST = "queryValidTimingMailList";
	/** 角色职业人数分布统计 */
	public static final String QUERY_CHARACTER_OCCUPATION_STATISTIC = "queryCharacterOccupationStatistic";
	/** 角色等级人数分布统计 */
	public static final String QUERY_CHARACTER_LEVEL_STATISTIC = "queryCharacterLevelStatistic";
	/** 查询所有腾讯用户信息 */
	public static final String QUERY_ALL_TENCENT_USER_INFO = "queryAllTencentUserInfo";
	/** 玩家充值发送到数据后台异步处理 */
	public static final String QUERY_RECHARGE = "queryRecharge";
	/** 玩家充值确认发送到数据后台异步处理 */
	public static final String QUERY_RECHARGE_CONFIRM = "queryRechargeConfirm";
	/** 根据openId和billno查询是否已经记录了该笔充值信息 */
	public static final String QUERY_RECHARGE_BY_OPENID_AND_BILLNO = "queryRechargeByOpenIdAndBillno";
	/** 查询没有走完的充值信息 */
	public static final String QUERY_UNRECHARGED_BY_HUMANID = "queryUnRechargedByHumanId";
	/** 查询平台登录状态 */
	public static final String QUERY_PLATE_LOGIN_STATE = "queryPlateLoginState";
	/** 根据日期查询充值业务流水 */
	public static final String QUERY_QQ_RECHARGE_BY_DATE = "queryQQRechargeByDate";
	/** 根据openId查询充值业务流水 */
	public static final String QUERY_QQ_RECHARGE_BY_OPENID = "queryQQRechargeByOpenId";
	/** 根据humanId查询充值业务流水 */
	public static final String QUERY_QQ_RECHARGE_BY_HUMANID = "queryQQRechargeByHumanId";
	/** 根据业务流水号查询充值业务 */
	public static final String QUERY_QQ_RECHARGE_BY_BILLNO = "queryQQRechargeByBillNO";
	/** 查出所有军团 */
	public static final String QUERY_ALL_LEGION = "queryAllLegion";
	/** 查出所有军团成员 */
	public static final String QUERY_ALL_LEGION_MEMBER = "queryAllLegionMember";
	/** 查出所有军团申请 */
	public static final String QUERY_ALL_LEGION_APPLY = "queryAllLegionApply";
	/** 查出所有军团日志 */
	public static final String QUERY_ALL_LEGION_LOG = "queryAllLegionLog";
	/** 查出所有军团建筑等级 */
	public static final String QUERY_ALL_LEGION_BUILDING = "queryAllLegionBuilding";
	/** 查出所有军团冥想日志 */
	public static final String QUERY_ALL_LEGION_MEDITATION_LOG = "queryAllLegionMeditationLog";
	/** 查出所有军团商品信息 */
	public static final String QUERY_ALL_LEGION_SHOP = "queryAllLegionShop";
	/** 查出所有军团科技信息 */
	public static final String QUERY_ALL_LEGION_TECHNOLOGY = "queryAllLegionTechnology";
	/** 查出所有军团头衔信息 */
	public static final String QUERY_ALL_LEGION_HONOR = "queryAllLegionHonor";
	/** 查询出所有战俘营角色信息 */
	public static final String QUERY_ALL_PRISONER = "queryAllPrisoner";
	/** 查询出所有战俘营日志信息 */
	public static final String QUERY_ALL_PRISON_LOG = "queryAllPrisonLog";
	/** 查询出所有角斗场房间信息 */
	public static final String QUERY_ALL_ABATTOIR_ROOM = "queryAllAbattoirRoom";
	/** 查询出角色相关的角斗场日志 */
	public static final String QUERY_ABATTOIR_LOG_BY_HUMAN_ID = "queryAbattoirLogByHumanId";
	/** 查询出所有角色的角斗场威望*/
	public static final String QUERY_ALL_ABATTOIR_PRESTIGE = "queryAllAbattoirPrestige";
	/** 查询出所有嗜血神殿房间信息 */
	public static final String QUERY_ALL_BLOOD_TEMPLE_ROOM = "queryAllBloodTempleRoom";
	/** 查询出角色相关的嗜血神殿日志 */
	public static final String QUERY_BLOOD_TEMPLE_LOG_BY_HUMAN_ID = "queryBloodTempleLogByHumanId";
	/** 查询出所有角色的嗜血神殿威望 */
	public static final String QUERY_ALL_BLOOD_TEMPLE_PRESTIGE = "queryAllBloodTemplePrestige";
	/** 查询所有阵营成员的数据 */
	public static final String QUERY_ALL_Faction_Member = "queryAllFactionMember";
	/** 查询所有战神之巅玩家的数据 */
	public static final String QUERY_ALL_MARS_MEMBER = "queryAllMarsMember";
	/** 查询军团boss信息 */
	public static final String QUERY_LEGION_BOSS_INFO = "queryLegionBossInfo";
	/** 查询所有参与军团boss战的玩家信息 */
	public static final String QUERY_LEGION_BOSS_ROLE_INFO = "queryLegionBossRoleInfo";
	/** 查询所有参与军团矿战的玩家信息 */
	public static final String QUERY_ALL_LEGION_MINE_MEMBER = "queryAllLegionMineMember";
	/** 查询所有军团矿战矿堆信息 */
	public static final String QUERY_ALL_LEGION_MINE = "queryAllLegionMine";
	/** 查询所有押运信息 */
	public static final String QUERY_ALL_ESCORT = "queryAllEscort";
	/** 查询所有押运邀请信息 */
	public static final String QUERY_ALL_ESCORT_INVITE = "queryAllEscortInvite";
	/** 查询押运拦截排行信息 */
	public static final String QUERY_ESCORT_ROB_RANK = "queryEscortRobRank";
	/** 查询所有押运军团祈福信息 */
	public static final String QUERY_ALL_ESCORT_LEGION_PRAY = "queryAllEscortLegionPray";
	/** 查询押运拦截日志信息 */
	public static final String QUERY_ALL_ESCORT_LOG = "queryAllEscortLog";
	/** 查询押运协助信息 */
	public static final String QUERY_ALL_ESCORT_HELP = "queryAllEscortHelp";
}
