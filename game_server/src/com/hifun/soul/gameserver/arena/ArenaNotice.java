package com.hifun.soul.gameserver.arena;

public class ArenaNotice {
	/** 角色id */
	private long roleId;
	/** 角色名称 */
	private String roleName;
	/** 其他角色id */
	private long otherRoleId;
	/** 其他角色名称 */
	private String otherRoleName;
	/** 是否是赢家 */
	private boolean win;
	/** 挑战时间 */
	private long challengeTime;
	/** 时间间隔 */
	private int timeInterval;
	/** 是否是挑战者 */
	private boolean isChallenger;
	/** 排名 */
	private int rank;
	/** 是否是前5名，且高排名挑战低排名 */
	private boolean isUpFiveAndHigherVsLower;
	
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
	public boolean getWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
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
	public int getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	public boolean getIsChallenger() {
		return isChallenger;
	}
	public void setIsChallenger(boolean isChallenger) {
		this.isChallenger = isChallenger;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public long getChallengeTime() {
		return challengeTime;
	}
	public void setChallengeTime(long challengeTime) {
		this.challengeTime = challengeTime;
	}
	public boolean getIsUpFiveAndHigherVsLower() {
		return isUpFiveAndHigherVsLower;
	}
	public void setIsUpFiveAndHigherVsLower(boolean isUpFiveAndHigherVsLower) {
		this.isUpFiveAndHigherVsLower = isUpFiveAndHigherVsLower;
	}
	
}
