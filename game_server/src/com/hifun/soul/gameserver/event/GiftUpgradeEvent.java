package com.hifun.soul.gameserver.event;

/**
 * 天赋升级事件;
 * 
 * @author yandajun
 * 
 */
public class GiftUpgradeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.GIFT_UPGRADE_EVENT;
	}

}
