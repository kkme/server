package com.hifun.soul.gameserver.event;

/**
 * 精灵升级事件;
 * 
 * @author yandajun
 * 
 */
public class SpriteUpgradeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.SPRITE_UPGRADE_EVENT;
	}

}
