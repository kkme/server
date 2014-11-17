package com.hifun.soul.gameserver.battle;

/**
 * 战斗类型;
 * 
 * @author crazyjohn
 * 
 */
public enum BattleType {
	/** 推图 */
	PVE(true),
	/** 竞技场战斗 */
	PVP_ARENA(false),
	/** 好友切磋 */
	PVP_FRIEND(false), 
	/** BOSS战战斗 */
	PVE_BOSS_WAR(true), 
	/** 精英副本 */
	PVE_ELITE_STAGE(true),
	/** 矿场偶遇战 */
	PVE_MINE_ENCOUNTER(true),
	/** 试炼场 */
	PVE_REFINE_STAGE(true),
	/** 匹配战 */
	PVP_MATCH_BATTLE(false),
	/** 勇士之路PVE */
	PVE_WARRIOR_BATTLE(true),
	/** 勇士之路PVP */
	PVP_WARRIOR_BATTLE(false),
	/** 战俘营PVP */
	PVP_PRISON_BATTLE(false),
	/** 角斗场PVE */
	PVE_ABATTOIR_BATTLE(true),
	/** 角斗场PVP */
	PVP_ABATTOIR_BATTLE(false),
	/** 嗜血神殿PVE */
	PVE_BLOOD_TEMPLE_BATTLE(true),
	/** 嗜血神殿PVP */
	PVP_BLOOD_TEMPLE_BATTLE(false),
	/** 战神之巅PVE */
	PVE_MARS_BATTLE(true),
	/** 战神之巅PVP */
	PVP_MARS_BATTLE(false), 
	/** 军团BOSS战战斗 */
	PVE_LEGION_BOSS(true),
	/** 军团矿战战斗 */
	PVP_LEGION_MINE(false),
	/** 押运拦截战斗 */
	PVP_ESCORT_ROB(false),
	/** 主城战斗 */
	PVE_MAIN_CITY(true)
	;
	private boolean isPVE;
	BattleType(boolean isPVE) {
		this.isPVE = isPVE;
	}
	public boolean isPVE() {
		return this.isPVE;
	}
}
