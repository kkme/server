package com.hifun.soul.gameserver.battle.msg.schedule;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.battle.Battle;

/**
 * 重置棋盘反馈调度;
 * 
 * @author crazyjohn
 * 
 */
public class RestChessBoardFeedbackSchedule extends SceneScheduleMessage implements IBattleScheduleMessage{
	private Battle battle;

	public RestChessBoardFeedbackSchedule(Battle battle) {
		this.battle = battle;
	}

	@Override
	public void execute() {
		if (this.isCanceled()) {
			return;
		}
		battle.continueBattleAction();
	}

	@Override
	public Battle getBattle() {
		return battle;
	}

}
