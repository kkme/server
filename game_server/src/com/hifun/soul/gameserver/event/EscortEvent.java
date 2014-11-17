package com.hifun.soul.gameserver.event;

/**
 * 跑商押运事件;
 * 
 * @author yandajun
 * 
 */
public class EscortEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.ESCORT_EVENT;
	}

}
