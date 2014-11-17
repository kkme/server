package com.hifun.soul.gameserver.guide;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

public enum GuideType implements IndexedEnum{
	/** 进入游戏 */
	ENTER_GAME(101),
	/** 相关主线任务引导 */
	QUEST_1001(201),
	/** 相关主线任务引导 */
	QUEST_1002(301),
	/** 相关主线任务引导 */
	QUEST_1003(401),
	/** 人物面板打开 */
	OPEN_CHARACTER_PANEL(402),
	/** 相关主线任务引导 */
	QUEST_1004(501),
	/** 打开技能面板 */
	SKILL(502),
	/** 相关主线任务引导 */
	QUEST_1005(601),
	/** 打开答题板子 */
	OPEN_QUESTION_PANEL(602),
	/** 答题 */
	QUESTION(603),
	/** 任务引导 */
	QUEST_1006(701),
	/** 打开战争学院面板 */
	OPEN_TECHNOLOGY_PANEL(702),
	/** 任务引导 */
	QUEST_1007(801),
	/** 相关主线任务引导 */
	QUEST_1008(901),
	/** 天命神殿打开 */
	OPEN_HOROSCOPE_PANEL(902),
	/** 打开矿场 */
	OPEN_MINE_PANEL(903),
	/** 任务完成 */
	QUEST_1009(1001),
	/** 任务完成 */
	QUEST_1010(1101),
	/** 任务完成 */
	QUEST_1011(1201),
	/** 精英副本文件面板打开 */
	OPEN_ELITE_PANEL(1202),
	/** id为1001的精英被杀死 */
	ELITE_20101(1203),
	/** 打造成功 */
	EQUIP_MAKE_SUCCESS(1204),
	/** 任务引导 */
	QUEST_1012(1301),
	/** 试炼副本 */
	OPEN_REFINE_PANEL(1302),
	/** 攻打试炼副本成功 */
	ATTACK_REFINE_STAGE_SUCCESS(1303),
	/** 任务引导 */
	QUEST_1013(1401),
	/** 主城功能面板打开 */
	OPEN_MAIN_CITY(1402),
	/** 任务引导 */
	QUEST_1014(1501),
	/** 竞技场面板打开 */
	OPEN_ARENA_PANEL(1502),
	/** 任务引导 */
	QUEST_1016(1701),
	/** 任务引导 */
	QUEST_1019(2001),
	/** 天赋打开 */
	OPEN_GIFT_PANEL(2002),
	/** 任务引导 */
	QUEST_1020(2101),
	/** 任务引导 */
	QUEST_1025(2601),
	/** 训练打开 */
	OPEN_TRAIN_PANEL(2602),
	/** 任务引导 */
	QUEST_1026(2701),
	/** 培养打开 */
	OPEN_FORSTER_PANEL(2702),
	/** 勇者之路打开 */
	OPEN_WARRIOR_PANEL(2703),
	/** 战斗引导棋盘 */
	BATTLE_GUIDE_CHESSBOARD(2801),
	/** 战斗四连消引导 */
	BATTLE_GUIDE_FOUR_BOMBS(2802),
	/** 战斗三连消(白色宝石)引导 */
	BATTLE_GUIDE_THREE_BOMBS_WHITE(2803),
	/** 战斗三连消(黑色宝石)引导 */
	BATTLE_GUIDE_THREE_BOMBS_BLACK(2804),
	/** 战斗使用技能引导 */
	BATTLE_GUIDE_USE_SKILL(2805),
	/** 战斗技能说明引导 */
	BATTLE_GUIDE_SKILL_INFO(2806),
	/** 战斗三连消(红)引导 */
	BATTLE_GUIDE_THREE_BOMBS_RED(2807),
	/** 战斗三连消(黄)引导 */
	BATTLE_GUIDE_THREE_BOMBS_YELLOW(2808),
	/** 战斗三连消(蓝)引导 */
	BATTLE_GUIDE_THREE_BOMBS_BLUE(2809),
	/** 战斗三连消(绿)引导 */
	BATTLE_GUIDE_THREE_BOMBS_GREEN(2810),
	/** 战斗三连消(紫)引导 */
	BATTLE_GUIDE_THREE_BOMBS_PURPLE(2811),
	/** 第一次关卡战斗胜利 */
	STAGE_10101(205),
	/** 第二关卡战斗胜利 */
	STAGE_10102(302),
	/** 角色面板关闭 */
	CLOSE_CHARACTER_PANEL(303),
	/** 精灵面板打开 */
	OPEN_SPRITE_PANEL(404),
	/** 精灵面板关闭 */
	CLOSE_SPRITE_PANEL(405),
	/** 主城面板关闭 */
	CLOSE_MAIN_CITY(503),
	/** 任务引导 */
	QUEST_1018(1901),
	/** 预见未来 */
	OPEN_PREDICT_PANEL(1902),
	/** 酒馆打开 */
	OPEN_SPRITE_PUB_PANEL(2102),
	/** 任务引导 */
	QUEST_1022(2301),
	/** 任务引导 */
	QUEST_1024(2501),
	/** 军衔面板 */
	OPEN_TITLE_PANEL(2502),
	/** 升衔成功 */
	UPGRADE_TITLE(2503),
	/** 任务引导 */
	QUEST_1028(2901),
	/** 任务引导 */
	QUEST_1032(3301),
	/** 任务引导 */
	QUEST_1036(3701),
	/** 军团面板打开 */
	OPEN_LEGION_PANEL(2004),
	/** 战俘营打开 */
	OPEN_PRISON_PANEL(1503),	
	/** 神魄面板打开 */
	OPEN_GODSOUL_PANEL(1504),
	/** 神魄面板打开 */
	OPEN_ATTACH_MAGIC_PANEL(2704),
	/** 战神之巅 */
	OPEN_MARS_PANEL(3001),
	/** 星图面板 */
	OPEN_STAR_MAP_PANEL(3101),
	/** 跑商界面打开 */
	OPEN_ESCORT_PANEL(3401),
	/** 任务引导 */
	QUEST_1015(4151),
	/** 任务引导 */
	QUEST_1017(4171),
	/** 任务引导 */
	QUEST_1021(4211),
	/** 任务引导 */
	QUEST_1023(4231),
	/** 任务引导 */
	QUEST_1027(4271),
	/** 任务引导 */
	QUEST_1029(4291),
	/** 任务引导 */
	QUEST_1030(4301),
	/** 任务引导 */
	QUEST_1031(4311),
	/** 任务引导 */
	QUEST_1033(4331),
	/** 任务引导 */
	QUEST_1034(4341),
	/** 任务引导 */
	QUEST_1035(4351),
	/** 任务引导 */
	QUEST_1038(4381)
	;
	
	private static final List<GuideType> indexes = IndexedEnumUtil.toIndexes(GuideType.values());
	
	private int index;
	private GuideType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static GuideType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
