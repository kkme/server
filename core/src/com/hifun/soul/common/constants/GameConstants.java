package com.hifun.soul.common.constants;

import com.hifun.soul.core.config.Config;

/**
 * 游戏全局参数定义，在resource/script/constants.js中填写
 * 
 */
public class GameConstants implements Config {
	/** 确认对话框 OK 按钮返回值 */
	public static final String OPTION_OK = "ok";
	/** 确认对话框 Cancel 按钮返回值 */
	public static final String OPTION_CANCEL = "cancel";
	
	// ===== 战斗相关常量 =====//
	/** 玩家战斗时候,移动失败, 扣的血量 */
	private int deductHpWhenMoveFailed;
	/** 等级差系数 */
	private int levelDifferFactor;
	/** 等级差最大值 */
	private int maxLevelDiffer;
	/** 等级差最小值 */
	private int minLevelDiffer;
	/** 攻击系数 */
	private int attackFactor;
	/** 防御系数 */
	private int defenseFactor;
	/** 基础暴击系数 */
	private float baseCritFactor;
	/** 基础格挡系数 */
	private float baseParryFactor;
	/** 基础命中系数 */
	private float baseHit;
	/** 单位黑宝石攻击加成 ;万分制 */
	private int blackGemAttackPerAddRate = 100; 
	/** 白宝石恢复能量上限百分比; 万分制 */
	private int whiteGemRecoverEnergyRate = 500;
	/** 最大黑宝石攻击加成 ;万分制 */
	private int blackMaxAddRate = 100000;
	/** 最大pve战斗回合数 */
	private int maxPveBattleRound = 50;
	/** 最大pvp战斗回合数 */
	private int maxPvpBattleRound = 50;
	
	/** 私聊开始符 */
	private String privateChatPrfix;
	/** 世界聊天间隔,单位分钟 */
	private int worldChatInterval;
	/** 私人聊天间隔,单位分钟 */
	private int privateChatInterval;
	/** 军团聊天间隔,单位分钟 */
	private int guildChatInterval;
	/** 组队聊天间隔,单位分钟 */
	private int teamChatInterval;
	/** 附近聊天间隔,单位分钟 */
	private int nearChatInterval;
	/** 世界聊天内容长度限制 */
	private int worldChatLength;
	/** 私人聊天内容长度限制 */
	private int privateChatLength;
	/** 军团聊天内容长度限制 */
	private int guildChatLength;
	
	/** 组队聊天内容长度限制 */
	private int teamChatLength;
	/** 附近聊天内容长度限制 */
	private int nearChatLength;
	
	/** 好友数量上限 */
	private int friendMax;
	/** 推荐好友的数量 */
	private int friendRecommendNum;
	/** 好友奖励次数上限 */
	private int friendRewardMax;
	/** 好友单次奖励体力值 */
	private int friendRewardEnergy;
	/** 好友申请显示数量上限 */
	private int friendApplyMaxNum;
	/** 最近联系人上限 */
	private int maxLatestFriendNum;
	/** 好友战斗提示信息上限 */
	private int maxFriendBattleInfoSize;
	/** 每天自己送出的好友体力次数，不能超过100 */
	private int maxSendEnergyTimes;
	/** 每天好友送给自己的好友体力次数，不能超过100 */
	private int maxBeSendedEnergyTimes;
	
	/** 免费次数 */
	private int turntableFreeTime;
	/** 显示玩家抽取奖品的数量 */
	private int turntableRewardShowNum;
	/** 抽奖消耗勇者之心的数量 */
	private int turntableCost;
	
	/** 每日常规收税次数 */
	private int levyTime;
	/** 收集魔法石奖励货币类型 */
	private int collectRewardType;
	/** 收集魔法石奖励基数 */
	private int collectRewardBaseNum;
	/** 完成收集魔法石任务税收加成比例 */
	private int leveExtraRate;
	/** 税收加成上限 */
	private int maxLeveExtraRate;
	/** 免费收集次数 */
	private int freeCollectNum;
	/** 每日押注次数 */
	private int levyBetTime;
	/** 胜利税收加成 */
	private int winLevyExtraRate;
	
	/** 普通训练每日训练时长限制 */
	private int normalTrainingMaxTime;
	/** vip训练魔晶消费限制 */
	private int vipTraingMaxCrystalConsume;
	
	/** 每日问答的题数 */
	private int dailyQuestionCount;
	/** 每日赠送的祈福次数 */
	private int dailyHandselBlessNum;
	/** 赠送的祈福次数累积最大值 */
	private int maxHandselBlessNum;
	/** 使用祈福时的收益倍率 */
	private float blessRevenueRate;
	/** 一键答题每题消耗魔晶 */
	private int onekeyAnswerSingleCost;
	/** 一键答题vip开启等级 */
	private int onekyAnswerVipLevel;
	
