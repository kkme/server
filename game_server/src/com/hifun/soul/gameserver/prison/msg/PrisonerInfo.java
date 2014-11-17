package com.hifun.soul.gameserver.prison.msg;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.prison.Prisoner;

public class PrisonerInfo {

	private long humanId;
	private String humanName;
	private int humanLevel;
	private int identityType;
	private String legionName;
	private long legionId;
	private int occupationType;

	public PrisonerInfo() {

	}

	public PrisonerInfo(Prisoner prisoner) {
		this.humanId = prisoner.getHumanId();
		this.humanName = prisoner.getHumanName();
		this.humanLevel = prisoner.getHumanLevel();
		this.identityType = prisoner.getIdentityType();
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				humanId);
		if (legion != null) {
			this.legionName = legion.getLegionName();
			this.legionId = legion.getId();
		}
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getHumanLevel() {
		return humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}

	public int getIdentityType() {
		return identityType;
	}

	public void setIdentityType(int identityType) {
		this.identityType = identityType;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public int getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(int occupationType) {
		this.occupationType = occupationType;
	}

}
