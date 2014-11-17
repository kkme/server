package com.hifun.soul.gameserver.rank;

import java.util.List;
import com.hifun.soul.gameserver.rank.model.MatchBattleRankData;

/**
 * 匹配战排行
 * 
 * @author magicstone
 * 
 */
public class MatchBattleRank extends HumanRank<MatchBattleRankData> {

	@Override
	protected RankType getType() {
		return RankType.MATCH_BATTLE_STREAK_WIN_RANK;
	}

	@Override
	public void refreshDataList() {
		//load from db  策划暂时没有要求显示该排行
		
	}

	public void updateRankData(List<MatchBattleRankData> rankDataList) {
		this.rankList = rankDataList;
	}

}
