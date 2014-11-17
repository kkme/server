package com.hifun.soul.gameserver.battle.action;

import com.hifun.soul.gameserver.human.Human;

/**
 * 异常移动以后的处理接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IInvalidMoveHandler {

	public void handleInvalidAction(Human unit);
}
