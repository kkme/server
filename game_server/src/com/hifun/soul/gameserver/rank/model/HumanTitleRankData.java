package com.hifun.soul.gameserver.rank.model;

import com.hifun.soul.gamedb.entity.TitleRankEntity;

public class HumanTitleRankData extends HumanRankData {

	public HumanTitleRankData() {
		super();
	}

	public HumanTitleRankData(TitleRankEntity entity) {
		super(entity);
		this.title = entity.getTitle();
	}

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
