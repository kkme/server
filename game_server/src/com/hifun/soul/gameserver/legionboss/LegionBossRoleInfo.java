package com.hifun.soul.gameserver.legionboss;

public class LegionBossRoleInfo {

	private long humanGuid;

	private int rank;

	private String name;

	private int damage;

	private int chargedstrikeRate;

	private int encourageRate;

	private boolean hasDamageReward;

	private boolean hasKillReward;

	private boolean hasRankReward;

	private boolean isJoin;

	private int stageReward;

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getChargedstrikeRate() {
		return chargedstrikeRate;
	}

	public void setChargedstrikeRate(int chargedstrikeRate) {
		this.chargedstrikeRate = chargedstrikeRate;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean getHasDamageReward() {
		return hasDamageReward;
	}

	public void setHasDamageReward(boolean hasDamageReward) {
		this.hasDamageReward = hasDamageReward;
	}

	public boolean getHasKillReward() {
		return hasKillReward;
	}

	public void setHasKillReward(boolean hasKillReward) {
		this.hasKillReward = hasKillReward;
	}

	public boolean getHasRankReward() {
		return hasRankReward;
	}

	public void setHasRankReward(boolean hasRankReward) {
		this.hasRankReward = hasRankReward;
	}

	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public int getStageReward() {
		return stageReward;
	}

	public void setStageReward(int stageReward) {
		this.stageReward = stageReward;
	}
}
