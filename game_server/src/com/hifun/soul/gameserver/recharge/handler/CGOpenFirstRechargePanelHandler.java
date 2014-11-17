package com.hifun.soul.gameserver.recharge.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.FirstRechargeState;
import com.hifun.soul.gameserver.recharge.msg.CGOpenFirstRechargePanel;
import com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo;
import com.hifun.soul.gameserver.recharge.msg.GCOpenFirstRechargePanel;

@Component
public class CGOpenFirstRechargePanelHandler implements
		IMessageHandlerWithType<CGOpenFirstRechargePanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_FIRST_RECHARGE_PANEL;
	}

	@Override
	public void execute(CGOpenFirstRechargePanel message) {
		Human human = message.getPlayer().getHuman();
		// 校验首充状态
		if (human.getFirstRechargeState() == FirstRechargeState.FINISHED
				.getIndex()) {
			human.sendErrorMessage(LangConstants.FIRST_RECHARGE_FINISHED);
			return;
		}
		List<FirstRechargeRewardInfo> rewardInfoList = human
				.getHumanRechargeManager().getFirstRechargeRewardList();
		// 发送首充奖励面板消息
		GCOpenFirstRechargePanel msg = new GCOpenFirstRechargePanel();
		msg.setTotalPrice(SharedConstants.FIRST_RECHARGE_PRICE);
		msg.setRewardInfos(rewardInfoList
				.toArray(new FirstRechargeRewardInfo[0]));
		human.sendMessage(msg);
	}

}
