package com.hifun.soul.common.model.building;


/**
 * 
 * 建筑信息，用于与客户端通信
 * 
 * @author magicstone
 *
 */
public class BuildingInfo {

	private int buildingId;
	
	private String name;
	
	private String desc;
	
	private int icon;
	
	private int level;
	
	private boolean upgrade;

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}
	
}
