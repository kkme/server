package com.hifun.soul.gameserver.stage.model;

/**
 * 完美通关奖励领取状态
 */
public class PerfectMapRewardStateInfo {
	private int mapId;
	private int perfectRewardState;
	
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public int getPerfectRewardState() {
		return perfectRewardState;
	}
	public void setPerfectRewardState(int perfectRewardState) {
		this.perfectRewardState = perfectRewardState;
	}
}
