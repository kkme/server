package com.hifun.soul.gameserver.human.quest.daily;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.counter.AbstractDailyQuestAimCounter;

/**
 * 日常嗜血神殿挑战目标;
 * 
 * @author yandajun
 * 
 */
public class DailyBloodTempleChallengeAimCounter extends
		AbstractDailyQuestAimCounter {

	public DailyBloodTempleChallengeAimCounter(IQuestAimType type, AimInfo aim,
			int index) {
		super(type, aim, index);
	}

	@Override
	public EnumSet<EventType> getEventTypeSet() {
		return EnumSet.of(EventType.BLOOD_TEMPLE_CHALLENGE_EVENT);
	}

	@Override
	public boolean onQuestEvent(IEvent event) {
		switch (event.getType()) {
		case BLOOD_TEMPLE_CHALLENGE_EVENT: {
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
