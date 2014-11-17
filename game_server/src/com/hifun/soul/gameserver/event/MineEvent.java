package com.hifun.soul.gameserver.event;

/**
 * 采矿事件;
 * 
 * @author crazyjohn
 * 
 */
public class MineEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.MINE_EVENT;
	}

}
