package com.hifun.soul.gameserver.battle.action;

import com.hifun.soul.gameserver.human.Human;

/**
 * 认为是外挂, 直接踢下线;
 * 
 * @author crazyjohn
 * 
 */
public class KickOffHandler implements IInvalidMoveHandler {
	@Override
	public void handleInvalidAction(Human unit) {
		if (unit.getPlayer().getSession() != null) {
			unit.getPlayer().getSession().close();
		}
	}

}
