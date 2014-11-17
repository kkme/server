package com.hifun.soul.gameserver.event;

/**
 * 竞技场挑战事件;
 * 
 * @author yandajun
 * 
 */
public class ArenaChallengeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.ARENA_CHALLENGE_EVENT;
	}

}
