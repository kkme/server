package com.hifun.soul.gameserver.battle;

import java.util.Random;

/**
 * 战斗工具类;
 * 
 * @author crazyjohn
 * 
 */
public class BattleUtil {
	private static Random rand = new Random();

	/**
	 * 获取托管玩家的思考时间;
	 * 
	 * @return
	 */
	public static long getHostingHumanThinkTimes() {
		// 模拟随机的思考时间;范围1-5s;
		return (rand.nextInt(1000));
	}

	/**
	 * 获取角色守卫者的思考时间;
	 * 
	 * @return
	 */
	public static long getHumanGuarderThinkTimes() {
		return getHostingHumanThinkTimes();
	}
}
