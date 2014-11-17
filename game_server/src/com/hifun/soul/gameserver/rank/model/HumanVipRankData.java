package com.hifun.soul.gameserver.rank.model;

import com.hifun.soul.gamedb.entity.VipRankEntity;

public class HumanVipRankData extends HumanRankData implements
		Comparable<HumanVipRankData> {
	/** 荣誉值 */
	private int vipLevel;

	public HumanVipRankData() {
		super();
	}

	public HumanVipRankData(VipRankEntity entity) {
		super(entity);
		this.vipLevel = entity.getVipLevel();
	}

	@Override
	public int compareTo(HumanVipRankData vipRankData) {
		if (vipRankData.getVipLevel() > this.vipLevel) {
			return 1;
		} else if (vipRankData.getVipLevel() < this.vipLevel) {
			return -1;
		} else {
			return -(vipRankData.getHumanName().compareTo(this.getHumanName()));
		}
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getYellowVipLevel() {
		return yellowVipLevel;
	}

	public void setYellowVipLevel(int yellowVipLevel) {
		this.yellowVipLevel = yellowVipLevel;
	}

	public boolean getIsYearYellowVip() {
		return isYearYellowVip;
	}

	public void setIsYearYellowVip(boolean isYearYellowVip) {
		this.isYearYellowVip = isYearYellowVip;
	}

	public boolean getIsYellowHighVip() {
		return isYellowHighVip;
	}

	public void setIsYellowHighVip(boolean isYellowHighVip) {
		this.isYellowHighVip = isYellowHighVip;
	}
}
