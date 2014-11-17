package com.hifun.soul.gameserver.legion;

public class LegionBuilding {
	private long id;

	private long legionId;
	private int buildingType;
	private int buildingLevel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public int getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(int buildingType) {
		this.buildingType = buildingType;
	}

	public int getBuildingLevel() {
		return buildingLevel;
	}

	public void setBuildingLevel(int buildingLevel) {
		this.buildingLevel = buildingLevel;
	}
}
