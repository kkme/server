package com.hifun.soul.gameserver.legion;

public class LegionTechnology {
	private long id;
	private long legionId;
	private int technologyType;
	private int technologyLevel;
	private int currentCoin;

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

	public int getTechnologyType() {
		return technologyType;
	}

	public void setTechnologyType(int technologyType) {
		this.technologyType = technologyType;
	}

	public int getTechnologyLevel() {
		return technologyLevel;
	}

	public void setTechnologyLevel(int technologyLevel) {
		this.technologyLevel = technologyLevel;
	}

	public int getCurrentCoin() {
		return currentCoin;
	}

	public void setCurrentCoin(int currentCoin) {
		this.currentCoin = currentCoin;
	}
}
