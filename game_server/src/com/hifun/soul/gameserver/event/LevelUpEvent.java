package com.hifun.soul.gameserver.event;

/**
 * 升级事件;
 * 
 * @author crazyjohn
 * 
 */
public class LevelUpEvent implements IEvent {
	/** 当前等级 */
	private int currentLevel;

	@Override
	public EventType getType() {
		return EventType.LEVEL_UP_EVENT;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

}
