package com.hifun.soul.gameserver.event;

/**
 * 科技升级事件;
 * 
 * @author crazyjohn
 * 
 */
public class TechUpgradeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.TECH_UPGRADE_EVENT;
	}

}
