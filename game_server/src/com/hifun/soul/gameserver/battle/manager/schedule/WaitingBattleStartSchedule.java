package com.hifun.soul.gameserver.battle.manager.schedule;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.battle.Battle;

/**
 * 等待战斗从INIT状态到START状态的超时;
 * 
 * @author crazyjohn
 * 
 */
public class WaitingBattleStartSchedule extends SceneScheduleMessage {
	private Battle battle;
	
	public WaitingBattleStartSchedule(Battle battle) {
		this.battle = battle;
	}
	@Override
	public void execute() {
		if (this.isCanceled()) {
			return;
		}
		battle.waitingBattleStartTimeout();
	}
	public Battle getBattle() {
		return battle;
	}

}
