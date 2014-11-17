package com.hifun.soul.gameserver.human.quest.aim.counter;

import java.util.EnumSet;

import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;

/**
 * 任务目标计时器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IQuestAimCounter {

	public IQuestAimType getType();

	public int getValue();

	public void setValue(int value);
	
	public int getIndex();

	public EnumSet<EventType> getEventTypeSet();

	public boolean isFinished();

	public void init(Human human);

	public boolean onQuestEvent(IEvent event);

	public AimInfo getAim();

}
