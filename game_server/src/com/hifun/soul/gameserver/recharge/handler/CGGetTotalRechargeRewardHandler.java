package com.hifun.soul.gameserver.recharge.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.msg.CGGetTotalRechargeReward;

@Component
public class CGGetTotalRechargeRewardHandler implements
		IMessageHandlerWithType<CGGetTotalRechargeReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_TOTAL_RECHARGE_REWARD;
	}

	@Override
	public void execute(CGGetTotalRechargeReward message) {
		Human human = message.getPlayer().getHuman();
		// 判断背包是否已满
		HumanBagManager bagManager = human.getBagManager();
		if (bagManager.isFull(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		human.getHumanRechargeManager().getTotalRechargeReward(
				message.getGradeId());
	}

}
