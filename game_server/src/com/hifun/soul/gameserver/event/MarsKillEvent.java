package com.hifun.soul.gameserver.event;

/**
 * 战神之巅击杀事件;
 * 
 * @author yandajun
 * 
 */
public class MarsKillEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.MARS_KILL_EVENT;
	}

}
