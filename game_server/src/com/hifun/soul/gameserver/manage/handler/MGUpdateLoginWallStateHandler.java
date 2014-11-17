package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.hifun.soul.gameserver.manage.msg.GMGetLoginWallState;
import com.hifun.soul.gameserver.manage.msg.MGUpdateLoginWallState;

@Component
public class MGUpdateLoginWallStateHandler implements IMessageHandlerWithType<MGUpdateLoginWallState> {

	@Override
	public short getMessageType() {
		return MessageType.MG_UPDATE_LOGIN_WALL_STATE;
	}

	@Override
	public void execute(MGUpdateLoginWallState message) {
		GameServerConfig config = (GameServerConfig) ApplicationContext
				.getApplicationContext().getDefaultListableBeanFactory()
				.getSingleton(GameServerConfig.class.getSimpleName());
		config.setLoginWallEnabled(message.isEnable());
		GMGetLoginWallState gmMsg = new GMGetLoginWallState();
		gmMsg.setResult(config.isLoginWallEnabled());
		gmMsg.setContextId(message.getContextId());
		message.getSession().write(gmMsg);				
	}

}
