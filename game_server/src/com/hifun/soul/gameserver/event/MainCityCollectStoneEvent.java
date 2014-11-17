package com.hifun.soul.gameserver.event;

/**
 * 主城通灵事件;
 * 
 * @author yandajun
 * 
 */
public class MainCityCollectStoneEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.MAIN_CITY_COLLECT_STONE_EVENT;
	}

}
