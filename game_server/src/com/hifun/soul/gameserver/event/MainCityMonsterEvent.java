package com.hifun.soul.gameserver.event;

/**
 * 试炼挑战事件;
 * 
 * @author crazyjohn
 * 
 */
public class MainCityMonsterEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.MAIN_CITY_MONSTER_EVENT;
	}

}
