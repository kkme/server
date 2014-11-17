package com.hifun.soul.gameserver.event;

/**
 * 属性培养事件;
 * 
 * @author yandajun
 * 
 */
public class PropertyFosterEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.PROPERTY_FOSTER_EVENT;
	}

}
