package com.hifun.soul.gameserver.timetask;

import com.hifun.soul.core.enums.IndexedEnum;

public enum TimeTaskType implements IndexedEnum{

	/** 抽奖次数重置定时任务 */
	RESET_TURNTABLE_NUM(1),
	/** 角色等级排行榜数据任务 */
	HUMAN_LEVEL_RANK_REFRESH(2),
	/** 魔晶兑换次数重置定时任务 */
	RESET_CRYSTAL_EXCHANGE_TIME(3),
	/** 训练场重置定时任务 */
	RESET_TRAINING_TIME(4),
	/** 连续登陆奖励定时任务 */
	RESET_LOGIN_REWARD(5),
	/** 刷新活动列表定时任务 */
	RESET_ACTIVITY_LIST(6),
	/** cd重置任务 */
	RESET_CD_TIRED(7),
	/** 好友奖励重置任务 */
	RESET_FRIEND_REWARD(8),
	/** 收获宝石重置任务 */
	RESET_HARVEST_GEM(9),
	/** 税收重置任务 */
	RESET_LEVY(10),
	/** 每日问答重置任务 */
	RESET_DAILY_ANSWER_QUESTION(11),
	/** 每周问答重置任务 */
	RESET_WEEKLY_ANSWER_QUESTION(12),
	/** 购买体力值 */
	RESET_BUY_ENERGY_TIME(13),
	/** 重置竞技场战斗次数 */
	RESET_ARENA_BATTLE_TIME(14),
	/** 重置竞技场排行奖励 */
	RESET_ARENA_RANK_REWARD(15),
	/** 重置协助冥想剩余次数 */
	RESET_ASSIST_MEDITATION_REMAIN_TIME(16),
	/** 重置竞技场购买次数 */
	RESET_ARENA_BUY_TIME(17),
	/** 重置每日任务 */
	RESET_DAILY_QUEST(18),
	/** 重置免费占星次数 */
	RESET_HOROSCOPE_GAMBLE_TIME(19),
	/** 重置矿场相关数据 */
	RESET_MINE_DATA(20),
	/** 刷新神秘商店 */
	REFRESH_SPECIAL_SHOP(21),
	/** 重置提交问题反馈次数 */
	RESET_SUBMIT_GM_QUESTION_TIME(22),
	/** 重置精英副本 */
	RESET_ELITE_STAGE(23),
	/** 重置发送邮件次数 */
	RESET_SEND_MAIL_TIME(24),
	/** 重置神秘商店刷新次数 */
	RESET_SPECIAL_SHOP_REFRESH_TIME(25),
	/** 重置装备洗炼次数 */
	RESET_FREE_EQUIP_FORGE_TIMES(26),
	/** 重置试练塔刷新次数 */
	RESET_REFINE_REFRESH_TIMES(27),
	/** 重置勇士之路的数据 */
	RESET_WARRIOR_DATA(28),
	/** 重置黄钻每日数据 */
	RESET_YELLOW_VIP_DAILY_DATA(29),
	/** 重置军衔每日俸禄领取状态 */
	RESET_TITLE_GET_SALARY(30),
	/** 角色军衔排行榜数据任务 */
	HUMAN_TITLE_RANK_REFRESH(31),
	/** 战俘营重置数据任务 */
	PRISON_RESET_DATA(32),
	/** 角色荣誉排行榜数据任务 */
	HUMAN_HONOR_RANK_REFRESH(33),
	/** 重置角色每日在角斗场数据任务 */
	RESET_HUMAN_ABATTOIR_DATA(34),
	/** 重置角色每日在嗜血神殿数据任务 */
	RESET_HUMAN_BLOOD_TEMPLE_DATA(35),
	/** 每周累计充值领奖数据重置任务 */
	RESET_WEEK_TOTAL_RECHARGE(36),
	/** 每日重置角色战神之巅数据任务 */
	RESET_HUMAN_MARS_DAILY_DATA(37),
	/** 每日重置全局战神之巅数据任务 */
	RESET_GLOBAL_MARS_DAILY_DATA(38),
	/** 全局押运每日数据任务 */
	GLOBAL_ESCORT_DAILY_TASK(39),
	/** 重置角色军团每日数据 */
	RESET_LEGION_DAILY_DATA(40),
	/** 刷新角色军团任务 */
	REFRESH_LEGION_TASK(41),
	/** 重置全局军团每日数据 */
	GLOBAL_LEGION_DAILY_TASK(42),
	/** 重置精灵酒馆每日数据 */
	RESET_HUMAN_SPRITE_PUB_DIALY_DATA(43),
	/** 重置每日手动恢复体力次数 */
	RESET_HUMEN_ENERGY_DAILY_TASK(44),
	/** 每日自动恢复体力值 */
	AUTO_RECOVER_ENERGY_DAILY_TASK(45),
	/** 重置角色每日押运数据 */
	RESET_HUMAN_ESCORT_DAILY_TASK(46)
	;

	private int index;
	
	private TimeTaskType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
