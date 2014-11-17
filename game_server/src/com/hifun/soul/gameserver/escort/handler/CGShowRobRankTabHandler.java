package com.hifun.soul.gameserver.escort.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortRobRank;
import com.hifun.soul.gameserver.escort.info.RobRankInfo;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGShowRobRankTab;
import com.hifun.soul.gameserver.escort.msg.GCShowRobRankTab;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 展示拦截榜页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowRobRankTabHandler implements
		IMessageHandlerWithType<CGShowRobRankTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_ROB_RANK_TAB;
	}

	@Override
	public void execute(CGShowRobRankTab message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		GCShowRobRankTab msg = new GCShowRobRankTab();
		// 今日排行榜数据
		List<RobRankInfo> todayList = globalEscortManager.getTodayRobRankList();
		msg.setTodayRanks(todayList.toArray(new RobRankInfo[0]));
		RobRankInfo todayRankInfo = globalEscortManager.getTodayRobRank(human
				.getHumanGuid());
		if (todayRankInfo != null) {// 如果上榜
			msg.setTodaySelfRobCoin(todayRankInfo.getRobCoin());
			msg.setTodaySelfRobRank(todayRankInfo.getRank());
		} else {// 如果未上榜
			EscortRobRank robRank = globalEscortManager.getSelfRobRank(human
					.getHumanGuid());
			if (robRank != null) {
				msg.setTodaySelfRobCoin(robRank.getTodayRobCoin());
			}
		}
		// 昨日排行榜数据
		List<RobRankInfo> yesterdayList = globalEscortManager
				.getYesterdayRobRankList();
		msg.setYesterdayRanks(yesterdayList.toArray(new RobRankInfo[0]));
		RobRankInfo yesterdayRankInfo = globalEscortManager
				.getYesterdayRobRank(human.getHumanGuid());
		if (yesterdayRankInfo != null) {// 如果上榜
			msg.setYesterdaySelfRobCoin(yesterdayRankInfo.getRobCoin());
			msg.setYesterdaySelfRobRank(yesterdayRankInfo.getRank());
		} else {// 如果未上榜
			EscortRobRank robRank = globalEscortManager.getSelfRobRank(human
					.getHumanGuid());
			if (robRank != null) {
				msg.setYesterdaySelfRobCoin(robRank.getYesterdayRobCoin());
			}
		}
		msg.setHasReward(globalEscortManager.hasRobRankReward(human));
		human.sendMessage(msg);
	}
}
