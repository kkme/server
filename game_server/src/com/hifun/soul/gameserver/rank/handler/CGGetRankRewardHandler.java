package com.hifun.soul.gameserver.rank.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rank.Rank;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.rank.manager.RankManager;
import com.hifun.soul.gameserver.rank.msg.CGGetRankReward;

@Component
public class CGGetRankRewardHandler implements IMessageHandlerWithType<CGGetRankReward> {
	private static Logger logger = Loggers.RANK_LOGGER;
	@Autowired
	RankManager rankManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_RANK_REWARD;
	}

	@Override
	public void execute(CGGetRankReward message) {
		Human human = message.getPlayer().getHuman();
		RankType rankType = RankType.indexOf(message.getRankType());		
		Rank<?> rank = rankManager.getRank(rankType);
		if (rank == null) {
			logger.error("没有找到对应的排行榜 rankType:" + rankType.toString());
			return;
		}
		if (rank.hasReward()) {
			receiveRankReward(human, rankType);
		}
	}

	/**
	 * 领取排行榜奖励
	 * 
	 * @param rankType
	 */
	private void receiveRankReward(Human human, RankType rankType) {
		// TODO: 需要的时候自己实现
	}

}