	/** 角色等级排行榜入榜数量 */
	private int levelRankNum;
	
	/** 角色军衔排行榜入榜数量 */
	private int titleRankNum;
	
	/** 体力值上限 */
	private int maxEnergy;
	/** 体力值恢复时间间隔（分钟） */
	private int energyRecoverInterval;
	/** 单次自动恢复的体力值 */
	private int energyRecoverNum;
	/** 单次手动恢复的体力值 */
	private int energyHandRecoverNum;
	/** 每日手动恢复体力次数 */
	private int energyHandRecoverTimes;
	/** 每日自动恢复体力值 */
	private int energyDailyRecoverNum;
	
	/** 有报酬的协助冥想次数 */
	private int hasRewardMeditationAssist;
	/** 计算冥想收益的单位时长（秒） */
	private int meditationTimeUnit;
	
	/** 角色名称的最大长度 */
	private int maxRoleNameSize = 5;
	/** 角色名称的最小长度 */
	private int minRoleNameSize = 2;
	
	/** 最大免费开矿坑次数 */
	private int maxFreeMineNum=8;
	/** 战斗失败或放弃战斗默认开启的矿坑类型 */
	private int defaultMineFieldType=1;
	/** 每日提交问题反馈次数上限 */
	private int submitGmQuestionNum=10;
	/** 世界聊天所需钻石 */
	private int worldChatNeedDiamond = 2;
	/** 每日最大发送邮件数 */
	private int maxSendMailNum = 100;
	/** 收件箱邮件数上限 */
	private int maxRecievedMailNum=50;
	
	/** 防沉迷开关 */
	private boolean antiIndulgeSwitch = false;
	/** 同步收益比率的间隔时间 */
	private int updateRevenueRateTimeSpan = 5*60*1000;
	/** 装备制作的时候降级的数量 */
	private int equipMakeDownLevel = 3;
	/** 装备洗炼每日免费次数 */
	private int equipForgeFreeTimes;
	/** 试练塔每日免费刷新次数 */
	private int refineRefreshTimes;
	
	//匹配战相关
	private int matchBattleBeginWaitTime = 120;
	private int matchBattleMatchTurnTime = 60;
	private int matchBattleMatchQueneSize = 12;
	private int matchBattleRankSize = 10;
	private int matchBattleRankShowSize = 3;
	
	//勇者之路相关
	private int warriorChallengeStrangerRewardNum = 1;
	private int warriorChallengeFriendRewardNum = 1;
	private int warriorChallengeNpcRewardNum = 1;
	private int warriorQuestCdTime = 5*60;
	private int warriorChallengeLevelDiff = 1;
	private int warriorRefreshOpponentCd = 5;
	private int maxWarriorChallengeRewardTimes;
	
	//战斗引导结束关卡
	private int battleGuideFinishStageId = 4;
	
	// 神魄相关
	private int upgradeCostCoin = 500;
	private int upgradeCostCrystal = 2;
	private int amendSuccessRate = 500;
	
	// 战俘营相关
	/** 默认抓捕房间数 */
	private int defaultArrestRoomNum;
	/** 免费抓捕次数 */
	private int freeArrestNum;
	/** 手下败将显示个数 */
	private int looserNumLimit;
	/** 仇人个数 */
	private int enemyNumLimit;
	/** 抓捕等级差 */
	private int arrestLevelDiff;
	/** 营救等级差 */
	private int rescueLevelDiff;
	/** 互动次数 */
	private int interactNumLimit;
	/** 反抗次数 */
	private int revoltNumLimit;
	/** 解救团员次数 */
	private int rescueNumLimit;
	/** 求救次数 */
	private int forHelpNumLimit;
	/** 最大奴隶数 */
	private int maxSlaveNum;
	/** 剥削奴隶时间 */
	private int holdSlaveTimeLimit;
	/** 互动冷却时间 */
	private int interactTimeLimit;
	/** 互动经验双倍概率 */
	private int interactDoubleExperience;
	/** 求救列表人数限制 */
	private int forHelpMemberNumLimit;
	/** 提取1小时经验消耗魔晶 */
	private int costCrystalPerHourExp;
	// 精灵相关
	/** 单个精灵格子消耗魔晶数量 */
	private int perSpriteBagCellCostCrystal;
	/** 换系消耗魔晶数量 */
	private int changeSkillDevelopCostCrystal;
	// 随机加入阵营的金币奖励
	private int randomSelectFactionRewardCoin;
	//战神之巅相关
	/** 刷新房间消费魔晶数量 */
	private int refreshMarsRoomCost;
	/** 解锁房间消费魔晶数量 */
	private int unlockMarsRoomCost;
	/** 每日默认击杀次数 */
	private int dayKillNum;
	/** 每日默认加倍次数 */
	private int multipleNum;
	/** 每日接受挑战奖励次数 */
	private int dayReceiveRewardNum;
	/** 每杀戮值对应威望数 */
	private int killValueToPrestigeRate;
	/** 杀戮值对阵营贡献系数 */
	private float killValueToFactionRate;
	// 日常任务相关
	/** 免费接受日常任务次数 */
	private int freeQuestNum;
	/** 魔晶刷新消耗 */
	private int refreshDailyQuestsCost;
	// 嗜血神殿相关
	/** 嗜血神殿开启所需军衔等级 */
	private int bloodTempleOpenTitle;
	
