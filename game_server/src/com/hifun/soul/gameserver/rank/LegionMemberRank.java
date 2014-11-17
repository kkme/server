package com.hifun.soul.gameserver.rank;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.rank.model.LegionMemberRankData;

@Component
public class LegionMemberRank<T> extends LegionRank<LegionMemberRankData>
		implements ICachableComponent, Sortable {

	@Override
	protected RankType getType() {
		return RankType.LEGION_MEMBER_RANK;
	}

	/**
	 * 初始化排行榜数据
	 */
	@Override
	public void initDataList(IDBService dbService) {
		List<Legion> legionList = GameServerAssist.getGlobalLegionManager()
				.getLegionList();
		if (legionList == null) {
			return;
		}
		rankList.clear();
		for (Legion legion : legionList) {
			LegionMemberRankData rankData = legionToRankData(legion);
			rankList.add(rankData);
		}
		sort();
	}

	@Override
	protected LegionMemberRankData legionToRankData(Legion legion) {
		LegionMemberRankData rankData = new LegionMemberRankData();
		rankData.setLegionId(legion.getId());
		rankData.setLegionName(legion.getLegionName());
		rankData.setLegionLevel(legion.getLegionLevel());
		rankData.setMemberNum(legion.getMemberNum());
		rankData.setCommanderName(legion.getCommanderName());
		return rankData;
	}

	@Override
	public void sort() {
		Collections.sort(rankList);
	}
}
