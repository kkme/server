package com.hifun.soul.gameserver.matchbattle.Comparator;

import java.util.Comparator;

import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;

public class MatchBattleRoleComparator implements Comparator<MatchBattleRole> {

	@Override
	public int compare(MatchBattleRole o1, MatchBattleRole o2) {
		if(o2.getMaxConsecutiveWinCount() == o1.getMaxConsecutiveWinCount()){
			return o1.getMaxConsecutiveWinTimeStamp()-o2.getMaxConsecutiveWinTimeStamp()>0?1:-1;
		}
		return o2.getMaxConsecutiveWinCount() - o1.getMaxConsecutiveWinCount();
	}

}
