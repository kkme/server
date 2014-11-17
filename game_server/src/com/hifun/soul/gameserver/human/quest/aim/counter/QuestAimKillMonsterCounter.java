package com.hifun.soul.gameserver.human.quest.aim.counter;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.MonsterDeadEvent;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.MainQuestAimType;

/**
 * 任务目标: 杀怪;<br>
 * 
 * @author crazyjohn
 * 
 */
public class QuestAimKillMonsterCounter extends AbstractMainQuestAimCounter {
	public QuestAimKillMonsterCounter(MainQuestAimType type, AimInfo aim, int index) {
		super(type, aim, index);
	}

	@Override
	public EnumSet<EventType> getEventTypeSet() {
		return EnumSet.of(EventType.MONSTER_DEAD_EVENT);
	}

	@Override
	public boolean onQuestEvent(IEvent event) {
		switch (event.getType()) {
		case MONSTER_DEAD_EVENT: {
			MonsterDeadEvent deadEvent = (MonsterDeadEvent) event;
			if (deadEvent.getMonsterId() == this.getAimMonsterId()) {
				if (this.currentValue < this.getAimKillMonsterCount()) {
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

	private int getAimKillMonsterCount() {
		return this.aim.getParam2();
	}

	private int getAimMonsterId() {
		return this.aim.getParam1();
	}

	@Override
	public AimInfo getAim() {
		return aim;
	}

	@Override
	public int getIndex() {
		return index;
	}

}
