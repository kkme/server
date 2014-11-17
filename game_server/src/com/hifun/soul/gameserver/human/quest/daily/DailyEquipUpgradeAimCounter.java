package com.hifun.soul.gameserver.human.quest.daily;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.counter.AbstractDailyQuestAimCounter;

/**
 * 日常任务装备强化目标;
 * 
 * @author crazyjohn
 * 
 */
public class DailyEquipUpgradeAimCounter extends AbstractDailyQuestAimCounter {

	public DailyEquipUpgradeAimCounter(IQuestAimType type, AimInfo aim,
			int index) {
		super(type, aim, index);
	}

	@Override
	public EnumSet<EventType> getEventTypeSet() {
		return EnumSet.of(EventType.EQUIP_UPGRADE_EVENT);
	}

	@Override
	public boolean onQuestEvent(IEvent event) {
		switch (event.getType()) {
		case EQUIP_UPGRADE_EVENT: {
			if (this.currentValue < this.aim.getParam1()) {
				this.currentValue++;
				return true;
			}
			break;
		}
		default: {
			return false;
		}
		}
		return false;
	}

}
