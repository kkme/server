package com.hifun.soul.gameserver.stage.model;

/**
 * 地图信息
 * @author magicstone
 */
public class StageMapInfo {
	/** 地图id */
	private int mapId;
	/** 地图名称 */
	private String mapName;
	/** 地图描述 */
	private String mapDesc;
	/** 地图icon */
	private int icon;
	/** 通关数量 */
	private int passCount;
	/** 所有关卡数量 */
	private int totalCount;
	/** 地图小icon */
	private int simpleIcon;
	/** 章节 */
	private String chapter;
	/** 地图状态 */
	private int mapState;
	/** 下章地图id */
	private int nextMapId;

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
	public String getMapDesc() {
		return mapDesc;
	}
	public void setMapDesc(String mapDesc) {
		this.mapDesc = mapDesc;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSimpleIcon() {
		return simpleIcon;
	}
	public void setSimpleIcon(int simpleIcon) {
		this.simpleIcon = simpleIcon;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public int getMapState() {
		return mapState;
	}
	public void setMapState(int mapState) {
		this.mapState = mapState;
	}
	public int getNextMapId() {
		return nextMapId;
	}
	public void setNextMapId(int nextMapId) {
		this.nextMapId = nextMapId;
	}
	
}
