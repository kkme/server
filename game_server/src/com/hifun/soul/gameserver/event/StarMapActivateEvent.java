package com.hifun.soul.gameserver.event;

/**
 * 星图点亮事件;
 * 
 * @author yandajun
 * 
 */
public class StarMapActivateEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.STAR_MAP_ACTIVATE_EVENT;
	}

}
