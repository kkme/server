// 全局常量信息

// =========== 战斗相关常量 ==========//

/** 玩家战斗时候,移动失败, 扣的血量 */
config.deductHpWhenMoveFailed = 0;
/** 等级差系数-万分制 */
config.levelDifferFactor = 10;
/** 等级差最大值-万分制 */
config.maxLevelDiffer = 1000;
/** 等级差最小值-万分制 */
config.minLevelDiffer = -1000;
/** 攻击系数 */
config.attackFactor = 1;
/** 防御系数 */
config.defenseFactor = 500;
/** 基础暴击系数 */
config.baseCritFactor = 1.5;
/** 基础格挡系数 */
config.baseParryFactor = 1.5;
/** 基础命中 */
config.baseHit = 0.95;
/** 白宝石恢复能量上限百分比; 万分制 */
config.whiteGemRecoverEnergyRate = 500;
/** 单位黑宝石攻击加成 ;万分制 */
config.blackGemAttackPerAddRate = 300;
/** 最大pve战斗回合数 */
config.maxPveBattleRound = 30;
/** 最大pvp战斗回合数 */
config.maxPvpBattleRound = 30;

// =========== 聊天相关常量 ==========//
/** 私聊开始符 */
config.privateChatPrfix = "/";
/** 世界聊天间隔,单位秒 */
config.worldChatInterval = 20;
/** 私人聊天间隔,单位秒 */
config.privateChatInterval = 1;
/** 军团聊天间隔,单位秒 */
config.guildChatInterval = 1;
/** 组队聊天间隔,单位秒 */
config.teamChatInterval = 1;
/** 附近聊天间隔,单位秒 */
config.nearChatInterval = 1;
/** 世界聊天内容长度限制 */
config.worldChatLength = 100;
/** 私人聊天内容长度限制 */
config.privateChatLength = 100;
/** 军团聊天内容长度限制 */
config.guildChatLength = 100;
/** 组队聊天内容长度限制 */
config.teamChatLength = 100;
/** 附近聊天内容长度限制 */
config.nearChatLength = 100;

// =========== 好友相关常量 ==========//
/** 好友数量上限 */
config.friendMax = 40;
/** 推荐好友的数量 */
config.friendRecommendNum = 5;
/** 好友单次奖励体力值 */
config.friendRewardEnergy = 2;
/** 好友奖励次数上限 */
config.friendRewardMax = 5;
/** 好友申请显示数量上限 */
config.friendApplyMaxNum = 10;
/** 最近联系人上限 */
config.maxLatestFriendNum = 10;
/** 好友战斗提示信息上限 */
config.maxFriendBattleInfoSize = 10;
/** 每天自己送出的好友体力次数，不能超过100 */
config.maxSendEnergyTimes = 40;
/** 每天好友送给自己的好友体力次数，不能超过100 */
config.maxBeSendedEnergyTimes = 40;

// =========== 转盘相关常量 ==========//
/** 免费次数 */
config.turntableFreeTime = 0;
/** 显示玩家抽取奖品的数量 */
config.turntableRewardShowNum = 10;
/** 每次抽奖消耗勇者之心数量 */
config.turntableCost = 15;

// =========== 税收相关常量 ==========//
/** 每日收税次数 */
config.levyTime = 8;
/** 收集魔法石奖励货币类型 */
config.collectRewardType = 1;
/** 收集魔法石奖励基数 */
config.collectRewardBaseNum = 500;
/** 完成收集魔法石任务税收加成比例 */
config.leveExtraRate = 1000;
/** 税收加成上限 */
config.maxLeveExtraRate = 20000;
/** 免费收集宝石次数 */
config.freeCollectNum = 15;
/** 每日常规押注次数 */
config.levyBetTime = 20;
/** 押注胜利税收加成 */
config.winLevyExtraRate = 1000;

// =========== 训练场相关常量 ==========//
/** 普通训练每日训练时长限制（分钟） */
config.normalTrainingMaxTime = 24 * 60;
/** vip训练魔晶消费限制 */
config.vipTraingMaxCrystalConsume = 500;

// =========== 问答相关常量 ==========//
/** 每日问答的题数 */
config.dailyQuestionCount = 10;
/** 每日赠送的祈福次数 */
config.dailyHandselBlessNum = 1;
/** 赠送的祈福次数累积最大值 */
config.maxHandselBlessNum = 6;
/** 使用祈福时的收益倍率 */
config.blessRevenueRate = 2;
/** 一键答题每题消耗魔晶 */
config.onekeyAnswerSingleCost = 9;
/** 一键答题vip开启等级 */
config.onekyAnswerVipLevel = 5;

// =========== 排行榜相关常量 ==========//
/** 角色等级排行榜入榜数量 */
config.levelRankNum = 100;
/** 角色军衔排行榜入榜数量 */
config.titleRankNum = 100;

// =========== 体力值相关常量 ==========//
/** 体力值上限 */
config.maxEnergy = 116;
/** 体力值恢复时间间隔（分钟） */
config.energyRecoverInterval = 15;
/** 单次自动恢复的体力值(已废弃) */
config.energyRecoverNum = 0;
/** 单次手动恢复的体力值 */
config.energyHandRecoverNum = 8;
/** 每日手动恢复体力次数 */
config.energyHandRecoverTimes = 7;
/** 每日自动恢复体力值 */
config.energyDailyRecoverNum = 50;

