package com.hifun.soul.gameserver.escort.info;

/**
 * 拦截榜信息
 * 
 * @author yandajun
 * 
 */
public class RobRankInfo implements Comparable<RobRankInfo> {
	private long robberId;
	private int rank;
	private String robberName;
	private int robCoin;
	private int rewardCoin;

	public long getRobberId() {
		return robberId;
	}

	public void setRobberId(long robberId) {
		this.robberId = robberId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setRobberName(String robberName) {
		this.robberName = robberName;
	}

	public String getRobberName() {
		return robberName;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getRobCoin() {
		return robCoin;
	}

	public void setRobCoin(int robCoin) {
		this.robCoin = robCoin;
	}

	@Override
	public int compareTo(RobRankInfo o) {
		return o.getRobCoin() - this.robCoin;
	}

}
