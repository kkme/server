package com.hifun.soul.gameserver.faction.model;

/**
 * 阵营简单信息;
 * 
 * @author crazyjohn
 * 
 */
public class FactionSimpleInfo {
	/** 阵营类型 */
	private int factionType;
	/** 阵营icon */
	private int factionIconId;
	/** 阵营描述 */
	private String factionDesc;

	public int getFactionType() {
		return factionType;
	}

	public void setFactionType(int factionType) {
		this.factionType = factionType;
	}

	public int getFactionIconId() {
		return factionIconId;
	}

	public void setFactionIconId(int factionIconId) {
		this.factionIconId = factionIconId;
	}

	public String getFactionDesc() {
		return factionDesc;
	}

	public void setFactionDesc(String factionDesc) {
		this.factionDesc = factionDesc;
	}

}
