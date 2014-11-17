package com.hifun.soul.gameserver.legionmine.msg.info;

public class JoinLegionInfo {
	private long legionId;
	private String legionName;
	private int occupyValue;
	private int joinLegionType;

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

	public int getOccupyValue() {
		return occupyValue;
	}

	public void setOccupyValue(int occupyValue) {
		this.occupyValue = occupyValue;
	}

	public int getJoinLegionType() {
		return joinLegionType;
	}

	public void setJoinLegionType(int joinLegionType) {
		this.joinLegionType = joinLegionType;
	}

}
