package com.hifun.soul.gameserver.rank.model;

public class MatchBattleRankData extends HumanRankData {
	private int maxStreakWinCount;
	private int occupation;
	private int level;
	public int getOccupation() {
		return occupation;
	}
	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMaxStreakWinCount() {
		return maxStreakWinCount;
	}

	public void setMaxStreakWinCount(int maxStreakWinCount) {
		this.maxStreakWinCount = maxStreakWinCount;
	}
	
}
