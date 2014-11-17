package com.hifun.soul.gameserver.stage;

public class StageMapStateInfo {
	/** 地图id */
	private int mapId;
	/** 地图状态 */
	private int state;
	/** 完美通关奖励状态 */
	private int perfectMapRewardState;

	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getPerfectMapRewardState() {
		return perfectMapRewardState;
	}
	public void setPerfectMapRewardState(int perfectMapRewardState) {
		this.perfectMapRewardState = perfectMapRewardState;
	}
	
}
