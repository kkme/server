package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.manage.msg.GMGetRealmInfoMessage;
import com.hifun.soul.gameserver.manage.msg.MGGetRealmInfoMessage;
import com.hifun.soul.gameserver.startup.GameServerRuntime;

@Component
public class MGGetRealmHandler implements
		IMessageHandlerWithType<MGGetRealmInfoMessage> {


	@Override
	public short getMessageType() {
		return MessageType.MG_GET_REALM_INFO;
	}

	@Override
	public void execute(MGGetRealmInfoMessage message) {
		GMGetRealmInfoMessage realmMsg = new GMGetRealmInfoMessage();
		realmMsg.setContextId(message.getContextId());
		realmMsg.setOpenState(GameServerRuntime.isOpen());
		message.getSession().write(realmMsg);
	}

}
