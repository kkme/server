package com.hifun.soul.gameserver.meditation.msg;

import com.hifun.soul.gameserver.friend.FriendInfo;

public class MeditationFriendAssistInfo {
	/** 协助位置 */
	int index;
	/** 好友信息 */
	FriendInfo friendInfo;
	/** 加成比例 */
	int assistRate;
	/** 科技点总数 */
	int totalTechPoint;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public FriendInfo getFriendInfo() {
		return friendInfo;
	}
	public void setFriendInfo(FriendInfo friendInfo) {
		this.friendInfo = friendInfo;
	}
	public int getAssistRate() {
		return assistRate;
	}
	public void setAssistRate(int assistRate) {
		this.assistRate = assistRate;
	}
	public int getTotalTechPoint() {
		return totalTechPoint;
	}
	public void setTotalTechPoint(int totalTechPoint) {
		this.totalTechPoint = totalTechPoint;
	}
	
	
}
