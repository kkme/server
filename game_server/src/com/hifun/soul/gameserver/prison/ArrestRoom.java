package com.hifun.soul.gameserver.prison;

/**
 * 抓捕房间
 * 
 * @author yandajun
 * 
 */
public class ArrestRoom {
	private int roomId;
	private int unlockNeedCrytalNum;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getUnlockNeedCrytalNum() {
		return unlockNeedCrytalNum;
	}

	public void setUnlockNeedCrytalNum(int unlockNeedCrytalNum) {
		this.unlockNeedCrytalNum = unlockNeedCrytalNum;
	}
}