// =========== 冥想相关常量 ==========//
/** 有报酬的协助冥想次数 */
config.hasRewardMeditationAssist = 8;
/** 计算冥想收益的单位时长（秒） */
config.meditationTimeUnit = 60;

// =========== 创建角色相关 ==========//
config.maxRoleNameSize = 10;
config.minRoleNameSize = 4;

// =========== 矿场相关 ==========//
/** 最大免费开矿坑次数 */
config.maxFreeMineNum = 10;
/** 战斗失败或放弃战斗默认开启的矿坑类型 */
config.defaultMineFieldType = 1;

// =========== gm问题反馈相关 ==========//
/** 每日提交问题反馈次数上限 */
config.submitGmQuestionNum = 10;

// =========== 发送邮件相关 ==========//
/** 每日最大发送邮件数 */
config.maxSendMailNum = 100;
/** 收件箱邮件数上限 */
config.maxRecievedMailNum = 40;

// =========== 防沉迷相关 ==========//
/** 防沉迷开关 */
config.antiIndulgeSwitch = false;
/** 同步收益比率的间隔时间 */
config.updateRevenueRateTimeSpan = 5 * 60 * 1000;

// =========== 装备制作相关 ==========//
config.equipMakeDownLevel = 10;

// =========== 装备洗炼相关 ==========//
config.equipForgeFreeTimes = 0;

// =========== 试炼相关 ==========//
config.refineRefreshTimes = 1;

// ===========勇者之路 ===========//
/** 挑战陌生人获得勇者之心数 */
config.warriorChallengeStrangerRewardNum = 1;
/** 挑战好友获得勇者之心数 */
config.warriorChallengeFriendRewardNum = 2;
/** 挑战NPC获得勇者之心数 */
config.warriorChallengeNpcRewardNum = 3;
/** 每轮任务完成后的cd时间(单位s) */
config.warriorQuestCdTime = 5 * 60;
/** 匹配对手等级差 */
config.warriorChallengeLevelDiff = 1;
/** 刷新对手cd时间（单位s) */
config.warriorRefreshOpponentCd = 5;
/** 接受勇者挑战获得奖励的最大次数 */
config.maxWarriorChallengeRewardTimes = 30;

// ===========战斗引导 ===========//
config.battleGuideFinishStageId = 2;

// ==========神魄相关============//
config.upgradeCostCoin = 500;
config.upgradeCostCrystal = 5;
config.amendSuccessRate = 500;

// ==========战俘营相关==========//
/** 没用 */
config.defaultArrestRoomNum = 2;
/** 免费抓捕次数 */
config.freeArrestNum = 3;
/** 手下败将显示个数 */
config.looserNumLimit = 10;
/** 夺俘之敌显示个数 */
config.enemyNumLimit = 10;
/** 抓捕等级差 */
config.arrestLevelDiff = 10;
/** 营救等级差 */
config.rescueLevelDiff = 10;
/** 互动次数上限 */
config.interactNumLimit = 6;
/** 反抗次数上限 */
config.revoltNumLimit = 3;
/** 营救次数上限 */
config.rescueNumLimit = 3;
/** 求救次数上限 */
config.forHelpNumLimit = 2;
/** 奴隶数上限 */
config.maxSlaveNum = 3;
/** 奴隶劳作时间 */
config.holdSlaveTimeLimit = 24;
/** 互动保护时间 */
config.interactTimeLimit = 20;
/** 互动双倍概率 没用 */
config.interactDoubleExperience = 0;
/** 求救列表数量 */
config.forHelpMemberNumLimit = 8;
/** 加速劳作1小时花费 */
config.costCrystalPerHourExp = 10;

// ==========精灵相关==========//
/** 单个精灵格子消耗魔晶数量 */
config.perSpriteBagCellCostCrystal = 50;

//==========附魔相关==========//
/** 换系消耗魔晶数量 */
config.changeSkillDevelopCostCrystal = 10;

//==========阵营相关==========//
/** 随机加入阵营的金币奖励 */
config.randomSelectFactionRewardCoin = 50000;

//==========战神之巅相关==========//
/** 刷新房间消费魔晶数量 */
config.refreshMarsRoomCost = 10;
/** 解锁房间消费魔晶数量 */
config.unlockMarsRoomCost = 50;
/** 每日默认击杀次数 */
config.dayKillNum = 10;
/** 每日默认加倍次数 */
config.multipleNum = 3;
/** 每次接受挑战奖励次数 */
config.dayReceiveRewardNum = 10;
/** 每杀戮值对应威望数 */
config.killValueToPrestigeRate = 100;
/** 杀戮值对阵营贡献系数 */
config.killValueToFactionRate = 0.1;

//==========日常任务相关==========//
/** 免费接受日常任务次数 */
config.freeQuestNum = 10;
/** 魔晶刷新任务列表消耗 */
config.refreshDailyQuestsCost = 5;

//==========嗜血神殿相关==========//
/** 嗜血神殿开启所需军衔等级 */
config.bloodTempleOpenTitle = 5;
