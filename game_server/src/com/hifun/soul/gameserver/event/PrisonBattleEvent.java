package com.hifun.soul.gameserver.event;

/**
 * 战俘营挑战事件;
 * 
 * @author yandajun
 * 
 */
public class PrisonBattleEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.PRISON_BATTLE_EVENT;
	}

}
