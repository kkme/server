package com.hifun.soul.gameserver.prison.msg;

public class HelperInfo {
	private long humanId;
	private String humanName;
	private int humanLevel;
	private int identityType;
	private String legionName;
	private long legionId;
	private boolean isForHelped;
	private int occupationType;

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

	public boolean getIsForHelped() {
		return isForHelped;
	}

	public void setIsForHelped(boolean isForHelped) {
		this.isForHelped = isForHelped;
	}

	public int getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(int occupationType) {
		this.occupationType = occupationType;
	}
}
