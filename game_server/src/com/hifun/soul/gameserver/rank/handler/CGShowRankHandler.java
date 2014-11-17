package com.hifun.soul.gameserver.rank.handler;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.msg.GCShowArenaRankList;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.rank.Rank;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.rank.manager.RankManager;
import com.hifun.soul.gameserver.rank.model.HumanHonorRankData;
import com.hifun.soul.gameserver.rank.model.HumanLevelRankData;
import com.hifun.soul.gameserver.rank.model.HumanTitleRankData;
import com.hifun.soul.gameserver.rank.model.HumanVipRankData;
import com.hifun.soul.gameserver.rank.model.LegionLevelRankData;
import com.hifun.soul.gameserver.rank.model.LegionMemberRankData;
import com.hifun.soul.gameserver.rank.model.RankData;
import com.hifun.soul.gameserver.rank.msg.CGShowRank;
import com.hifun.soul.gameserver.rank.msg.GCShowHonorRank;
import com.hifun.soul.gameserver.rank.msg.GCShowLegionLevelRank;
import com.hifun.soul.gameserver.rank.msg.GCShowLegionMemberRank;
import com.hifun.soul.gameserver.rank.msg.GCShowLevelRank;
import com.hifun.soul.gameserver.rank.msg.GCShowTitleRank;
import com.hifun.soul.gameserver.rank.msg.GCShowVipRank;
import com.hifun.soul.gameserver.tencent.TencentUserInfo;

/**
 * 请求显示排行榜处理类
 * 
 * @author magicstone
 * 
 */
@Component
public class CGShowRankHandler implements IMessageHandlerWithType<CGShowRank> {
	private static Logger logger = Loggers.RANK_LOGGER;

