package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.escort.msg.CGEscortBuyRobNum;
import com.hifun.soul.gameserver.escort.msg.GCEscortBuyRobNum;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 购买拦截次数
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortBuyRobNumHandler implements
		IMessageHandlerWithType<CGEscortBuyRobNum> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_BUY_ROB_NUM;
	}

	@Override
	public void execute(CGEscortBuyRobNum message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		int buyRobNum = human.getEscortRobBuyNum();
		if (buyRobNum >= GameServerAssist.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel()).getMaxBuyEscortRobTimes()) {
			human.sendErrorMessage(LangConstants.CAN_NOT_BUY_ROB_NUM);
			return;
		}
		int buyCost = GameServerAssist.getEscortTemplateManager()
				.getBuyRobNumCost(buyRobNum + 1);
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, buyCost,
				MoneyLogReason.ESCORT_BUY_ROB_NUM, "")) {
			human.setEscortRobBuyNum(buyRobNum + 1);
			human.setEscortRobRemainNum(human.getEscortRobRemainNum() + 1);
			GCEscortBuyRobNum msg = new GCEscortBuyRobNum();
			msg.setBuyRobNumCost(GameServerAssist.getEscortTemplateManager()
					.getBuyRobNumCost(buyRobNum + 2));
			msg.setRemainRobNum(human.getEscortRobRemainNum());
			human.sendMessage(msg);
		}
	}
}
