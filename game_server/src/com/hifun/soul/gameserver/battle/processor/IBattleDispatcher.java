package com.hifun.soul.gameserver.battle.processor;

import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 战斗派发中心接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleDispatcher {

	/**
	 * 分发战斗消息;
	 * 
	 * @param message
	 */
	public void dispatchBattleMessage(CGMessage message);

	/**
	 * 启动;
	 */
	public void start();

	/**
	 * 关闭;
	 * 
	 */
	public void stop();

	void scheduleOnece(SysInternalMessage task, long delay);

}
