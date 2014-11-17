package com.hifun.soul.gameserver.escort.info;

/**
 * 押运怪物信息
 * 
 * @author yandajun
 * 
 */
public class EscortMonsterInfo {
	private int escortTime;
	private int monsterType;
	private int escortRewardCoin;
	private int monsterIconId;
	private String monsterName;

	public int getEscortTime() {
		return escortTime;
	}

	public void setEscortTime(int escortTime) {
		this.escortTime = escortTime;
	}

	public int getMonsterType() {
		return monsterType;
	}

	public void setMonsterType(int monsterType) {
		this.monsterType = monsterType;
	}

	public int getEscortRewardCoin() {
		return escortRewardCoin;
	}

	public void setEscortRewardCoin(int escortRewardCoin) {
		this.escortRewardCoin = escortRewardCoin;
	}

	public int getMonsterIconId() {
		return monsterIconId;
	}

	public void setMonsterIconId(int monsterIconId) {
		this.monsterIconId = monsterIconId;
	}

	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

}
