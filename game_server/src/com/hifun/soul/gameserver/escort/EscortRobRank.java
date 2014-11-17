package com.hifun.soul.gameserver.escort;

/**
 * 押运拦截排行
 * 
 * @author yandajun
 * 
 */
public class EscortRobRank {
	private long robberId;
	private String robberName;
	private int yesterdayRobCoin;
	private int todayRobCoin;
	private int rewardState;

	public long getRobberId() {
		return robberId;
	}

	public void setRobberId(long robberId) {
		this.robberId = robberId;
	}

	public String getRobberName() {
		return robberName;
	}

	public void setRobberName(String robberName) {
		this.robberName = robberName;
	}

	public int getYesterdayRobCoin() {
		return yesterdayRobCoin;
	}

	public void setYesterdayRobCoin(int yesterdayRobCoin) {
		this.yesterdayRobCoin = yesterdayRobCoin;
	}

	public int getTodayRobCoin() {
		return todayRobCoin;
	}

	public void setTodayRobCoin(int todayRobCoin) {
		this.todayRobCoin = todayRobCoin;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}
}
