package com.hifun.soul.gameserver.event;

/**
 * 试炼挑战事件;
 * 
 * @author crazyjohn
 * 
 */
public class RefineChallengeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.REFINE_CHALLENGE_EVENT;
	}

}
