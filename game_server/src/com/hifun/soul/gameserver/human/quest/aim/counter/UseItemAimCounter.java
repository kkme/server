package com.hifun.soul.gameserver.human.quest.aim.counter;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.UseItemEvent;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.MainQuestAimType;

/**
 * 使用物品目标计数器;
 * 
 * @author crazyjohn
 * 
 */
public class UseItemAimCounter extends AbstractMainQuestAimCounter {

	public UseItemAimCounter(MainQuestAimType type, AimInfo aim, int index) {
		super(type, aim, index);
	}

	@Override
	public EnumSet<EventType> getEventTypeSet() {
		return EnumSet.of(EventType.USE_ITEM_EVENT);
	}

	@Override
	public boolean onQuestEvent(IEvent event) {
		switch (event.getType()) {
		case USE_ITEM_EVENT: {
			UseItemEvent useEvent = (UseItemEvent) event;
			if (useEvent.getItemId() == this.getAim().getParam1()) {
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