	public int getTurntableFreeTime() {
		return turntableFreeTime;
	}

	public void setTurntableFreeTime(int turntableFreeTime) {
		this.turntableFreeTime = turntableFreeTime;
	}

	public int getTurntableRewardShowNum() {
		return turntableRewardShowNum;
	}

	public void setTurntableRewardShowNum(int turntableRewardShowNum) {
		this.turntableRewardShowNum = turntableRewardShowNum;
	}

	public int getFriendMax() {
		return friendMax;
	}

	public void setFriendMax(int friendMax) {
		this.friendMax = friendMax;
	}

	public int getFriendRecommendNum() {
		return friendRecommendNum;
	}

	public void setFriendRecommendNum(int friendRecommendNum) {
		this.friendRecommendNum = friendRecommendNum;
	}

	public int getFriendRewardMax() {
		return friendRewardMax;
	}

	public void setFriendRewardMax(int friendRewardMax) {
		this.friendRewardMax = friendRewardMax;
	}

	public static String getOptionOk() {
		return OPTION_OK;
	}

	public static String getOptionCancel() {
		return OPTION_CANCEL;
	}

	@Override
	public boolean getIsDebug() {
		return false;
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public void validate() {
		
	}

	public int getDeductHpWhenMoveFailed() {
		return deductHpWhenMoveFailed;
	}

	public void setDeductHpWhenMoveFailed(int deductHpWhenMoveFailed) {
		this.deductHpWhenMoveFailed = deductHpWhenMoveFailed;
	}

	public int getLevelDifferFactor() {
		return levelDifferFactor;
	}

	public void setLevelDifferFactor(int levelDifferFactor) {
		this.levelDifferFactor = levelDifferFactor;
	}

	public int getAttackFactor() {
		return attackFactor;
	}

	public void setAttackFactor(int attackFactor) {
		this.attackFactor = attackFactor;
	}

	public int getDefenseFactor() {
		return defenseFactor;
	}

	public void setDefenseFactor(int defenseFactor) {
		this.defenseFactor = defenseFactor;
	}

	public float getBaseCritFactor() {
		return baseCritFactor;
	}

	public void setBaseCritFactor(float baseCritFactor) {
		this.baseCritFactor = baseCritFactor;
	}

	public float getBaseParryFactor() {
		return baseParryFactor;
	}

	public void setBaseParryFactor(float baseParryFactor) {
		this.baseParryFactor = baseParryFactor;
	}

	public float getBaseHit() {
		return baseHit;
	}

	public void setBaseHit(float baseHit) {
		this.baseHit = baseHit;
	}

	public String getPrivateChatPrfix() {
		return privateChatPrfix;
	}

	public void setPrivateChatPrfix(String privateChatPrfix) {
		this.privateChatPrfix = privateChatPrfix;
	}

	public int getWorldChatInterval() {
		return worldChatInterval;
	}

	public void setWorldChatInterval(int worldChatInterval) {
		this.worldChatInterval = worldChatInterval;
	}

	public int getPrivateChatInterval() {
		return privateChatInterval;
	}

	public void setPrivateChatInterval(int privateChatInterval) {
		this.privateChatInterval = privateChatInterval;
	}

	public int getGuildChatInterval() {
		return guildChatInterval;
	}

	public void setGuildChatInterval(int guildChatInterval) {
		this.guildChatInterval = guildChatInterval;
	}

	public int getTeamChatInterval() {
		return teamChatInterval;
	}

	public void setTeamChatInterval(int teamChatInterval) {
		this.teamChatInterval = teamChatInterval;
	}

	public int getNearChatInterval() {
		return nearChatInterval;
	}

	public void setNearChatInterval(int nearChatInterval) {
		this.nearChatInterval = nearChatInterval;
	}

	public int getWorldChatLength() {
		return worldChatLength;
	}

	public void setWorldChatLength(int worldChatLength) {
		this.worldChatLength = worldChatLength;
	}

	public int getPrivateChatLength() {
		return privateChatLength;
	}

	public void setPrivateChatLength(int privateChatLength) {
		this.privateChatLength = privateChatLength;
	}

	public int getGuildChatLength() {
		return guildChatLength;
	}

	public void setGuildChatLength(int guildChatLength) {
		this.guildChatLength = guildChatLength;
	}

	public int getTeamChatLength() {
		return teamChatLength;
	}

	public void setTeamChatLength(int teamChatLength) {
		this.teamChatLength = teamChatLength;
	}

	public int getNearChatLength() {
		return nearChatLength;
	}

	public void setNearChatLength(int nearChatLength) {
		this.nearChatLength = nearChatLength;
	}
	public int getLevyTime() {
		return levyTime;
	}

	public void setLevyTime(int levyTime) {
		this.levyTime = levyTime;
	}

	public int getCollectRewardType() {
		return collectRewardType;
	}

	public void setCollectRewardType(int collectRewardType) {
		this.collectRewardType = collectRewardType;
	}

	public int getCollectRewardBaseNum() {
		return collectRewardBaseNum;
	}

	public void setCollectRewardBaseNum(int collectRewardBaseNum) {
		this.collectRewardBaseNum = collectRewardBaseNum;
	}

	public int getLeveExtraRate() {
		return leveExtraRate;
	}

	public void setLeveExtraRate(int leveExtraRate) {
		this.leveExtraRate = leveExtraRate;
	}

	public int getMaxLeveExtraRate() {
		return maxLeveExtraRate;
	}

	public void setMaxLeveExtraRate(int maxLeveExtraRate) {
		this.maxLeveExtraRate = maxLeveExtraRate;
	}

	public int getFreeCollectNum() {
		return freeCollectNum;
	}

	public void setFreeCollectNum(int freeCollectNum) {
		this.freeCollectNum = freeCollectNum;
	}

	public int getLevyBetTime() {
		return levyBetTime;
	}

	public void setLevyBetTime(int levyBetTime) {
		this.levyBetTime = levyBetTime;
	}

	public int getWinLevyExtraRate() {
		return winLevyExtraRate;
	}

	public void setWinLevyExtraRate(int winLevyExtraRate) {
		this.winLevyExtraRate = winLevyExtraRate;
	}

	public int getNormalTrainingMaxTime() {
		return normalTrainingMaxTime;
	}

	public void setNormalTrainingMaxTime(int normalTrainingMaxTime) {
		this.normalTrainingMaxTime = normalTrainingMaxTime;
	}

	public int getVipTraingMaxCrystalConsume() {
		return vipTraingMaxCrystalConsume;
	}

	public void setVipTraingMaxCrystalConsume(int vipTraingMaxCrystalConsume) {
		this.vipTraingMaxCrystalConsume = vipTraingMaxCrystalConsume;
	}

	public int getDailyQuestionCount() {
		return dailyQuestionCount;
	}

	public void setDailyQuestionCount(int dailyQuestionCount) {
		this.dailyQuestionCount = dailyQuestionCount;
	}

	public int getDailyHandselBlessNum() {
		return dailyHandselBlessNum;
	}

	public void setDailyHandselBlessNum(int dailyHandselBlessNum) {
		this.dailyHandselBlessNum = dailyHandselBlessNum;
	}

	public int getMaxHandselBlessNum() {
		return maxHandselBlessNum;
	}

	public void setMaxHandselBlessNum(int maxHandselBlessNum) {
		this.maxHandselBlessNum = maxHandselBlessNum;
	}

	public float getBlessRevenueRate() {
		return blessRevenueRate;
	}

	public void setBlessRevenueRate(float blessRevenueRate) {
		this.blessRevenueRate = blessRevenueRate;
	}

	public int getOnekeyAnswerSingleCost() {
		return onekeyAnswerSingleCost;
	}

	public void setOnekeyAnswerSingleCost(int onekeyAnswerSingleCost) {
		this.onekeyAnswerSingleCost = onekeyAnswerSingleCost;
	}

	public int getOnekyAnswerVipLevel() {
		return onekyAnswerVipLevel;
	}

	public void setOnekyAnswerVipLevel(int onekyAnswerVipLevel) {
		this.onekyAnswerVipLevel = onekyAnswerVipLevel;
	}

	public int getLevelRankNum() {
		return levelRankNum;
	}

	public void setLevelRankNum(int levelRankNum) {
		this.levelRankNum = levelRankNum;
	}

	public int getTitleRankNum() {
		return titleRankNum;
	}

	public void setTitleRankNum(int titleRankNum) {
		this.titleRankNum = titleRankNum;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public int getEnergyRecoverInterval() {
		return energyRecoverInterval;
	}

	public void setEnergyRecoverInterval(int energyRecoverInterval) {
		this.energyRecoverInterval = energyRecoverInterval;
	}

	public int getEnergyRecoverNum() {
		return energyRecoverNum;
	}

	public void setEnergyRecoverNum(int energyRecoverNum) {
		this.energyRecoverNum = energyRecoverNum;
	}

	public int getEnergyHandRecoverNum() {
		return energyHandRecoverNum;
	}

	public void setEnergyHandRecoverNum(int energyHandRecoverNum) {
		this.energyHandRecoverNum = energyHandRecoverNum;
	}

	public int getEnergyHandRecoverTimes() {
		return energyHandRecoverTimes;
	}

	public void setEnergyHandRecoverTimes(int energyHandRecoverTimes) {
		this.energyHandRecoverTimes = energyHandRecoverTimes;
	}

	public int getEnergyDailyRecoverNum() {
		return energyDailyRecoverNum;
	}

	public void setEnergyDailyRecoverNum(int energyDailyRecoverNum) {
		this.energyDailyRecoverNum = energyDailyRecoverNum;
	}

	public int getHasRewardMeditationAssist() {
		return hasRewardMeditationAssist;
	}

	public void setHasRewardMeditationAssist(int hasRewardMeditationAssist) {
		this.hasRewardMeditationAssist = hasRewardMeditationAssist;
	}

	public int getMeditationTimeUnit() {
		return meditationTimeUnit;
	}

	public void setMeditationTimeUnit(int meditationTimeUnit) {
		if(meditationTimeUnit<=0){
			throw new IllegalArgumentException("计算冥想值的单位时间必须大于0");
		}
		this.meditationTimeUnit = meditationTimeUnit;
	}

	public int getMaxLevelDiffer() {
		return maxLevelDiffer;
	}

	public void setMaxLevelDiffer(int maxLevelDiffer) {
		this.maxLevelDiffer = maxLevelDiffer;
	}

	public int getMinLevelDiffer() {
		return minLevelDiffer;
	}

	public void setMinLevelDiffer(int minLevelDiffer) {
		this.minLevelDiffer = minLevelDiffer;
	}

	public int getMaxRoleNameSize() {
		return maxRoleNameSize;
	}

	public void setMaxRoleNameSize(int maxRoleNameSize) {
		this.maxRoleNameSize = maxRoleNameSize;
	}

	public int getMaxFreeMineNum() {
		return maxFreeMineNum;
	}

	public void setMaxFreeMineNum(int maxFreeMineNum) {
		this.maxFreeMineNum = maxFreeMineNum;
	}

	public int getDefaultMineFieldType() {
		return defaultMineFieldType;
	}

	public void setDefaultMineFieldType(int defaultMineFieldType) {
		this.defaultMineFieldType = defaultMineFieldType;
	}

	public int getSubmitGmQuestionNum() {
		return submitGmQuestionNum;
	}

	public void setSubmitGmQuestionNum(int submitGmQuestionNum) {
		this.submitGmQuestionNum = submitGmQuestionNum;
	}

	public int getMinRoleNameSize() {
		return minRoleNameSize;
	}

	public void setMinRoleNameSize(int minRoleNameSize) {
		this.minRoleNameSize = minRoleNameSize;
	}

	public int getWorldChatNeedDiamond() {
		return worldChatNeedDiamond;
	}

	public void setWorldChatNeedDiamond(int worldChatNeedDiamond) {
		this.worldChatNeedDiamond = worldChatNeedDiamond;
	}

	public int getMaxSendMailNum() {
		return maxSendMailNum;
	}

	public void setMaxSendMailNum(int maxSendMailNum) {
		this.maxSendMailNum = maxSendMailNum;
	}

	public int getMaxRecievedMailNum() {
		return maxRecievedMailNum;
	}

	public void setMaxRecievedMailNum(int maxRecievedMailNum) {
		this.maxRecievedMailNum = maxRecievedMailNum;
	}

	public int getBlackGemAttackPerAddRate() {
		return blackGemAttackPerAddRate;
	}

	public void setBlackGemAttackPerAddRate(int blackGemAttackPerAddRate) {
		this.blackGemAttackPerAddRate = blackGemAttackPerAddRate;
	}

	public int getWhiteGemRecoverEnergyRate() {
		return whiteGemRecoverEnergyRate;
	}

	public void setWhiteGemRecoverEnergyRate(int whiteGemRecoverEnergyRate) {
		this.whiteGemRecoverEnergyRate = whiteGemRecoverEnergyRate;
	}

	public int getBlackMaxAddRate() {
		return blackMaxAddRate;
	}

	public void setBlackMaxAddRate(int blackMaxAddRate) {
		this.blackMaxAddRate = blackMaxAddRate;
	}

	public int getMaxPveBattleRound() {
		return maxPveBattleRound;
	}

	public void setMaxPveBattleRound(int maxPveBattleRound) {
		this.maxPveBattleRound = maxPveBattleRound;
	}

	public int getMaxPvpBattleRound() {
		return maxPvpBattleRound;
	}

	public void setMaxPvpBattleRound(int maxPvpBattleRound) {
		this.maxPvpBattleRound = maxPvpBattleRound;
	}

	public int getFriendRewardEnergy() {
		return friendRewardEnergy;
	}

	public void setFriendRewardEnergy(int friendRewardEnergy) {
		this.friendRewardEnergy = friendRewardEnergy;
	}

	public int getFriendApplyMaxNum() {
		return friendApplyMaxNum;
	}

	public void setFriendApplyMaxNum(int friendApplyMaxNum) {
		this.friendApplyMaxNum = friendApplyMaxNum;
	}

	public boolean getAntiIndulgeSwitch() {
		return antiIndulgeSwitch;
	}

	public void setAntiIndulgeSwitch(boolean antiIndulgeSwitch) {
		this.antiIndulgeSwitch = antiIndulgeSwitch;
	}

	public int getUpdateRevenueRateTimeSpan() {
		return updateRevenueRateTimeSpan;
	}

	public void setUpdateRevenueRateTimeSpan(int updateRevenueRateTimeSpan) {
		this.updateRevenueRateTimeSpan = updateRevenueRateTimeSpan;
	}

	public int getEquipMakeDownLevel() {
		return equipMakeDownLevel;
	}

	public void setEquipMakeDownLevel(int equipMakeDownLevel) {
		this.equipMakeDownLevel = equipMakeDownLevel;
	}

	public int getEquipForgeFreeTimes() {
		return equipForgeFreeTimes;
	}

	public void setEquipForgeFreeTimes(int equipForgeFreeTimes) {
		this.equipForgeFreeTimes = equipForgeFreeTimes;
	}

	public int getRefineRefreshTimes() {
		return refineRefreshTimes;
	}

	public void setRefineRefreshTimes(int refineRefreshTimes) {
		this.refineRefreshTimes = refineRefreshTimes;
	}

	public int getMatchBattleBeginWaitTime() {
		return matchBattleBeginWaitTime;
	}

	public void setMatchBattleBeginWaitTime(int matchBattleBeginWaitTime) {
		this.matchBattleBeginWaitTime = matchBattleBeginWaitTime;
	}

	public int getMatchBattleMatchTurnTime() {
		return matchBattleMatchTurnTime;
	}

	public void setMatchBattleMatchTurnTime(int matchBattleMatchTurnTime) {
		this.matchBattleMatchTurnTime = matchBattleMatchTurnTime;
	}

	public int getMatchBattleMatchQueneSize() {
		return matchBattleMatchQueneSize;
	}

	public void setMatchBattleMatchQueneSize(int matchBattleMatchQueneSize) {
		this.matchBattleMatchQueneSize = matchBattleMatchQueneSize;
	}

	public int getMatchBattleRankSize() {
		return matchBattleRankSize;
	}

	public void setMatchBattleRankSize(int matchBattleRankSize) {
		this.matchBattleRankSize = matchBattleRankSize;
	}

	public int getMatchBattleRankShowSize() {
		return matchBattleRankShowSize;
	}

	public void setMatchBattleRankShowSize(int matchBattleRankShowSize) {
		this.matchBattleRankShowSize = matchBattleRankShowSize;
	}

	public int getMaxLatestFriendNum() {
		return maxLatestFriendNum;
	}

	public void setMaxLatestFriendNum(int maxLatestFriendNum) {
		this.maxLatestFriendNum = maxLatestFriendNum;
	}

	public int getMaxFriendBattleInfoSize() {
		return maxFriendBattleInfoSize;
	}

	public void setMaxFriendBattleInfoSize(int maxFriendBattleInfoSize) {
		this.maxFriendBattleInfoSize = maxFriendBattleInfoSize;
	}

	public int getMaxSendEnergyTimes() {
		return maxSendEnergyTimes;
	}

	public void setMaxSendEnergyTimes(int maxSendEnergyTimes) {
		this.maxSendEnergyTimes = maxSendEnergyTimes;
	}

	public int getMaxBeSendedEnergyTimes() {
		return maxBeSendedEnergyTimes;
	}

	public void setMaxBeSendedEnergyTimes(int maxBeSendedEnergyTimes) {
		this.maxBeSendedEnergyTimes = maxBeSendedEnergyTimes;
	}

	public int getWarriorChallengeStrangerRewardNum() {
		return warriorChallengeStrangerRewardNum;
	}

	public void setWarriorChallengeStrangerRewardNum(int warriorChallengeStrangerRewardNum) {
		this.warriorChallengeStrangerRewardNum = warriorChallengeStrangerRewardNum;
	}

	public int getWarriorChallengeFriendRewardNum() {
		return warriorChallengeFriendRewardNum;
	}

	public void setWarriorChallengeFriendRewardNum(int warriorChallengeFriendRewardNum) {
		this.warriorChallengeFriendRewardNum = warriorChallengeFriendRewardNum;
	}

	public int getWarriorChallengeNpcRewardNum() {
		return warriorChallengeNpcRewardNum;
	}

	public void setWarriorChallengeNpcRewardNum(int warriorChallengeNpcRewardNum) {
		this.warriorChallengeNpcRewardNum = warriorChallengeNpcRewardNum;
	}

	public int getWarriorQuestCdTime() {
		return warriorQuestCdTime;
	}

	public void setWarriorQuestCdTime(int warriorQuestCdTime) {
		this.warriorQuestCdTime = warriorQuestCdTime;
	}

	public int getWarriorChallengeLevelDiff() {
		return warriorChallengeLevelDiff;
	}

	public void setWarriorChallengeLevelDiff(int warriorChallengeLevelDiff) {
		this.warriorChallengeLevelDiff = warriorChallengeLevelDiff;
	}

	public int getWarriorRefreshOpponentCd() {
		return warriorRefreshOpponentCd;
	}

	public void setWarriorRefreshOpponentCd(int warriorRefreshOpponentCd) {
		this.warriorRefreshOpponentCd = warriorRefreshOpponentCd;
	}

	public int getTurntableCost() {
		return turntableCost;
	}

	public void setTurntableCost(int turntableCost) {
		this.turntableCost = turntableCost;
	}

	public int getBattleGuideFinishStageId() {
		return battleGuideFinishStageId;
	}

	public void setBattleGuideFinishStageId(int battleGuideFinishStageId) {
		this.battleGuideFinishStageId = battleGuideFinishStageId;
	}

	public int getMaxWarriorChallengeRewardTimes() {
		return maxWarriorChallengeRewardTimes;
	}

	public void setMaxWarriorChallengeRewardTimes(int maxWarriorChallengeRewardTimes) {
		this.maxWarriorChallengeRewardTimes = maxWarriorChallengeRewardTimes;
	}

	public int getUpgradeCostCoin() {
		return upgradeCostCoin;
	}

	public void setUpgradeCostCoin(int upgradeCostCoin) {
		this.upgradeCostCoin = upgradeCostCoin;
	}

	public int getUpgradeCostCrystal() {
		return upgradeCostCrystal;
	}

	public void setUpgradeCostCrystal(int upgradeCostCrystal) {
		this.upgradeCostCrystal = upgradeCostCrystal;
	}

	public int getAmendSuccessRate() {
		return amendSuccessRate;
	}

	public void setAmendSuccessRate(int amendSuccessRate) {
		this.amendSuccessRate = amendSuccessRate;
	}

	public int getDefaultArrestRoomNum() {
		return defaultArrestRoomNum;
	}

	public int getArrestLevelDiff() {
		return arrestLevelDiff;
	}

	public void setArrestLevelDiff(int arrestLevelDiff) {
		this.arrestLevelDiff = arrestLevelDiff;
	}

	public int getRescueLevelDiff() {
		return rescueLevelDiff;
	}

	public void setRescueLevelDiff(int rescueLevelDiff) {
		this.rescueLevelDiff = rescueLevelDiff;
	}

	public void setDefaultArrestRoomNum(int defaultArrestRoomNum) {
		this.defaultArrestRoomNum = defaultArrestRoomNum;
	}

	public int getFreeArrestNum() {
		return freeArrestNum;
	}

	public void setFreeArrestNum(int freeArrestNum) {
		this.freeArrestNum = freeArrestNum;
	}

	public int getEnemyNumLimit() {
		return enemyNumLimit;
	}

	public void setEnemyNumLimit(int enemyNumLimit) {
		this.enemyNumLimit = enemyNumLimit;
	}

	public int getLooserNumLimit() {
		return looserNumLimit;
	}

	public void setLooserNumLimit(int looserNumLimit) {
		this.looserNumLimit = looserNumLimit;
	}

	public int getInteractNumLimit() {
		return interactNumLimit;
	}

	public void setInteractNumLimit(int interactNumLimit) {
		this.interactNumLimit = interactNumLimit;
	}

	public int getRevoltNumLimit() {
		return revoltNumLimit;
	}

	public void setRevoltNumLimit(int revoltNumLimit) {
		this.revoltNumLimit = revoltNumLimit;
	}

	public int getRescueNumLimit() {
		return rescueNumLimit;
	}

	public void setRescueNumLimit(int rescueNumLimit) {
		this.rescueNumLimit = rescueNumLimit;
	}

	public int getForHelpNumLimit() {
		return forHelpNumLimit;
	}

	public void setForHelpNumLimit(int forHelpNumLimit) {
		this.forHelpNumLimit = forHelpNumLimit;
	}

	public int getMaxSlaveNum() {
		return maxSlaveNum;
	}

	public void setMaxSlaveNum(int maxSlaveNum) {
		this.maxSlaveNum = maxSlaveNum;
	}

	public int getHoldSlaveTimeLimit() {
		return holdSlaveTimeLimit;
	}

	public void setHoldSlaveTimeLimit(int holdSlaveTimeLimit) {
		this.holdSlaveTimeLimit = holdSlaveTimeLimit;
	}

	public int getInteractTimeLimit() {
		return interactTimeLimit;
	}

	public void setInteractTimeLimit(int interactTimeLimit) {
		this.interactTimeLimit = interactTimeLimit;
	}

	public int getInteractDoubleExperience() {
		return interactDoubleExperience;
	}

	public void setInteractDoubleExperience(int interactDoubleExperience) {
		this.interactDoubleExperience = interactDoubleExperience;
	}

	public int getForHelpMemberNumLimit() {
		return forHelpMemberNumLimit;
	}

	public void setForHelpMemberNumLimit(int forHelpMemberNumLimit) {
		this.forHelpMemberNumLimit = forHelpMemberNumLimit;
	}

	public int getCostCrystalPerHourExp() {
		return costCrystalPerHourExp;
	}

	public void setCostCrystalPerHourExp(int costCrystalPerHourExp) {
		this.costCrystalPerHourExp = costCrystalPerHourExp;
	}
	
	public int getPerSpriteBagCellCostCrystal() {
		return perSpriteBagCellCostCrystal;
	}

	public void setPerSpriteBagCellCostCrystal(int perSpriteBagCellCostCrystal) {
		this.perSpriteBagCellCostCrystal = perSpriteBagCellCostCrystal;
	}

	public int getChangeSkillDevelopCostCrystal() {
		return changeSkillDevelopCostCrystal;
	}

	public void setChangeSkillDevelopCostCrystal(
			int changeSkillDevelopCostCrystal) {
		this.changeSkillDevelopCostCrystal = changeSkillDevelopCostCrystal;
	}

	public int getRandomSelectFactionRewardCoin() {
		return randomSelectFactionRewardCoin;
	}

	public void setRandomSelectFactionRewardCoin(
			int randomSelectFactionRewardCoin) {
		this.randomSelectFactionRewardCoin = randomSelectFactionRewardCoin;
	}

	public int getRefreshMarsRoomCost() {
		return refreshMarsRoomCost;
	}

	public void setRefreshMarsRoomCost(int refreshMarsRoomCost) {
		this.refreshMarsRoomCost = refreshMarsRoomCost;
	}

	public int getUnlockMarsRoomCost() {
		return unlockMarsRoomCost;
	}

	public void setUnlockMarsRoomCost(int unlockMarsRoomCost) {
		this.unlockMarsRoomCost = unlockMarsRoomCost;
	}

	public int getDayKillNum() {
		return dayKillNum;
	}

	public void setDayKillNum(int dayKillNum) {
		this.dayKillNum = dayKillNum;
	}

	public int getMultipleNum() {
		return multipleNum;
	}

	public void setMultipleNum(int multipleNum) {
		this.multipleNum = multipleNum;
	}

	public int getDayReceiveRewardNum() {
		return dayReceiveRewardNum;
	}

	public void setDayReceiveRewardNum(int dayReceiveRewardNum) {
		this.dayReceiveRewardNum = dayReceiveRewardNum;
	}

	public int getKillValueToPrestigeRate() {
		return killValueToPrestigeRate;
	}

	public void setKillValueToPrestigeRate(int killValueToPrestigeRate) {
		this.killValueToPrestigeRate = killValueToPrestigeRate;
	}

	public float getKillValueToFactionRate() {
		return killValueToFactionRate;
	}

	public void setKillValueToFactionRate(float killValueToFactionRate) {
		this.killValueToFactionRate = killValueToFactionRate;
	}

	public int getFreeQuestNum() {
		return freeQuestNum;
	}

	public void setFreeQuestNum(int freeQuestNum) {
		this.freeQuestNum = freeQuestNum;
	}

	public int getRefreshDailyQuestsCost() {
		return refreshDailyQuestsCost;
	}

	public void setRefreshDailyQuestsCost(int refreshDailyQuestsCost) {
		this.refreshDailyQuestsCost = refreshDailyQuestsCost;
	}

	public int getBloodTempleOpenTitle() {
		return bloodTempleOpenTitle;
	}

	public void setBloodTempleOpenTitle(int bloodTempleOpenTitle) {
		this.bloodTempleOpenTitle = bloodTempleOpenTitle;
	}
	
}
