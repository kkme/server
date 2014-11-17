package com.hifun.soul.gameserver.event;

/**
 * 占星事件;
 * 
 * @author crazyjohn
 * 
 */
public class HoroscopeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.HOROSCOPE_EVENT;
	}

}
