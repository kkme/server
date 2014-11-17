package com.hifun.soul.gameserver.rank.model;

public class LegionLevelRankData extends LegionRankData implements
		Comparable<LegionLevelRankData> {

	@Override
	public int compareTo(LegionLevelRankData o) {
		return Integer.valueOf(o.getLegionLevel()).compareTo(
				Integer.valueOf(this.legionLevel));
	}

}
