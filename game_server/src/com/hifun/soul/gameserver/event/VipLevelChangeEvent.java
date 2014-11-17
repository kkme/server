package com.hifun.soul.gameserver.event;

/**
 * vip等级改变事件;
 * 
 * @author magicstone
 * 
 */
public class VipLevelChangeEvent implements IEvent {
	/** vip等级 */
	private int vipLevel;
	/** 改变的等级 */
	private int changeLevel;

	@Override
	public EventType getType() {
		return EventType.VIP_LEVEL_CHANGE_EVENT;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getChangeLevel() {
		return changeLevel;
	}

	public void setChangeLevel(int changeLevel) {
		this.changeLevel = changeLevel;
	}

}
