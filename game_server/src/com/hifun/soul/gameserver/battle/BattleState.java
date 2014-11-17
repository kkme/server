package com.hifun.soul.gameserver.battle;

/**
 * 战斗状态;
 * 
 * @author crazyjohn
 * 
 */
public enum BattleState {
	/** 战斗初始状态 */
	INIT,
	/** 战斗开始状态 */
	STARTING,
	/** 战斗进行中重置棋盘状态 */
	RESETING,
	/** 战斗完成状态 */
	FINISHED;
}
