package com.hifun.soul.gameserver.event;

/**
 * 装备位升级事件;
 * 
 * @author yandajun
 * 
 */
public class EquipBitUpgradeEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.EQUIP_BIT_UPGRADE_EVENT;
	}

}
