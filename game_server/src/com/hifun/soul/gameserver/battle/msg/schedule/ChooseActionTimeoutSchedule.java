package com.hifun.soul.gameserver.battle.msg.schedule;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;

/**
 * 选择行动超时调度;
 * 
 * @author crazyjohn
 * 
 */
public class ChooseActionTimeoutSchedule extends SceneScheduleMessage implements IBattleScheduleMessage{
	private Battle battle;
	private IBattleUnit battleUnit;

	public ChooseActionTimeoutSchedule(Battle battle, IBattleUnit actionUnit) {
		this.battle = battle;
		this.battleUnit = actionUnit;
	}

	@Override
	public void execute() {
		if (this.isCanceled()) {
			return;
		}
		if (battle == null) {
			return;
		}
		if(!battle.isThisUnitTurn(battleUnit)){
			return;
		}
		battle.onChooseActionTimeout(battleUnit);
	}

	@Override
	public Battle getBattle() {
		return battle;
	}

}
