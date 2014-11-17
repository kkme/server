package com.hifun.soul.gameserver.refine;

/**
 * 试炼地图信息
 * @author magicstone
 */
public class RefineMapInfo {
	private int mapId;
	private String mapName;
	private boolean mapActivated;
	private int mapOpenLevel;
	private int mapStageNum;
	private int bestStageId;
	
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public boolean getMapActivated() {
		return mapActivated;
	}
	public void setMapActivated(boolean mapActivated) {
		this.mapActivated = mapActivated;
	}
	public int getMapOpenLevel() {
		return mapOpenLevel;
	}
	public void setMapOpenLevel(int mapOpenLevel) {
		this.mapOpenLevel = mapOpenLevel;
	}
	public int getMapStageNum() {
		return mapStageNum;
	}
	public void setMapStageNum(int mapStageNum) {
		this.mapStageNum = mapStageNum;
	}
	public int getBestStageId() {
		return bestStageId;
	}
	public void setBestStageId(int bestStageId) {
		this.bestStageId = bestStageId;
	}
}
