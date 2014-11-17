package com.hifun.soul.gameserver.legion;

/**
 * 军团申请
 * 
 * @author yandajun
 * 
 */
public class LegionApply {
	private long id;
	private long applyHumanGuid;
	private long applyLegionId;
	private String applyHumanName;
	private int applyHumanLevel;
	private int applyArenaRank;
	private int applyStatus;
	private long applyTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getApplyHumanGuid() {
		return applyHumanGuid;
	}

	public void setApplyHumanGuid(long applyHumanGuid) {
		this.applyHumanGuid = applyHumanGuid;
	}

	public long getApplyLegionId() {
		return applyLegionId;
	}

	public void setApplyLegionId(long applyLegionId) {
		this.applyLegionId = applyLegionId;
	}

	public String getApplyHumanName() {
		return applyHumanName;
	}

	public void setApplyHumanName(String applyHumanName) {
		this.applyHumanName = applyHumanName;
	}

	public int getApplyHumanLevel() {
		return applyHumanLevel;
	}

	public void setApplyHumanLevel(int applyHumanLevel) {
		this.applyHumanLevel = applyHumanLevel;
	}

	public int getApplyArenaRank() {
		return applyArenaRank;
	}

	public void setApplyArenaRank(int applyArenaRank) {
		this.applyArenaRank = applyArenaRank;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

}
