package com.hifun.soul.gameserver.prison.msg;

public class SlaveInfo {
	private long humanId;
	private String humanName;
	private int humanLevel;
	private int occupationType;
	private int identityType;
	private String legionName;
	private long legionId;
	private int currentExperience;

	private int totalExperience;
	private int totalExpCost;
	private int slaveTimeDiff;
	private int beInteractedTimeDiff;

	public int getSlaveTimeDiff() {
		return slaveTimeDiff;
	}

	public void setSlaveTimeDiff(int slaveTimeDiff) {
		this.slaveTimeDiff = slaveTimeDiff;
	}

	public int getBeInteractedTimeDiff() {
		return beInteractedTimeDiff;
	}

	public void setBeInteractedTimeDiff(int beInteractedTimeDiff) {
		this.beInteractedTimeDiff = beInteractedTimeDiff;
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getHumanLevel() {
		return humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}

	public int getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(int occupationType) {
		this.occupationType = occupationType;
	}

	public int getIdentityType() {
		return identityType;
	}

	public void setIdentityType(int identityType) {
		this.identityType = identityType;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public int getCurrentExperience() {
		return currentExperience;
	}

	public void setCurrentExperience(int currentExperience) {
		this.currentExperience = currentExperience;
	}

	public int getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(int totalExperience) {
		this.totalExperience = totalExperience;
	}

	public int getTotalExpCost() {
		return totalExpCost;
	}

	public void setTotalExpCost(int totalExpCost) {
		this.totalExpCost = totalExpCost;
	}

}
