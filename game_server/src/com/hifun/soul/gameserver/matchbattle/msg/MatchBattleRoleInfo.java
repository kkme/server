package com.hifun.soul.gameserver.matchbattle.msg;

public class MatchBattleRoleInfo {
	private long roleId;
	private String roleName;
	private int battleState;
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
	public int getBattleState() {
		return battleState;
	}
	public void setBattleState(int battleState) {
		this.battleState = battleState;
	}
	
}
