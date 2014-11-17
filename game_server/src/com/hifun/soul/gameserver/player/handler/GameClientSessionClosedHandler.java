package com.hifun.soul.gameserver.player.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.logger.Loggers;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.msg.GameClientSessionClosedMsg;

/**
 * 
 * 关闭连接消息的处理<br>
 * FIXME: crazyjohn 一定要谨慎处理收尾操作没做好引起的内存泄露!!!
 * 
 * @author magicstone
 * @author crazyjohn
 * @since 2012-06-14
 */
@Component
public class GameClientSessionClosedHandler implements
		IMessageHandlerWithType<GameClientSessionClosedMsg> {
	private Logger logger = Loggers.HUMAN_LOGIN_LOGGER();
	@Autowired
	private GameWorld sceneManager;

	@Override
	public short getMessageType() {
		return MessageType.SYS_SESSION_CLOSE;
	}

	@Override
	public void execute(GameClientSessionClosedMsg message) {
		Player player = message.getSession().getPlayer();
		if (player == null) {
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Player exit, passportId: %d, account: %s",
					player.getPassportId(), player.getAccount()));
		}
		sceneManager.playerLeve(player);
	}

}
