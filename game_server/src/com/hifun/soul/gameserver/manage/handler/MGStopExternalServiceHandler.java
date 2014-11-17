package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.manage.msg.MGStopExternalServiceMessage;
import com.hifun.soul.gameserver.startup.GameServerRuntime;

@Component
public class MGStopExternalServiceHandler implements
		IMessageHandlerWithType<MGStopExternalServiceMessage> {

	@Override
	public short getMessageType() {
		return MessageType.MG_STOP_EXTERNAL_SERVICE;
	}

	@Override
	public void execute(MGStopExternalServiceMessage message) {
		GameServerRuntime.setOpenOff();
	}

}
