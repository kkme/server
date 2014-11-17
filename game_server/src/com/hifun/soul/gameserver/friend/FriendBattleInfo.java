package com.hifun.soul.gameserver.friend;

/**
 * 好友挑战信息
 */
public class FriendBattleInfo {
	/** 角色id */
	private long roleId;
	/** 角色名称 */
	private String roleName;
	/** 其他角色id */
	private long otherRoleId;
	/** 其他角色名称 */
	private String otherRoleName;
	/** 是否胜利 */
	private boolean win;
	/** 战斗时间 */
	private long battleTime;
	/** 是否是战斗发起者 */
	private boolean isChallenger;
	/** 时间间隔 */
	private int timeInterval;
	
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
	public long getOtherRoleId() {
		return otherRoleId;
	}
	public void setOtherRoleId(long otherRoleId) {
		this.otherRoleId = otherRoleId;
	}
	public String getOtherRoleName() {
		return otherRoleName;
	}
	public void setOtherRoleName(String otherRoleName) {
		this.otherRoleName = otherRoleName;
	}
	public boolean getWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public long getBattleTime() {
		return battleTime;
	}
	public void setBattleTime(long battleTime) {
		this.battleTime = battleTime;
	}
	public boolean getIsChallenger() {
		return isChallenger;
	}
	public void setIsChallenger(boolean isChallenger) {
		this.isChallenger = isChallenger;
	}
	public int getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	
}
