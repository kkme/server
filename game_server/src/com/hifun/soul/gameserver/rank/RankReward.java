package com.hifun.soul.gameserver.rank;

public class RankReward {
	protected RankType rankType;
	protected boolean isGotReward;
	
	public RankReward(RankType rankType,boolean isGotReward){
		this.rankType = rankType;
		this.isGotReward = false;
	}
	
	public RankType getRankType() {
		return rankType;
	}
	public void setRankType(RankType rankType) {
		this.rankType = rankType;
	}
	public boolean isGotReward() {
		return isGotReward;
	}
	public void setGotReward(boolean isGotReward) {
		this.isGotReward = isGotReward;
	}

}
