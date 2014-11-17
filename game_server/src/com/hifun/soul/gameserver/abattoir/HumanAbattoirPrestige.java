package com.hifun.soul.gameserver.abattoir;

public class HumanAbattoirPrestige {
	private long humanId;
	private int prestige;
	private long lastExtractTime;

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public int getPrestige() {
		return prestige;
	}

	public void setPrestige(int honor) {
		this.prestige = honor;
	}

	public long getLastExtractTime() {
		return lastExtractTime;
	}

	public void setLastExtractTime(long lastExtractTime) {
		this.lastExtractTime = lastExtractTime;
	}

}
