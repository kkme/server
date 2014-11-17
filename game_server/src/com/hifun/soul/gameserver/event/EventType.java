package com.hifun.soul.gameserver.event;

/**
 * 事件类型;
 * 
 * @author crazyjohn
 * 
 */
public enum EventType {
	/** 杀死指定的怪物 */
	MONSTER_DEAD_EVENT,
	/** 点击好友进行迷你战斗 */
	CLICK_FRIEND_MINI_BATTLE_EVENT,
	/** 收集物品事件 */
	COLLECT_ITEM_EVENT,
	/** 使用物品事件 */
	USE_ITEM_EVENT,
	/** 玩家升级事件 */
	LEVEL_UP_EVENT,
	/**玩家升衔事件 */
	TITLE_UP_EVENT,
	/** 战斗胜利事件 */
	BATTLE_WIN_EVENT,
	/** 角色科技升级事件 */
	TECH_UPGRADE_EVENT,
	/** 抽奖事件 */
	LOTTERY_EVENT,
	/** 采矿事件 */
	MINE_EVENT,
	/** 税收事件 */
	REVENUE_EVENT,
	/** 答题事件 */
	ANSWER_EVENT,
	/** 训练事件 */
	TRAINING_EVENT,
	/** 装备洗练事件 */
	EQUIP_REFINE_EVENT,
	/** 装备强化事件 */
	EQUIP_UPGRADE_EVENT,
	/** 占星事件 */
	HOROSCOPE_EVENT,
	/** 精英副本战斗胜利事件 */
	ELITE_STAGE_BATTLE_EVENT,
	/** VIP等级改变事件 */
	VIP_LEVEL_CHANGE_EVENT,
	/** 角色离线事件 */
	HUMAN_OFF_LINE_EVENT,
	/** 充值事件 */
	RECHARGE_EVENT,
	/** 试炼挑战事件 */
	REFINE_CHALLENGE_EVENT,
	/** 城堡盗贼战斗事件 */
	MAIN_CITY_MONSTER_EVENT,
	/** 属性培养 */
	PROPERTY_FOSTER_EVENT,
	/** 巫术套现 */
	CRYSTAL_EXCHANGE_EVENT,
	/** 天赋升级 */
	GIFT_UPGRADE_EVENT,
	/** 竞技场挑战 */
	ARENA_CHALLENGE_EVENT,
	/** 酒馆对酒 */
	SPRITE_PUB_EVENT,
	/** 跑商押运 */
	ESCORT_EVENT,
	/** 猜大小 */
	MAIN_CITY_BET_EVENT,
	/** 战神之巅击杀 */
	MARS_KILL_EVENT,
	/** 战俘营挑战 */
	PRISON_BATTLE_EVENT,
	/** 角斗场挑战 */
	ABATTOIR_CHALLENGE_EVENT,
	/** 嗜血神殿挑战 */
	BLOOD_TEMPLE_CHALLENGE_EVENT,
	/** 装备位升级 */
	EQUIP_BIT_UPGRADE_EVENT,
	/** 点亮星图 */
	STAR_MAP_ACTIVATE_EVENT,
	/** 主城通灵 */
	MAIN_CITY_COLLECT_STONE_EVENT,
	/** 精灵升级 */
	SPRITE_UPGRADE_EVENT
	;
}
