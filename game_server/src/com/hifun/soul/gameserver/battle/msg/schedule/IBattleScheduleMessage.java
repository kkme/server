package com.hifun.soul.gameserver.battle.msg.schedule;

import com.hifun.soul.gameserver.battle.Battle;
/**
 * 战斗调度消息接口;
 * @author crazyjohn
 *
 */
public interface IBattleScheduleMessage{
	/**
	 * 获取关联的战斗对象;
	 * @return
	 */
	public Battle getBattle();
	
}
