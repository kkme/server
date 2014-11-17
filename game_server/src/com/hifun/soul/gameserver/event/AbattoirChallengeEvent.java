package com.hifun.soul.gameserver.event;

/**
 * 嗜血神殿挑战事件;
 * 
 * @author yandajun
 * 
 */
public class AbattoirChallengeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.ABATTOIR_CHALLENGE_EVENT;
	}

}
