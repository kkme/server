package com.hifun.soul.gameserver.mars;

/**
 * 战神之巅房间锁定状态
 * 
 * @author yandajun
 * 
 */
public enum MarsRoomLockState {
	/** 未锁定 */
	UNLOCKED(0),
	/** 锁定 */
	LOCKED(1);
	private int index;

	private MarsRoomLockState(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
