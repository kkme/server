package com.hifun.soul.gameserver.battle;

/**
 * 战斗行动调度中心接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleActionScheduleCenter {

	/**
	 * 获取下一个行动单元;
	 * 
	 * @return
	 */
	public IBattleUnit getNextActionUnit();

	/**
	 * 是否是指定单元的回合;
	 * 
	 * @param unit
	 * @return
	 */
	public boolean isThisUnitTurn(IBattleUnit unit);

	/**
	 * 切换到下一个单元行动;
	 */
	public void turnToNextUnitAction();

	/**
	 * 开始行动,第一次行动;
	 * 
	 * @param firstActionUnit
	 */
	public void startFirstAction(IBattleUnit firstActionUnit);

	void addActionTimes(IBattleUnit unit);
}
