package com.hifun.soul.gameserver.event;

/**
 * 军衔升级事件;
 * 
 * @author yandajun
 * 
 */
public class TitleUpEvent implements IEvent {
	/** 当前等级 */
	private int currentTitle;

	@Override
	public EventType getType() {
		return EventType.TITLE_UP_EVENT;
	}

	public int getCurrentTitle() {
		return currentTitle;
	}

	public void setCurrentTitle(int currentTitle) {
		this.currentTitle = currentTitle;
	}

}
