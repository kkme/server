package com.hifun.soul.gameserver.event;

/**
 * 嗜血神殿挑战事件;
 * 
 * @author yandajun
 * 
 */
public class BloodTempleChallengeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.BLOOD_TEMPLE_CHALLENGE_EVENT;
	}

}
