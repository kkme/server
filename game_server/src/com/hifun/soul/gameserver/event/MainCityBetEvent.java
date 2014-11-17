package com.hifun.soul.gameserver.event;

/**
 * 主城猜大小事件;
 * 
 * @author yandajun
 * 
 */
public class MainCityBetEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.MAIN_CITY_BET_EVENT;
	}

}
