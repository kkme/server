package com.hifun.soul.gameserver.target.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.target.TargetRewardState;
import com.hifun.soul.gameserver.target.msg.CGGetTargetReward;
import com.hifun.soul.gameserver.target.msg.GCGetTargetReward;
import com.hifun.soul.gameserver.target.template.TargetReward;
import com.hifun.soul.gameserver.target.template.TargetTemplate;

@Component
public class CGGetTargetRewardHandler implements
		IMessageHandlerWithType<CGGetTargetReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_TARGET_REWARD;
	}

	@Override
	public void execute(CGGetTargetReward message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.TARGET, true)) {
			return;
		}
		// 校验是否可以领奖
		int rewardState = human.getHumanTargetManager().getRewardState(
				message.getTargetId());
		if (rewardState == TargetRewardState.NOT_REACH.getIndex()) {
			return;
		} else if (rewardState == TargetRewardState.FINISHED.getIndex()) {
			return;
		}
		// 判断背包是否已满
		HumanBagManager bagManager = human.getBagManager();
		if (bagManager.isFull(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 领奖
		TargetTemplate template = GameServerAssist.getTargetTemplateManager()
				.getTargetTemplate(message.getTargetId());
		if (template == null) {
			return;
		}
		TargetReward[] targetRewards = template.getTargetRewards();
		for (TargetReward reward : targetRewards) {
			if (!human.getBagManager().putItems(BagType.MAIN_BAG,
					reward.getItemId(), reward.getItemNum(), true,
					ItemLogReason.TARGET_REACH_REWARD, "")) {
				return;
			}
		}
		human.getHumanTargetManager().updateRewardState(message.getTargetId(),
				TargetRewardState.FINISHED.getIndex());
		GCGetTargetReward msg = new GCGetTargetReward();
		msg.setTargetId(message.getTargetId());
		msg.setRewardState(TargetRewardState.FINISHED.getIndex());
		msg.setIsCompletedAll(human.getHumanTargetManager()
				.isAllTargetsCompleted());
		human.sendMessage(msg);
	}
}