	@Autowired
	private RankManager rankManager;
	@Autowired
	private ArenaService arenaService;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_RANK;
	}

	@Override
	public void execute(CGShowRank message) {
		RankType rankType = RankType.indexOf(message.getRankType());
		switch (rankType) {
		case HUMAN_LEVEL_RANK:
			this.showLevelRank(message);
			break;
		case HUMAN_TITLE_RANK:
			this.showTitleRank(message);
			break;
		case ARENA_RANK:
			this.showArenaRank(message);
			break;
		case HUMAN_HONOR_RANK:
			this.showHonorRank(message);
			break;
		case HUMAN_VIP_RANK:
			this.showVipRank(message);
			break;
		case LEGION_LEVEL_RANK:
			this.showLegionLevelRank(message);
			break;
		case LEGION_MEMBER_RANK:
			this.showLegionMemberRank(message);
			break;
		}
	}

	/**
	 * 展示等级排行榜
	 * 
	 * @param rankType
	 */
	private void showLevelRank(CGShowRank message) {
		Player player = message.getPlayer();
		RankType rankType = RankType.indexOf(message.getRankType());
		Rank<?> rank = rankManager.getRank(rankType);
		if (rank == null) {
			logger.error("Not found RankType='" + rankType);
			throw new IllegalArgumentException("rankType error.");
		}
		List<RankData> rankList = rankManager.getRankData(rankType,
				message.getRankPageIndex());
		GCShowLevelRank gcMsg = new GCShowLevelRank();
		gcMsg.setHumanRankPosition(rankManager.getRankPosition(
				player.getHuman(), rankType));
		gcMsg.setRankSize(rank.getSize());
		HumanLevelRankData[] humanLevelRankData = new HumanLevelRankData[rankList
				.size()];
		for (int i = 0; i < rankList.size(); i++) {
			humanLevelRankData[i] = (HumanLevelRankData) rankList.get(i);
			int title = humanLevelRankData[i].getTitle();
			String titleName = GameServerAssist.getTitleTemplateManager()
					.getTitleName(title);
			humanLevelRankData[i].setTitleName(titleName);
			TencentUserInfo txUserInfo = GameServerAssist
					.getTencentUserInfoManager().getTencentUserInfo(
							humanLevelRankData[i].getHumanGuid());
			if (txUserInfo != null) {
				humanLevelRankData[i].setYellowVipLevel(txUserInfo
						.getYellowVipLevel());
				humanLevelRankData[i].setIsYearYellowVip(txUserInfo
						.getIsYearYellowVip());
				humanLevelRankData[i].setIsYellowHighVip(txUserInfo
						.getIsYellowHighVip());
			} else {
				humanLevelRankData[i].setYellowVipLevel(0);
				humanLevelRankData[i].setIsYearYellowVip(false);
				humanLevelRankData[i].setIsYellowHighVip(false);
			}
		}
		gcMsg.setRankDatas(humanLevelRankData);
		player.sendMessage(gcMsg);
	}

	/**
	 * 展示军衔排行榜
	 * 
	 * @param message
	 */
	private void showTitleRank(CGShowRank message) {
		Player player = message.getPlayer();
		RankType rankType = RankType.indexOf(message.getRankType());
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(player.getHuman(),
				GameFuncType.TITLE, true)) {
			return;
		}
		Rank<?> titleRank = rankManager.getRank(rankType);
		if (titleRank == null) {
			logger.error("Not found RankType='" + rankType);
			throw new IllegalArgumentException("rankType error.");
		}
		List<RankData> titleRankList = rankManager.getRankData(rankType,
				message.getRankPageIndex());
		GCShowTitleRank gcShowTitleMsg = new GCShowTitleRank();
		gcShowTitleMsg.setHumanRankPosition(rankManager.getRankPosition(
				player.getHuman(), rankType));
		gcShowTitleMsg.setRankSize(titleRank.getSize());
		HumanTitleRankData[] humanTitleRankData = new HumanTitleRankData[titleRankList
				.size()];
		for (int i = 0; i < titleRankList.size(); i++) {
			humanTitleRankData[i] = (HumanTitleRankData) titleRankList.get(i);
			int title = humanTitleRankData[i].getTitle();
			String titleName = GameServerAssist.getTitleTemplateManager()
					.getTitleName(title);
			humanTitleRankData[i].setTitleName(titleName);
			TencentUserInfo txUserInfo = GameServerAssist
					.getTencentUserInfoManager().getTencentUserInfo(
							humanTitleRankData[i].getHumanGuid());
			if (txUserInfo != null) {
				humanTitleRankData[i].setYellowVipLevel(txUserInfo
						.getYellowVipLevel());
				humanTitleRankData[i].setIsYearYellowVip(txUserInfo
						.getIsYearYellowVip());
				humanTitleRankData[i].setIsYellowHighVip(txUserInfo
						.getIsYellowHighVip());
			} else {
				humanTitleRankData[i].setYellowVipLevel(0);
				humanTitleRankData[i].setIsYearYellowVip(false);
				humanTitleRankData[i].setIsYellowHighVip(false);
			}
		}
		gcShowTitleMsg.setRankDatas(humanTitleRankData);
		player.sendMessage(gcShowTitleMsg);
	}

	/**
	 * 展示竞技场排行榜
	 * 
	 * @param message
	 */
	private void showArenaRank(CGShowRank message) {
		Player player = message.getPlayer();
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(player.getHuman(),
				GameFuncType.ARENA_RANK, true)) {
			return;
		}
		GCShowArenaRankList gcShowArenaRankListMsg = new GCShowArenaRankList();
		gcShowArenaRankListMsg.setTotalSize(arenaService.getTotalSize());
		ArenaMember[] arenaMembers = new ArenaMember[0];
		arenaMembers = arenaService.getArenaRankArenaMembers(
				message.getRankPageIndex()).toArray(arenaMembers);
		// 军团名称，军团ID
		for (ArenaMember member : arenaMembers) {
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(member.getRoleId());
			if (legion != null) {
				member.setLegionId(legion.getId());
				member.setLegionName(legion.getLegionName());
			}
		}
		gcShowArenaRankListMsg.setArenaMembers(arenaMembers);
		gcShowArenaRankListMsg.setRankPosition(arenaService.getArenaRank(player
				.getHuman().getHumanGuid()));
		ArenaMember arenaMember = arenaService.getArenaMember(player.getHuman()
				.getHumanGuid());
		if (arenaMember == null) {
			gcShowArenaRankListMsg.setRankPosition(-1);
		} else {
			gcShowArenaRankListMsg.setRankPosition(arenaMember.getRank());
		}
		player.sendMessage(gcShowArenaRankListMsg);
	}

	/**
	 * 展示荣誉值排行榜
	 */
	private void showHonorRank(CGShowRank message) {
		Player player = message.getPlayer();
		RankType rankType = RankType.indexOf(message.getRankType());
		Rank<?> rank = rankManager.getRank(rankType);
		if (rank == null) {
			logger.error("Not found RankType='" + rankType);
			throw new IllegalArgumentException("rankType error.");
		}
		List<RankData> rankList = rankManager.getRankData(rankType,
				message.getRankPageIndex());
		GCShowHonorRank gcMsg = new GCShowHonorRank();
		gcMsg.setHumanRankPosition(rankManager.getRankPosition(
				player.getHuman(), rankType));
		gcMsg.setRankSize(rank.getSize());
		HumanHonorRankData[] humanHonorData = new HumanHonorRankData[rankList
				.size()];
		for (int i = 0; i < rankList.size(); i++) {
			humanHonorData[i] = (HumanHonorRankData) rankList.get(i);
			TencentUserInfo txUserInfo = GameServerAssist
					.getTencentUserInfoManager().getTencentUserInfo(
							humanHonorData[i].getHumanGuid());
			if (txUserInfo != null) {
				humanHonorData[i].setYellowVipLevel(txUserInfo
						.getYellowVipLevel());
				humanHonorData[i].setIsYearYellowVip(txUserInfo
						.getIsYearYellowVip());
				humanHonorData[i].setIsYellowHighVip(txUserInfo
						.getIsYellowHighVip());
			} else {
				humanHonorData[i].setYellowVipLevel(0);
				humanHonorData[i].setIsYearYellowVip(false);
				humanHonorData[i].setIsYellowHighVip(false);
			}
		}
		gcMsg.setRankDatas(humanHonorData);
		player.sendMessage(gcMsg);
	}

	/**
	 * 展示VIP排行榜
	 */
	private void showVipRank(CGShowRank message) {
		Player player = message.getPlayer();
		RankType rankType = RankType.indexOf(message.getRankType());
		Rank<?> rank = rankManager.getRank(rankType);
		if (rank == null) {
			logger.error("Not found RankType='" + rankType);
			throw new IllegalArgumentException("rankType error.");
		}
		List<RankData> rankList = rankManager.getRankData(rankType,
				message.getRankPageIndex());
		GCShowVipRank gcMsg = new GCShowVipRank();
		gcMsg.setHumanRankPosition(rankManager.getRankPosition(
				player.getHuman(), rankType));
		gcMsg.setRankSize(rank.getSize());
		HumanVipRankData[] humanVipData = new HumanVipRankData[rankList.size()];
		for (int i = 0; i < rankList.size(); i++) {
			humanVipData[i] = (HumanVipRankData) rankList.get(i);
			TencentUserInfo txUserInfo = GameServerAssist
					.getTencentUserInfoManager().getTencentUserInfo(
							humanVipData[i].getHumanGuid());
			if (txUserInfo != null) {
				humanVipData[i].setYellowVipLevel(txUserInfo
						.getYellowVipLevel());
				humanVipData[i].setIsYearYellowVip(txUserInfo
						.getIsYearYellowVip());
				humanVipData[i].setIsYellowHighVip(txUserInfo
						.getIsYellowHighVip());
			} else {
				humanVipData[i].setYellowVipLevel(0);
				humanVipData[i].setIsYearYellowVip(false);
				humanVipData[i].setIsYellowHighVip(false);
			}
		}
		gcMsg.setRankDatas(humanVipData);
		player.sendMessage(gcMsg);
	}

	/**
	 * 展示军团等级排行榜
	 */
	private void showLegionLevelRank(CGShowRank message) {
		Player player = message.getPlayer();
		RankType rankType = RankType.indexOf(message.getRankType());
		Rank<?> rank = rankManager.getRank(rankType);
		if (rank == null) {
			logger.error("Not found RankType='" + rankType);
			throw new IllegalArgumentException("rankType error.");
		}
		List<RankData> rankList = rankManager.getRankData(rankType,
				message.getRankPageIndex());
		GCShowLegionLevelRank gcMsg = new GCShowLegionLevelRank();
		gcMsg.setHumanRankPosition(rankManager.getRankPosition(
				player.getHuman(), rankType));
		gcMsg.setRankSize(rank.getSize());
		LegionLevelRankData[] legionLevelData = new LegionLevelRankData[rankList
				.size()];
		gcMsg.setRankDatas(rankList.toArray(legionLevelData));
		player.sendMessage(gcMsg);

	}

	/**
	 * 展示军团人数排行榜
	 */
	private void showLegionMemberRank(CGShowRank message) {
		Player player = message.getPlayer();
		RankType rankType = RankType.indexOf(message.getRankType());
		Rank<?> rank = rankManager.getRank(rankType);
		if (rank == null) {
			logger.error("Not found RankType='" + rankType);
			throw new IllegalArgumentException("rankType error.");
		}
		List<RankData> rankList = rankManager.getRankData(rankType,
				message.getRankPageIndex());
		GCShowLegionMemberRank gcMsg = new GCShowLegionMemberRank();
		gcMsg.setHumanRankPosition(rankManager.getRankPosition(
				player.getHuman(), rankType));
		gcMsg.setRankSize(rank.getSize());
		LegionMemberRankData[] legionMemberData = new LegionMemberRankData[rankList
				.size()];
		gcMsg.setRankDatas(rankList.toArray(legionMemberData));
		player.sendMessage(gcMsg);

	}
}
