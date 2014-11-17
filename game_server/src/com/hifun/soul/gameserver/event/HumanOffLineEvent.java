package com.hifun.soul.gameserver.event;

/**
 * 角色离线事件;
 * 
 * @author yandajun
 * 
 */
public class HumanOffLineEvent implements IEvent {
	/** 离线时间 */
	private long offLineTime;

	@Override
	public EventType getType() {
		return EventType.HUMAN_OFF_LINE_EVENT;
	}

	public long getOffLineTime() {
		return offLineTime;
	}

	public void setOffLineTime(long offLineTime) {
		this.offLineTime = offLineTime;
	}

}
