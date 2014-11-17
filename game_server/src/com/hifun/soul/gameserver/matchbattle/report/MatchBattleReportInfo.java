package com.hifun.soul.gameserver.matchbattle.report;

/**
 * 匹配战战报信息
 * 
 * @author magicstone
 *
 */
public class MatchBattleReportInfo {
	private boolean isTerminate;
	private int coinReward;
	private int honourReward;
	private int lastConsecutiveWinCount;
	public boolean isTerminate() {
		return isTerminate;
	}
	public void setTerminate(boolean isTerminate) {
		this.isTerminate = isTerminate;
	}
	public int getCoinReward() {
		return coinReward;
	}
	public void setCoinReward(int coinReward) {
		this.coinReward = coinReward;
	}
	public int getHonourReward() {
		return honourReward;
	}
	public void setHonourReward(int honourReward) {
		this.honourReward = honourReward;
	}
	public int getLastConsecutiveWinCount() {
		return lastConsecutiveWinCount;
	}
	public void setLastConsecutiveWinCount(int lastConsecutiveWinCount) {
		this.lastConsecutiveWinCount = lastConsecutiveWinCount;
	}
	
}
