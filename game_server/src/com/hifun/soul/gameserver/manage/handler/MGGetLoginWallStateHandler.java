package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.hifun.soul.gameserver.manage.msg.GMGetLoginWallState;
import com.hifun.soul.gameserver.manage.msg.MGGetLoginWallState;

@Component
public class MGGetLoginWallStateHandler implements IMessageHandlerWithType<MGGetLoginWallState> {

	@Override
	public short getMessageType() {
		return MessageType.MG_GET_LOGIN_WALL_STATE;
	}

	@Override
	public void execute(MGGetLoginWallState message) {
		GameServerConfig config = (GameServerConfig) ApplicationContext
				.getApplicationContext().getDefaultListableBeanFactory()
				.getSingleton(GameServerConfig.class.getSimpleName());		
		GMGetLoginWallState gmMsg = new GMGetLoginWallState();
		gmMsg.setResult(config.isLoginWallEnabled());
		gmMsg.setContextId(message.getContextId());
		message.getSession().write(gmMsg);				
	}

}
