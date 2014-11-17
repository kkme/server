package com.hifun.soul.gameserver.rank;

import java.util.List;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.rank.model.HumanRankData;

public abstract class HumanRank<T extends HumanRankData> extends Rank<T> {

	/**
	 * 为个人排行榜数据设置军团ID、名称
	 */
	@Override
	public List<T> getRankList() {
		for (T rankData : rankList) {
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(rankData.getHumanGuid());
			if (legion != null) {
				rankData.setLegionId(legion.getId());
				rankData.setLegionName(legion.getLegionName());
			}
		}
		return rankList;
	}

}
