package com.hifun.soul.gameserver.rank.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.rank.HumanHonorRank;
import com.hifun.soul.gameserver.rank.HumanLevelRank;
import com.hifun.soul.gameserver.rank.HumanRank;
import com.hifun.soul.gameserver.rank.HumanTitleRank;
import com.hifun.soul.gameserver.rank.HumanVipRank;
import com.hifun.soul.gameserver.rank.LegionLevelRank;
import com.hifun.soul.gameserver.rank.LegionMemberRank;
import com.hifun.soul.gameserver.rank.LegionRank;
import com.hifun.soul.gameserver.rank.Rank;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.rank.model.HumanHonorRankData;
import com.hifun.soul.gameserver.rank.model.HumanLevelRankData;
import com.hifun.soul.gameserver.rank.model.HumanRankData;
import com.hifun.soul.gameserver.rank.model.HumanTitleRankData;
import com.hifun.soul.gameserver.rank.model.HumanVipRankData;
import com.hifun.soul.gameserver.rank.model.LegionLevelRankData;
import com.hifun.soul.gameserver.rank.model.LegionMemberRankData;
import com.hifun.soul.gameserver.rank.model.LegionRankData;
import com.hifun.soul.gameserver.rank.model.RankData;

/**
 * 排行榜管理类
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class RankManager {
	private static Logger logger = Loggers.RANK_LOGGER;
	Map<Integer, Rank<?>> rankMap = new HashMap<Integer, Rank<?>>();

	public RankManager() {

	}

	/**
	 * 初始化所有的排行榜数据
	 */
	public void init() {
		/** 等级排行榜 */
		@SuppressWarnings("unchecked")
		Rank<HumanLevelRankData> levelRank = ApplicationContext
				.getApplicationContext().getBean(HumanLevelRank.class);
		levelRank.init();
		/** 军衔排行榜 */
		@SuppressWarnings("unchecked")
		Rank<HumanTitleRankData> titleRank = ApplicationContext
				.getApplicationContext().getBean(HumanTitleRank.class);
		titleRank.init();
		/** 荣誉值排行榜 */
		@SuppressWarnings("unchecked")
		Rank<HumanHonorRankData> honorRank = ApplicationContext
				.getApplicationContext().getBean(HumanHonorRank.class);
		honorRank.init();
		/** vip排行榜 */
		@SuppressWarnings("unchecked")
		Rank<HumanVipRankData> vipRank = ApplicationContext
				.getApplicationContext().getBean(HumanVipRank.class);
		vipRank.init();

		/** 军团等级排行榜 */
		@SuppressWarnings("unchecked")
		Rank<LegionLevelRankData> legionLevelRank = ApplicationContext
				.getApplicationContext().getBean(LegionLevelRank.class);
		legionLevelRank.init();

		/** 军团人数排行榜 */
		@SuppressWarnings("unchecked")
		Rank<LegionMemberRankData> legionMemberRank = ApplicationContext
				.getApplicationContext().getBean(LegionMemberRank.class);
		legionMemberRank.init();

		rankMap.put(RankType.HUMAN_LEVEL_RANK.getIndex(), levelRank);
		rankMap.put(RankType.HUMAN_TITLE_RANK.getIndex(), titleRank);
		rankMap.put(RankType.HUMAN_HONOR_RANK.getIndex(), honorRank);
		rankMap.put(RankType.HUMAN_VIP_RANK.getIndex(), vipRank);
		rankMap.put(RankType.LEGION_LEVEL_RANK.getIndex(), legionLevelRank);
		rankMap.put(RankType.LEGION_MEMBER_RANK.getIndex(), legionMemberRank);
	}

	/**
	 * 初始化排行榜数据(实时刷新排行榜，一开始初始化)
	 */
	public void start(IDBService dbService) {
		/** vip排行榜 */
		@SuppressWarnings("unchecked")
		Rank<HumanVipRankData> vipRank = ApplicationContext
				.getApplicationContext().getBean(HumanVipRank.class);
		vipRank.start(dbService);

		/** 军团等级排行榜 */
		@SuppressWarnings("unchecked")
		Rank<LegionLevelRankData> legionLevelRank = ApplicationContext
				.getApplicationContext().getBean(LegionLevelRank.class);
		legionLevelRank.start(dbService);

		/** 军团人数排行榜 */
		@SuppressWarnings("unchecked")
		Rank<LegionMemberRankData> legionMemberRank = ApplicationContext
				.getApplicationContext().getBean(LegionMemberRank.class);
		legionMemberRank.start(dbService);
	}

	/**
	 * 根据排行榜类型获取排行榜
	 * 
	 * @param rankType
	 * @return
	 */
	public Rank<?> getRank(RankType rankType) {
		return rankMap.get(rankType.getIndex());
	}

	/**
	 * 分页获取排行榜数据
	 * 
	 * @param rankType
	 *            排行榜类型
	 * @param pageIndex
	 *            排行榜页码索引
	 * @return
	 */
	public List<RankData> getRankData(RankType rankType, int pageIndex) {
		List<RankData> resultList = new ArrayList<RankData>();
		Rank<?> rank = rankMap.get(rankType.getIndex());
		int rankSize = rank.getRankList().size();
		if (rankSize == 0) {
			return resultList;
		}
		int pageSize = 0;
		switch (rankType) {
		case HUMAN_LEVEL_RANK:
			pageSize = SharedConstants.HUMAN_LEVEL_RANK_PAGE_SIZE;
			break;
		case HUMAN_TITLE_RANK:
			pageSize = SharedConstants.HUMAN_TITLE_RANK_PAGE_SIZE;
			break;
		case HUMAN_HONOR_RANK:
			pageSize = SharedConstants.HUMAN_HONOR_RANK_PAGE_SIZE;
			break;
		case HUMAN_VIP_RANK:
			pageSize = SharedConstants.HUMAN_VIP_RANK_PAGE_SIZE;
			break;
		case LEGION_LEVEL_RANK:
			pageSize = SharedConstants.HUMAN_VIP_RANK_PAGE_SIZE;
			break;
		case LEGION_MEMBER_RANK:
			pageSize = SharedConstants.HUMAN_VIP_RANK_PAGE_SIZE;
			break;
		}
		int fromIndex = pageIndex * pageSize;
		if (fromIndex >= rankSize) {
			logger.error("Illegal arguments by quering ranktype：" + rankType
					+ " pageIndex:" + pageIndex + " rankSize:" + rankSize);
			throw new IllegalArgumentException("page index is out of range");
		}
		int toIndex = fromIndex + pageSize;
		if (toIndex > rankSize) {
			toIndex = rankSize;
		}

		for (int i = fromIndex; i < toIndex; i++) {
			rank.getRankList().get(i).setRankPosition(i + 1);
			resultList.add(rank.getRankList().get(i));
		}
		return resultList;
	}

	/**
	 * 获取角色在排行榜中的名次
	 * 
	 * @param human
	 * @param rankType
	 * @return 不在榜中时返回-1
	 */
	public int getRankPosition(Human human, RankType rankType) {
		int rankPosition = -1;
		Rank<?> rank = getRank(rankType);
		// 如果是个人排行榜
		if (rank instanceof HumanRank) {
			@SuppressWarnings("unchecked")
			HumanRank<HumanRankData> humanRank = (HumanRank<HumanRankData>) rank;
			int rankSize = rank.getRankList().size();
			for (int i = 0; i < rankSize; i++) {
				if (humanRank.getRankList().get(i).getHumanGuid() == human
						.getHumanGuid()) {
					rankPosition = i + 1;
					break;
				}
			}
		}
		// 如果是军团排行榜
		if (rank instanceof LegionRank) {
			@SuppressWarnings("unchecked")
			LegionRank<LegionRankData> legionRank = (LegionRank<LegionRankData>) rank;
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(human.getHumanGuid());
			if (legion == null) {
				rankPosition = -1;
				return rankPosition;
			}
			int rankSize = rank.getRankList().size();
			for (int i = 0; i < rankSize; i++) {
				if (legionRank.getRankList().get(i).getLegionId() == legion
						.getId()) {
					rankPosition = i + 1;
					break;
				}
			}
		}
		return rankPosition;
	}

	/**
	 * 刷新排行榜数据
	 * 
	 * @param rankType
	 */
	public void refreshRankData(RankType rankType) {
		Rank<?> rank = rankMap.get(rankType.getIndex());
		if (rank != null) {
			rank.refreshDataList();
		}
	}

}
