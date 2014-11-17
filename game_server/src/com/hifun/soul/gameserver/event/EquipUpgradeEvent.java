package com.hifun.soul.gameserver.event;

/**
 * 装备强化事件;
 * 
 * @author crazyjohn
 * 
 */
public class EquipUpgradeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.EQUIP_UPGRADE_EVENT;
	}

}
