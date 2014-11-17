package com.hifun.soul.gameserver.legion.msg;

/**
 * 军团成员列表信息
 * 
 * @author yandajun
 * 
 */
public class LegionMemberListInfo {
	private long memberId;
	private String memberName;
	private int level;
	private int arenaRank;
	private String positionName;
	private int totalContribution;
	private int todayContribution;
	private int offLineTimeInterval;
	/** 转让按钮是否可见 */
	private boolean transferButtonVisible;
	/** 移除按钮是否可见 */
	private boolean removeButtonVisible;

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getArenaRank() {
		return arenaRank;
	}

	public void setArenaRank(int arenaRank) {
		this.arenaRank = arenaRank;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public int getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(int totalContribution) {
		this.totalContribution = totalContribution;
	}

	public int getTodayContribution() {
		return todayContribution;
	}

	public void setTodayContribution(int todayContribution) {
		this.todayContribution = todayContribution;
	}

	public int getOffLineTimeInterval() {
		return offLineTimeInterval;
	}

	public void setOffLineTimeInterval(int offLineTimeInterval) {
		this.offLineTimeInterval = offLineTimeInterval;
	}

	public boolean getTransferButtonVisible() {
		return transferButtonVisible;
	}

	public void setTransferButtonVisible(boolean transferButtonVisible) {
		this.transferButtonVisible = transferButtonVisible;
	}

	public boolean getRemoveButtonVisible() {
		return removeButtonVisible;
	}

	public void setRemoveButtonVisible(boolean removeButtonVisible) {
		this.removeButtonVisible = removeButtonVisible;
	}

}
