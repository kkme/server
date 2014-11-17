package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.escort.EscortHelp;
import com.hifun.soul.gameserver.escort.EscortRobRank;
import com.hifun.soul.gameserver.escort.enums.EscortRewardState;
import com.hifun.soul.gameserver.escort.enums.EscortRewardType;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.escort.info.RobRankInfo;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGEscortGetReward;
import com.hifun.soul.gameserver.escort.msg.GCEscortGetReward;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;

/**
 * 领取押运系统的奖励
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortGetRewardHandler implements
		IMessageHandlerWithType<CGEscortGetReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_GET_REWARD;
	}

	@Override
	public void execute(CGEscortGetReward message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		EscortRewardType rewardType = EscortRewardType.indexOf(message
				.getRewardType());
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		switch (rewardType) {
		// 押运奖励
		case ESCORT_REWARD:
			if (!globalEscortManager.hasEscortReward(human)) {
				return;
			}
			EscortInfo escortInfo = globalEscortManager.getEscortInfo(human
					.getHumanGuid());
			if (escortInfo == null) {
				return;
			}
			int escortReward = escortInfo.getEscortRewardCoin();
			// 军团科技加成押运奖励
			escortReward = human.getHumanLegionManager()
					.getLegionTechnologyAmend(LegionTechnologyType.ESCORT,
							escortReward);
			human.getWallet().addMoney(CurrencyType.COIN, escortReward, true,
					MoneyLogReason.ESCORT_REWARD, "");
			escortInfo.setEscortRewardState(EscortRewardState.GOT_REWARD
					.getIndex());
			globalEscortManager.updateEscortInfo(escortInfo);
			break;
		// 护送奖励
		case HELP_REWARD:
			if (!globalEscortManager.hasHelpReward(human)) {
				return;
			}
			EscortHelp help = globalEscortManager.getHelpInfo(human
					.getHumanGuid());
			if (help == null) {
				return;
			}
			int helpReward = help.getRewardCoin();
			human.getWallet().addMoney(CurrencyType.COIN, helpReward, true,
					MoneyLogReason.ESCORT_HELP_REWARD, "");
			help.setRewardState(EscortRewardState.GOT_REWARD.getIndex());
			globalEscortManager.updateHelpInfo(help);
			break;
		// 拦截榜奖励
		case ROB_REWARD:
			if (!globalEscortManager.hasRobRankReward(human)) {
				return;
			}
			// 根据昨日拦截排名获得奖励
			RobRankInfo yesterdayRank = globalEscortManager
					.getYesterdayRobRank(human.getHumanGuid());
			human.getWallet().addMoney(CurrencyType.COIN,
					yesterdayRank.getRewardCoin(), true,
					MoneyLogReason.ESCORT_ROB_RANK_REWARD, "");
			EscortRobRank robRank = globalEscortManager.getSelfRobRank(human
					.getHumanGuid());
			robRank.setRewardState(EscortRewardState.GOT_REWARD.getIndex());
			globalEscortManager.updateRobRank(robRank);
			break;
		}
		GCEscortGetReward msg = new GCEscortGetReward();
		msg.setRewardType(rewardType.getIndex());
		msg.setHasReward(false);
		human.sendMessage(msg);
	}
}
