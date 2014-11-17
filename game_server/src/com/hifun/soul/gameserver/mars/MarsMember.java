package com.hifun.soul.gameserver.mars;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.faction.GlobalFactionManager;

public class MarsMember {
	private long humanId;
	private String humanName;
	private int humanLevel;
	private int occupation;
	private int todayKillValue;
	private int totalKillValue;
	private int rewardCoin;
	private int rewardPrestige;
	private int rewardState;

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getHumanLevel() {
		return humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public int getTodayKillValue() {
		return todayKillValue;
	}

	public void setTodayKillValue(int todayKillValue) {
		this.todayKillValue = todayKillValue;
	}

	public int getTotalKillValue() {
		return totalKillValue;
	}

	public void setTotalKillValue(int totalKillValue) {
		this.totalKillValue = totalKillValue;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getRewardPrestige() {
		return rewardPrestige;
	}

	public void setRewardPrestige(int rewardPrestige) {
		this.rewardPrestige = rewardPrestige;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}

	/**
	 * 获取玩家所在阵营
	 */
	public int getFaction() {
		GlobalFactionManager factionManager = GameServerAssist
				.getGlobalFactionManager();
		if (factionManager.areadyJoinFaction(humanId)) {
			return factionManager.getFactionTypeByHuman(humanId).getIndex();
		}
		return 0;
	}
}
