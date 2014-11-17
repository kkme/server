package com.hifun.soul.gameserver.escort.info;

/**
 * 护送好友信息
 * 
 * @author yandajun
 * 
 */
public class EscortFriendInfo {
	private long friendId;
	private String friendName;
	private int friendLevel;
	private int remainHelpNum;
	private int maxHelpNum;

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public int getFriendLevel() {
		return friendLevel;
	}

	public void setFriendLevel(int friendLevel) {
		this.friendLevel = friendLevel;
	}

	public int getRemainHelpNum() {
		return remainHelpNum;
	}

	public void setRemainHelpNum(int remainHelpNum) {
		this.remainHelpNum = remainHelpNum;
	}

	public int getMaxHelpNum() {
		return maxHelpNum;
	}

	public void setMaxHelpNum(int maxHelpNum) {
		this.maxHelpNum = maxHelpNum;
	}
}
