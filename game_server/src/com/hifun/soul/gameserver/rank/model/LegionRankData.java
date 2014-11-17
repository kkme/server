package com.hifun.soul.gameserver.rank.model;

public abstract class LegionRankData extends RankData {
	protected long legionId;
	protected String legionName;
	protected String commanderName;
	protected int legionLevel;
	protected int memberNum;

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

	public String getCommanderName() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}

	public int getLegionLevel() {
		return legionLevel;
	}

	public void setLegionLevel(int legionLevel) {
		this.legionLevel = legionLevel;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

}
