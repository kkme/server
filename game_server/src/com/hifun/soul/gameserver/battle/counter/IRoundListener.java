package com.hifun.soul.gameserver.battle.counter;

/**
 * 回合监听器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IRoundListener {

	/**
	 * 回合结束以后的回调;
	 */
	public void onRoundFinished();

	/**
	 * 记录行动;
	 */
	public void recordAction();

	/**
	 * 在监听期间总共的行动次数;
	 * 
	 * @return
	 */
	public int getActionTimes();

}
