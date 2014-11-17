package com.hifun.soul.gameserver.mars.msg.info;

/**
 * 战神之巅个人信息
 * 
 * @author yandajun
 * 
 */
public class MarsSelfInfo {
	private int remainKillNum;
	private int totalKillNum;
	private int remainMultipleNum;
	private int buyMultipleNumCost;
	private int totalMultipleNum;
	private int todayKillValue;
	private int rewardCoin;

	public int getRemainKillNum() {
		return remainKillNum;
	}

	public void setRemainKillNum(int remainKillNum) {
		this.remainKillNum = remainKillNum;
	}

	public int getTotalKillNum() {
		return totalKillNum;
	}

	public void setTotalKillNum(int totalKillNum) {
		this.totalKillNum = totalKillNum;
	}

	public int getRemainMultipleNum() {
		return remainMultipleNum;
	}

	public void setRemainMultipleNum(int remainMultipleNum) {
		this.remainMultipleNum = remainMultipleNum;
	}

	public int getBuyMultipleNumCost() {
		return buyMultipleNumCost;
	}

	public void setBuyMultipleNumCost(int buyMultipleNumCost) {
		this.buyMultipleNumCost = buyMultipleNumCost;
	}

	public int getTotalMultipleNum() {
		return totalMultipleNum;
	}

	public void setTotalMultipleNum(int totalMultipleNum) {
		this.totalMultipleNum = totalMultipleNum;
	}

	public int getTodayKillValue() {
		return todayKillValue;
	}

	public void setTodayKillValue(int todayKillValue) {
		this.todayKillValue = todayKillValue;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

}
