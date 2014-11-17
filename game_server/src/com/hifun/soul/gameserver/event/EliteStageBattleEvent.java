package com.hifun.soul.gameserver.event;

public class EliteStageBattleEvent implements IQuestEvent {
	private boolean isWin;

	public EliteStageBattleEvent(boolean isWin){
		this.isWin = isWin;
	}
	@Override
	public EventType getType() {
		return EventType.ELITE_STAGE_BATTLE_EVENT;
	}
	public boolean isWin() {
		return isWin;
	}
}
