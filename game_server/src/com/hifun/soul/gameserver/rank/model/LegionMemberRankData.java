package com.hifun.soul.gameserver.rank.model;

public class LegionMemberRankData extends LegionRankData implements
		Comparable<LegionMemberRankData> {

	@Override
	public int compareTo(LegionMemberRankData o) {
		return Integer.valueOf(o.getMemberNum()).compareTo(
				Integer.valueOf(this.memberNum));
	}

}
