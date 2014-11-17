package com.hifun.soul.gameserver.matchbattle.Comparator;

import java.util.Comparator;

import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo;

public class MatchBattleRankRoleInfoComparator implements Comparator<MatchBattleRankRoleInfo> {

	@Override
	public int compare(MatchBattleRankRoleInfo o1, MatchBattleRankRoleInfo o2) {
		return o2.getConsecutiveWinCount() - o1.getConsecutiveWinCount();
	}
}
