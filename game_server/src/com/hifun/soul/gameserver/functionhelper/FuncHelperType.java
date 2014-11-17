package com.hifun.soul.gameserver.functionhelper;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

@AutoCreateClientEnumType
public enum FuncHelperType implements IndexedEnum {
	/** 主城税收 */
	@ClientEnumComment(comment = "主城税收")
	MAIN_CITY_LEVY(1),
	/** 巫术套现 */
	@ClientEnumComment(comment = "巫术套现")
	CRYSTAL_EXCHANGE(2),
	/** 关卡 */
	@ClientEnumComment(comment = "关卡")
	STAGE(3),
	/** 精英副本 */
	@ClientEnumComment(comment = "精英副本")
	ELITE(4),
	/** 主线任务 */
	@ClientEnumComment(comment = "主线任务")
	MAIN_QUEST(5),
	/** 日常任务 */
	@ClientEnumComment(comment = "日常任务")
	DAILY_QUEST(6),
	/** 奇遇答题 */
	@ClientEnumComment(comment = "奇遇答题")
	ANSWER_QUESTION(7),
	/** boss战 */
	@ClientEnumComment(comment = "boss战")
	BOSS_WAR(8),
	/** 魔石混战*/
	@ClientEnumComment(comment= "魔石混战")
	MATCH_BATTLE(9),
	/** 竞技场建筑 */
	@ClientEnumComment(comment = "竞技场")
	ARENA(10),
	/** 冥想学院 */
	@ClientEnumComment(comment = "冥想学院")
	MEDITATION(11),
	/** 训练 */
	@ClientEnumComment(comment = "训练")
	TRAINING(12),
	/** 矿场 */
	@ClientEnumComment(comment = "矿场")
	MINE(13),
	/** 荣誉商店 */
	@ClientEnumComment(comment = "荣誉商店")
	HONOUR_SHOP(14),
	/** 试练塔 */
	@ClientEnumComment(comment = "试练塔")
	REFINE(15),
	/** 装备制作 */
	@ClientEnumComment(comment = "装备制作")
	EQUIP_MAKE(16),
	/** 装备强化 */
	@ClientEnumComment(comment = "装备强化")
	EQUIP_UPGRADE(17),
	/** 宝石镶嵌 */
	@ClientEnumComment(comment = "宝石镶嵌")
	GEM_EMBED(18),
	/** 装备洗练 */
	@ClientEnumComment(comment = "装备洗练")
	FORGE(19),
	/** 战争学院 */
	@ClientEnumComment(comment = "战争学院")
	WAR_COLLEGE(20),
	/** 天命神殿 */
	@ClientEnumComment(comment = "天命神殿")
	HOROSCOPE(21),
	/** 魔法塔 */
	@ClientEnumComment(comment = "魔法塔")
	MAGIC_TOWER(22),
	/** 勇者之路 */
	@ClientEnumComment(comment = "勇者之路")
	WARRIOR(23),
	/** 神秘商店 */
	@ClientEnumComment(comment = "神秘商店")
	SPECIAL_SHOP(24),
	/** 培养 */
	@ClientEnumComment(comment = "培养")
	FOSTER(25),
	/** 天赋 */
	@ClientEnumComment(comment = "天赋")
	GIFT(26),
	/** 抽奖 */
	@ClientEnumComment(comment = "抽奖")
	TURNTABLE(27),
	/** 连续登陆 */
	@ClientEnumComment(comment = "连续登陆")
	LOGIN_REWARD(28),
	/** 充值 */
	@ClientEnumComment(comment = "充值")
	RECHARGE(29),
	;

	private int index;
	private FuncHelperType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {		
		return index;
	}

}
