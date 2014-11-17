package com.hifun.soul.gameserver.event;

/**
 * 巫术套现事件;
 * 
 * @author yandajun
 * 
 */
public class CrystalExchangeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.CRYSTAL_EXCHANGE_EVENT;
	}

}
