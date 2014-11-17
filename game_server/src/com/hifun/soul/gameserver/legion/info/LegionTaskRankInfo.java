package com.hifun.soul.gameserver.legion.info;

/**
 * 军团任务排行信息
 * 
 * @author yandajun
 * 
 */
public class LegionTaskRankInfo implements Comparable<LegionTaskRankInfo> {
	private int rank;
	private String humanName;
	private int humanLevel;
	private int score;
	private int rewardMedal;

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHumanLevel() {
		return humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}

	public int getRewardMedal() {
		return rewardMedal;
	}

	public void setRewardMedal(int rewardMedal) {
		this.rewardMedal = rewardMedal;
	}

	@Override
	public int compareTo(LegionTaskRankInfo o) {
		return this.rank - o.getRank();
	}

}
