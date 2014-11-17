package com.hifun.soul.gameserver.battle.msg.schedule;

import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.ai.BaseMonsterAI;

public class MonsterAIActionSchedule extends SysInternalMessage implements
		IBattleScheduleMessage {
	private Battle battle;
	private BaseMonsterAI ai;
	public MonsterAIActionSchedule(Battle battle, BaseMonsterAI ai) {
		this.battle = battle;
		this.ai = ai;
	}
	
	@Override
	public void execute() {
		ai.afterThink();
	}

	@Override
	public Battle getBattle() {
		return battle;
	}

}
