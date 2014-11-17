package com.hifun.soul.gameserver.human.quest.aim.counter;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;

/**
 * 任务目标计数器抽象, 适用于主线任务的目标;
 * 
 * @author crazyjohn
 * 
 */
public abstract class AbstractMainQuestAimCounter implements IQuestAimCounter {
	protected AimInfo aim;
	protected int currentValue;
	protected int index;
	protected IQuestAimType type;

	public AbstractMainQuestAimCounter(IQuestAimType type, AimInfo aim, int index) {
		this.type = type;
		this.aim = aim;
		this.index = index;
	}

	@Override
	public IQuestAimType getType() {
		return type;
	}

	@Override
	public int getValue() {
		return currentValue;
	}

	@Override
	public void setValue(int value) {
		this.currentValue = value;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public abstract EnumSet<EventType> getEventTypeSet();

	@Override
	public boolean isFinished() {
		if (this.currentValue == this.aim.getParam2()) {
			return true;
		}
		return false;
	}

	@Override
	public void init(Human human) {
		this.currentValue = 0;
	}

	@Override
	public abstract boolean onQuestEvent(IEvent event);

	@Override
	public AimInfo getAim() {
		return aim;
	}

}
