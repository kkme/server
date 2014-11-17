package com.hifun.soul.gameserver.antiindulge.handler;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.antiindulge.msg.CGUpdateRevenueRateResponse;

public class CGUpdateRevenueRateResponseHandler implements IMessageHandlerWithType<CGUpdateRevenueRateResponse>{

	@Override
	public short getMessageType() {		
		return MessageType.CG_UPDATE_REVENUE_RATE_RESPONSE;
	}

	@Override
	public void execute(CGUpdateRevenueRateResponse message) {
		message.getPlayer().getHuman().getHumanAntiIndulgeManager().setRevenueRate(message.getRevenueRate());		
	}

}
