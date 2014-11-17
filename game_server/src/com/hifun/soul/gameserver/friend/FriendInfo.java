package com.hifun.soul.gameserver.friend;

/**
 * 玩家单个好友信息
 */
public class FriendInfo {
	private long roleId;
	private String roleName;
	private int level;
	private int occupation;
	/** 是否送出体力 */
	private boolean sendState = false;
	/** 领取体力状态 */
	private int getState = FriendRewardState.NO_REWARD.getIndex();
	/** 在线状态 */
	private boolean isOnLine = false;
	/** 黄钻等级 */
	private int yellowVipLevel;
	/** 是否为年费黄钻会员 */
	private boolean isYearYellowVip;
	/** 是否为年费黄钻会员 */
	private boolean isYellowHighVip;

	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getOccupation() {
		return occupation;
	}
	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}
	public boolean getSendState() {
		return sendState;
	}
	public void setSendState(boolean sendState) {
		this.sendState = sendState;
	}
	public int getGetState() {
		return getState;
	}
	public void setGetState(int getState) {
		this.getState = getState;
	}
	public boolean getIsOnLine() {
		return isOnLine;
	}
	public void setIsOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}
	public int getYellowVipLevel() {
		return yellowVipLevel;
	}
	public void setYellowVipLevel(int yellowVipLevel) {
		this.yellowVipLevel = yellowVipLevel;
	}
	public boolean getIsYearYellowVip() {
		return isYearYellowVip;
	}
	public void setIsYearYellowVip(boolean isYearYellowVip) {
		this.isYearYellowVip = isYearYellowVip;
	}
	public boolean getIsYellowHighVip() {
		return isYellowHighVip;
	}
	public void setIsYellowHighVip(boolean isYellowHighVip) {
		this.isYellowHighVip = isYellowHighVip;
	}
	
}
