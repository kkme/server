package com.hifun.soul.gameserver.human.quest.aim.counter;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.CollectItemEvent;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.MainQuestAimType;

/**
 * 收集物品目标;
 * 
 * @author crazyjohn
 * 
 */
public class CollectItemAimCounter extends AbstractMainQuestAimCounter {

	public CollectItemAimCounter(MainQuestAimType type, AimInfo aim, int index) {
		super(type, aim, index);
	}

	@Override
	public EnumSet<EventType> getEventTypeSet() {
		return EnumSet.of(EventType.COLLECT_ITEM_EVENT);
	}

	@Override
	public boolean onQuestEvent(IEvent event) {
		switch (event.getType()) {
		case COLLECT_ITEM_EVENT: {
			CollectItemEvent collectEvent = (CollectItemEvent) event;
			if (collectEvent.getItemId() == this.getAim().getParam1()) {
				if (this.currentValue < this.getAim().getParam2()) {
					this.currentValue++;
					return true;
				}
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
