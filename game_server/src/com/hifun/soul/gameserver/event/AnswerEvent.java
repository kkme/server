package com.hifun.soul.gameserver.event;

/**
 * 答题事件;
 * 
 * @author crazyjohn
 * 
 */
public class AnswerEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.ANSWER_EVENT;
	}

}
