package com.hifun.soul.gameserver.player.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.logger.Loggers;
import com.hifun.soul.gameserver.player.msg.GameClientSessionOpenedMsg;

/**
 * 
 * 连接建立的处理
 * 
 * @author magicstone
 * 
 */
@Component
public class GameClientSessionOpenedHandler implements
		IMessageHandlerWithType<GameClientSessionOpenedMsg> {

	@Override
	public short getMessageType() {
		return MessageType.SYS_SESSION_OPEN;
	}

	@Override
	public void execute(GameClientSessionOpenedMsg message) {
		Loggers.HUMAN_LOGIN_LOGGER().info("Handle session open message!");

	}

}
