package com.hifun.soul.gameserver.event;

/**
 * 怪物死亡事件;
 * 
 * @author crazyjohn
 * 
 */
public class MonsterDeadEvent implements IQuestEvent {
	private int monsterId;

	@Override
	public EventType getType() {
		return EventType.MONSTER_DEAD_EVENT;
	}

	public int getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}

}
