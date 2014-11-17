package com.hifun.soul.gameserver.activity.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.msg.CGEnterActivity;

@Component
public class CGEnterActivityHandler implements IMessageHandlerWithType<CGEnterActivity> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_ACTIVITY;
	}

	@Override
	public void execute(CGEnterActivity message) {
		message.getPlayer().getHuman().getHumanActivityManager().enterActivity(message.getActivityId());
	}

}
