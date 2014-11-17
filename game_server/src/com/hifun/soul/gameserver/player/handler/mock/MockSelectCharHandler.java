package com.hifun.soul.gameserver.player.handler.mock;

import org.slf4j.Logger;

import com.hifun.soul.common.auth.LoginChar;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.logger.Loggers;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.handler.CGSelectCharHandler;
import com.hifun.soul.gameserver.player.msg.CGSelectChar;
import com.hifun.soul.gameserver.player.msg.GCEnterScene;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * Mock选择角色处理器, 不经过DB验证, 直接发送进场景;
 * 
 * @author crazyjohn
 * 
 */
public class MockSelectCharHandler extends CGSelectCharHandler {
	private static Logger logger = Loggers.HUMAN_LOGIN_LOGGER();

	@Override
	public void execute(CGSelectChar message) {
		MinaGameClientSession session = message.getSession();
		final Player player = session.getPlayer();
		if (player == null) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"Player can not be null, session: %s", session));
			}
			return;
		}
		// 绑定玩家和角色
		final Human human = new Human(player);
		player.setHuman(human);

		// 获取指定索引的loginChar
		LoginChar currentChar = player.getLoginCharByIndex(message
				.getCharIndex());
		if (currentChar == null) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"CurrentLoginChar can not be null, session: %s",
						session));
			}
			return;
		}
		player.setCurrentLoginChar(currentChar);

		// 切换状态
		if (!player.canTransferStateTo(PlayerState.ENTERING)) {
			logger.error(String.format(
					"Can not transfer player state from %s to %s",
					player.getState(), PlayerState.ENTERING));
			return;
		}
		player.transferStateTo(PlayerState.ENTERING);

		// 通知客户端可以进场景了
		GCEnterScene enterSceneMsg = new GCEnterScene();
		player.sendMessage(enterSceneMsg);
	}
}
