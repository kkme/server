package com.hifun.soul.gameserver.legionboss;

public class LegionBossInfo {

	private int bossId;

	private String name;

	private int level;

	private int icon;

	private int remainBlood;

	private int totalBlood;

	private int bossState;

	private int joinPeopleNum;

	private long killerId;

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getBossState() {
		return bossState;
	}

	public void setBossState(int bossState) {
		this.bossState = bossState;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRemainBlood() {
		return remainBlood;
	}

	public void setRemainBlood(int remainBlood) {
		this.remainBlood = remainBlood;
	}

	public int getJoinPeopleNum() {
		return joinPeopleNum;
	}

	public void setJoinPeopleNum(int joinPeopleNum) {
		this.joinPeopleNum = joinPeopleNum;
	}

	public int getTotalBlood() {
		return totalBlood;
	}

	public void setTotalBlood(int totalBlood) {
		this.totalBlood = totalBlood;
	}

	public long getKillerId() {
		return killerId;
	}

	public void setKillerId(long killerId) {
		this.killerId = killerId;
	}

}
