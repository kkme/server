package com.hifun.soul.gameserver.battle.counter;

import com.hifun.soul.gameserver.battle.IBattleUnit;

/**
 * 战斗回合计数器接口;<br>
 * PVE如果战斗回合超时, 则战斗失败;PVP如果战斗回合超时, 则挑战方失败;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleRoundCounter {
	/**
	 * 记录行动单元;
	 * 
	 * @param actionUnit
	 */
	public void recordActionUnit(IBattleUnit actionUnit);

	/**
	 * 记录角色行动超时的情况;
	 * 
	 * @param actionUnit
	 */
	public void recoredActionTimeout(IBattleUnit actionUnit);

	/**
	 * 记录角色无法行动的情况;
	 * 
	 * @param actionUnit
	 */
	public void recoredCanNotAction(IBattleUnit actionUnit);

	/**
	 * 获取当前战斗回合数;
	 * 
	 * @return
	 */
	public int getCurrentRound();

	/**
	 * 添加回合监听器;
	 * 
	 * @param listener
	 */
	public void addRoundListener(IRoundListener listener);

	/**
	 * 移除回合监听器;
	 * 
	 * @param listener
	 */
	public void removeRoundListener(IRoundListener listener);

	/**
	 * 是否达到最大战斗回合数;
	 * 
	 * @return
	 */
	boolean isReachMaxBattleRound();

	/**
	 * 记录对方额外回合, 相当于自己的回合被跳过;
	 * 
	 * @param self
	 *            战斗单元本身;
	 */
	public void recordEnemyNewRound(IBattleUnit self);

}
