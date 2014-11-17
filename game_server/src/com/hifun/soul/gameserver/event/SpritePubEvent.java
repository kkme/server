package com.hifun.soul.gameserver.event;

/**
 * 天赋升级事件;
 * 
 * @author yandajun
 * 
 */
public class SpritePubEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.SPRITE_PUB_EVENT;
	}

}
