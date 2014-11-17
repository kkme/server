package com.hifun.soul.gameserver.function;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;


/**
 * 游戏功能类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum GameFuncType implements IndexedEnum {
	/** 奇遇答题 */
	@ClientEnumComment(comment = "奇遇答题")
	ANSWER_QUESTION(101),
	/** 竞技场排行 */
	@ClientEnumComment(comment = "竞技场排行")
	ARENA_RANK(102),
	/** 精英副本 */
	@ClientEnumComment(comment = "精英副本")
	ELITE(103),
	/** 占星屋建筑 */
	@ClientEnumComment(comment = "占星屋建筑")
	GAMBLE(104),
	/** 一键占星 */
	@ClientEnumComment(comment = "一键占星")
	AUTO_GAMBLE(105),
	/** 主城建筑 */
	@ClientEnumComment(comment = "主城建筑 ")
	MAIN_CITY(106),
	/** 矿场建筑 */
	@ClientEnumComment(comment = "矿场建筑")
	MINE(107),
	/** 魔法塔建筑 */
	@ClientEnumComment(comment = "魔法塔建筑")
	SKILL(108),
	/** 竞技场建筑 */
	@ClientEnumComment(comment = "竞技场建筑")
	ARENA(109),
	/** 训练场建筑 */
	@ClientEnumComment(comment = "训练场建筑")
	TRAINING(110),
	/** 科技屋建筑 */
	@ClientEnumComment(comment = "科技屋建筑")
	TECHNOLOGY(111),
	/** 铁匠铺建筑 */
	@ClientEnumComment(comment = "铁匠铺建筑")
	SMITHY(112),
	/** 商店 */
	@ClientEnumComment(comment = "商店")
	SHOP(113),
	/** 装备强化 */
	@ClientEnumComment(comment = "装备强化")
	EQUIP_UPGRADE(115),
	/** 宝石镶嵌*/
	@ClientEnumComment(comment = "宝石镶嵌")
	GEM_EMBED(116),
	/** 装备制作 */
	@ClientEnumComment(comment = "装备制作")
	EQUIP_MAKE(117),
	/** boss战 */
	@ClientEnumComment(comment = "boss战")
	BOSS_WAR(118),
	/** 魔晶兑换 */
	@ClientEnumComment(comment = "魔晶兑换")
	CRYSTAL_EXCHANGE(119),
	/** 神秘商店 */
	@ClientEnumComment(comment = "神秘商店")
	SPECIAL_SHOP(120),
	/** 荣誉商店 */
	@ClientEnumComment(comment = "荣誉商店")
	HONOUR_SHOP(121),
	/** 消除所有矿坑cd */
	@ClientEnumComment(comment = "消除所有矿坑cd")
	REMOVE_ALL_MINE_CD(122),
	/** 收获所有矿坑 */
	@ClientEnumComment(comment = "收获所有矿坑")
	ALL_MINE_HARVEST(123),
	/** 装备洗练 */
	@ClientEnumComment(comment= "装备洗炼")
	STONE_FORGE(124),
	/** 洗练锁定 */
	@ClientEnumComment(comment= "洗练锁定")
	LOCK_FORGE(125),
	/** 属性培养 */
	@ClientEnumComment(comment= "属性培养")
	FOSTER(126),
	/** 天赋系统 */
	@ClientEnumComment(comment= "天赋系统")
	GIFT(127),
	/** 试练塔*/
	@ClientEnumComment(comment= "试炼塔")
	REFINE(128),
	/** 自动攻打试炼塔*/
	@ClientEnumComment(comment= "自动攻打试炼塔")
	AUTO_REFINE(129),
	/** 自动攻打所在的试炼地图所有关卡*/
	@ClientEnumComment(comment= "自动攻打所在的试炼地图所有关卡")
	AUTO_ALL_REFINE(130),
	/** 加速冥想*/
	@ClientEnumComment(comment= "加速冥想")
	MUSE_MODE_TWO(131),
	/** 急速冥想*/
	@ClientEnumComment(comment= "急速冥想")
	MUSE_MODE_THREE(132),
	/** 极限冥想*/
	@ClientEnumComment(comment= "极限冥想")
	MUSE_MODE_FOUR(133),
	/** 冥想两小时*/
	@ClientEnumComment(comment= "冥想两小时")
	MUSE_TIME_TWO(134),
	/** 冥想四小时*/
	@ClientEnumComment(comment= "冥想四小时")
	MUSE_TIME_THREE(135),
	/** 冥想八小时*/
	@ClientEnumComment(comment= "冥想八小时")
	MUSE_TIME_FOUR(136),
	/** 匹配战活动*/
	@ClientEnumComment(comment= "匹配战活动")
	MATCH_BATTLE(137),
	/** 小助手*/
	@ClientEnumComment(comment= "小助手")
	FUNCTION_HELPER(138),
	/** 勇士之路*/
	@ClientEnumComment(comment= "勇士之路")
	WARRIOR_WAY(139),
	/** 抽奖*/
	@ClientEnumComment(comment= "抽奖")
	LOTTERY(140),
	/** 军衔*/
	@ClientEnumComment(comment= "军衔")
	TITLE(141),
	/** 军团 */
	@ClientEnumComment(comment= "军团")
	LEGION(142),
	/** 神魄 */
	@ClientEnumComment(comment= "神魄")
	GODSOUL(144),
	/** 战俘营 */
	@ClientEnumComment(comment= "战俘营")
	PRISON(145),
	/** 战神之巅 */
	@ClientEnumComment(comment= "战神之巅")
	MARS(146),
	/** 战神之巅刷新房间 */
	@ClientEnumComment(comment= "战神之巅刷新房间")
	MARS_REFRESH_ROOM(147),
	/** 战神之巅解锁房间 */
	@ClientEnumComment(comment= "战神之巅解锁房间")
	MARS_UNLOCK_ROOM(148),
	@ClientEnumComment(comment= "角斗场")
	ABATTOIR(149),
	@ClientEnumComment(comment= "嗜血神殿")
	BLOOD_TEMPLE(150),
	@ClientEnumComment(comment= "精灵")
	SPRITE(151),
	@ClientEnumComment(comment= "精灵酒馆")
	SPRITE_PUB(152),
	@ClientEnumComment(comment= "星图")
	STAR_MAP(153),
	@ClientEnumComment(comment= "阵营")
	FACTION(154),
	@ClientEnumComment(comment= "阵营战")
	FACTION_WAR(155),
	@ClientEnumComment(comment= "宝石合成")
	GEM_COMPOUND(156),
	@ClientEnumComment(comment= "军团boss战")
	LEGION_BOSS_WAR(157),
	@ClientEnumComment(comment= "军团矿战")
	LEGION_MINE_WAR(158),
	@ClientEnumComment(comment= "征战")
	STAGE_BATTLE(159),
	@ClientEnumComment(comment= "主线任务")
	MAIN_QUEST(160),
	@ClientEnumComment(comment= "日常任务")
	DAILY_QUEST(161),
	@ClientEnumComment(comment= "充值")
	RECHARGE(162),
	@ClientEnumComment(comment= "连续登陆")
	LOGIN_REWARD(163),
	@ClientEnumComment(comment= "附魔")
	ATTACH_MAGIC(164),
	@ClientEnumComment(comment= "个人目标")
	TARGET(165),
	@ClientEnumComment(comment= "好友")
	FRIEND(166),
	@ClientEnumComment(comment= "跑商")
	ESCORT(167),
	@ClientEnumComment(comment= "预见未来")
	PREDICT(168),
	@ClientEnumComment(comment= "城堡盗贼")
	MAIN_CITY_MONSTER(169),
	@ClientEnumComment(comment= "商城")
	MALL(170),
	@ClientEnumComment(comment= "主城猜大小")
	MAIN_CITY_BET(171),
	@ClientEnumComment(comment= "主城通灵")
	MAIN_CITY_AURA(172),
	@ClientEnumComment(comment= "恢复体力")
	RECOVER_ENERGY(173)
	;
	
	private static final List<GameFuncType> indexes = IndexedEnumUtil.toIndexes(GameFuncType.values());
	
	private int index;
	private GameFuncType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static GameFuncType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
