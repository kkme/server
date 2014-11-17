package com.hifun.soul.gameserver.event;

/**
 * 税收事件;
 * 
 * @author crazyjohn
 * 
 */
public class RevenueEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.REVENUE_EVENT;
	}

}
