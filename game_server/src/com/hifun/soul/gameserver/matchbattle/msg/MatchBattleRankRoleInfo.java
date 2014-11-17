package com.hifun.soul.gameserver.matchbattle.msg;

public class MatchBattleRankRoleInfo {
	private long roleId;
	private String roleName;
	private int occupation;
	private int level;
	private int consecutiveWinCount;
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
	public int getOccupation() {
		return occupation;
	}
	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getConsecutiveWinCount() {
		return consecutiveWinCount;
	}
	public void setConsecutiveWinCount(int consecutiveWinCount) {
		this.consecutiveWinCount = consecutiveWinCount;
	}
	
}
