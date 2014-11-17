package com.hifun.soul.gameserver.battle.ai;

/**
 * 战斗AI接口;<br>
 * FIXME: crazyjohn 对于AI来说, 可以支持脚本编写还是比较方便, 至少可以做到scala这类语言编写, MD java写起来太SB了,
 * 有时间搞一下;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleAI {

	/**
	 * 行动接口;
	 * 
	 * @param thinkTimes
	 *            模拟思考时间;
	 */
	public void action(long thinkTimes);

}
