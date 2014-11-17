package com.hifun.soul.gameserver.mars.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.msg.CGBuyMarsMultipleNum;
import com.hifun.soul.gameserver.mars.msg.GCBuyMarsMultipleNum;

@Component
public class CGBuyMarsMultipleNumHandler implements
		IMessageHandlerWithType<CGBuyMarsMultipleNum> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_MARS_MULTIPLE_NUM;
	}

	@Override
	public void execute(CGBuyMarsMultipleNum message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MARS, true)) {
			return;
		}
		// 是否超过vip限制次数
		int buyMultipleNum = human.getMarsBuyMultipleNum() + 1;
		if (buyMultipleNum > GameServerAssist.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel())
				.getMaxBuyMarsMutipleTimes()) {
			human.sendErrorMessage(LangConstants.CAN_NOT_BUY_MORE_MULTIPLE_TIMES);
			return;
		}
		// 消费魔晶
		int costNum = GameServerAssist.getMarsTemplateManager()
				.getBuyMultipleNumCost(buyMultipleNum);
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.MARS_BUY_MULTIPLE_NUM, "")) {
			// 已购买加倍次数
			human.setMarsBuyMultipleNum(buyMultipleNum);
			// 剩余加倍次数+1
			human.setMarsRemainMultipleNum(human.getMarsRemainMultipleNum() + 1);
			// 发送响应消息
			GCBuyMarsMultipleNum msg = new GCBuyMarsMultipleNum();
			msg.setRemainMultipleNum(human.getMarsRemainMultipleNum());
			msg.setBuyMultipleNumCost(GameServerAssist.getMarsTemplateManager()
					.getBuyMultipleNumCost(buyMultipleNum + 1));
			human.sendMessage(msg);
		}
	}

}
