package com.hifun.soul.gameserver.recharge.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.FirstRechargeState;
import com.hifun.soul.gameserver.recharge.msg.CGGetFirstRechargeReward;

@Component
public class CGGetFirstRechargeRewardHandler implements
		IMessageHandlerWithType<CGGetFirstRechargeReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_FIRST_RECHARGE_REWARD;
	}

	@Override
	public void execute(CGGetFirstRechargeReward message) {
		Human human = message.getPlayer().getHuman();
		// 校验首充状态
		if (human.getFirstRechargeState() == FirstRechargeState.NO_RECHARGE
				.getIndex()) {
			human.sendErrorMessage(LangConstants.NO_FIRST_RECHARGE);
			return;
		}
		if (human.getFirstRechargeState() == FirstRechargeState.FINISHED
				.getIndex()) {
			human.sendErrorMessage(LangConstants.FIRST_RECHARGE_FINISHED);
			return;
		}
		// 判断背包是否已满
		HumanBagManager bagManager = human.getBagManager();
		if (bagManager.isFull(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		human.getHumanRechargeManager().getFirstRechargeReward();
	}

}
