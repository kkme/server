package com.hifun.soul.gameserver.legion.info;

/**
 * 军团建筑信息
 * 
 * @author yandajun
 * 
 */
public class LegionBuildingInfo {
	private int currentNum;
	private int buildingLevel;
	private int nextNum;
	private int needLegionCoin;
	private int currentLegionCoin;

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public int getBuildingLevel() {
		return buildingLevel;
	}

	public void setBuildingLevel(int buildingLevel) {
		this.buildingLevel = buildingLevel;
	}

	public int getNextNum() {
		return nextNum;
	}

	public void setNextNum(int nextNum) {
		this.nextNum = nextNum;
	}

	public int getNeedLegionCoin() {
		return needLegionCoin;
	}

	public void setNeedLegionCoin(int needLegionCoin) {
		this.needLegionCoin = needLegionCoin;
	}

	public int getCurrentLegionCoin() {
		return currentLegionCoin;
	}

	public void setCurrentLegionCoin(int currentLegionCoin) {
		this.currentLegionCoin = currentLegionCoin;
	}

}
