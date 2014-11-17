package com.hifun.soul.gameserver.levy;

/**
 * 攻城怪物信息
 * 
 * @author yandajun
 * 
 */
public class MainCityMonsterInfo {
	private int monsterId;
	private int monsterLevel;
	private String monsterName;
	private int totalNum;
	private int remainNum;
	private int rewardCoin;
	private int rewardExperience;

	public int getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}

	public int getMonsterLevel() {
		return monsterLevel;
	}

	public void setMonsterLevel(int monsterLevel) {
		this.monsterLevel = monsterLevel;
	}

	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getRewardExperience() {
		return rewardExperience;
	}

	public void setRewardExperience(int rewardExperience) {
		this.rewardExperience = rewardExperience;
	}
}
