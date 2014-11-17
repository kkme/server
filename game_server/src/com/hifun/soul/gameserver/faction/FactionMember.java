package com.hifun.soul.gameserver.faction;

/**
 * 阵营成员对象;
 * 
 * @author crazyjohn
 * 
 */
public class FactionMember {
	/** 角色guid */
	private long humanGuid;
	/** 阵营类型 */
	private int factionType;

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public int getFactionType() {
		return factionType;
	}

	public void setFactionType(int factionType) {
		this.factionType = factionType;
	}
}
