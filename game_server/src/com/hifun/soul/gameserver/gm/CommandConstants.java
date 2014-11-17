package com.hifun.soul.gameserver.gm;

/**
 * 
 * gm命令的常量
 * 
 * @author magicstone
 * 
 */
public class CommandConstants {
	/** 给钱的gm命令 (!givemoney 货币类型 货币数量)*/
	public static final String GM_CMD_GIVE_MONEY = "givemoney";
	/** 给物品GM命令 (!giveitem 物品id 物品数量)*/
	public static final String GM_CMD_GIVE_ITEM = "giveitem";
	/** 给科技点命令 (!givetechnologypoint 科技点数量)*/
	public static final String GM_CMD_TECHNOLOGY_POINT = "givetechnologypoint";
	/** 给经验 (!giveexperience 经验值)*/
	public static final String GM_CMD_GIVE_EXPERIENCE = "giveexperience";
	/** 清空背包(!clearbag) */
	public static final String GM_CMD_CLEAR_BAG = "clearbag";
	/** 通关地图(!clearmap) */
	public static final String GM_CMD_PASS_MAP = "passmap";
	/** 重置竞技场战斗次数 */
	public static final String GM_CMD_RESET_ARENA_BATTLE_TIME = "resetarenabattletime";
	/** 重置竞技场排名奖励 */
	public static final String GM_CMD_RESET_ARENA_RANK_REWARD = "resetarenarankreward";
	/** 改变竞技场排名 */
	public static final String GM_CMD_CHANGE_ARENA_RANK = "changearenarank";
	/** 开启boss战 */
	public static final String GM_CMD_START_BOSS_WAR = "startbosswar";
	/** 关闭boss战 */
	public static final String GM_CMD_STOP_BOSS_WAR = "stopbosswar";
	/** 打开boss战面板 */
	public static final String GM_CMD_OPEN_BOSS_WAR_PANEL = "openbosswarpanel";
	/** 选择战斗行动的cmd */
	public static final String GM_CMD_CHOOSE_BATTLE_ACTION = "choosebattleaction";
	/** 给技能点 */
	public static final String GM_CMD_GIVE_SKILL_POINT = "giveskillpoint";
	/** 重置人物属性点 */
	public static final String GM_CMD_RESET_HUMAN_PROPERTIES = "resetHumanProperties";
	/** 清理体力值 */
	public static final String GM_CMD_CLEAR_ENERGY = "clearEnergy";
	/** 给荣誉值 */
	public static final String GM_CMD_GIVE_HONOUR = "givehonour";
	/** 给答题积分 */
	public static final String GM_CMD_GIVE_ANSWER_SCORE = "giveanswerscore";
	/** 给星运 */
	public static final String GM_CMD_GIVE_HOROSCOPE = "givehoroscope";
	/** 通关关卡 */
	public static final String GM_CMD_PASS_STAGE = "passstage";
	/** 给天赋点 */
	public static final String GM_CMD_GIVE_GIFT_POINT = "givegiftpoint";
	/** 给勇者之心 */
	public static final String GM_CMD_GIVE_WARRIOR = "givewarrior";
	/** 给星魂 */
	public static final String GM_CMD_GIVE_STAR_SOUL = "givestarsoul";
	/** 给灵气 */
	public static final String GM_CMD_GIVE_AURA = "giveaura";
	/** 发公告 */
	public static final String GM_CMD_SEND_BULLETIN = "sendbulletin";
	/** 给威望 */
	public static final String GM_CMD_GIVE_PRESTIGE = "giveprestige";
	
}
