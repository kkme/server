package com.hifun.soul.gameserver.event;

/**
 * 装备洗练事件;
 * 
 * @author crazyjohn
 * 
 */
public class EquipRefineEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.EQUIP_REFINE_EVENT;
	}

}
