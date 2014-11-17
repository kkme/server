package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.manage.msg.MGStartExternalServiceMessage;
import com.hifun.soul.gameserver.startup.GameServerRuntime;

@Component
public class MGStartExternalServiceHandler implements
		IMessageHandlerWithType<MGStartExternalServiceMessage> {
	@Override
	public short getMessageType() {
		return MessageType.MG_START_EXTERNAL_SERVICE;
	}

	@Override
	public void execute(MGStartExternalServiceMessage message) {
		GameServerRuntime.setOpenOn();
	}
}
