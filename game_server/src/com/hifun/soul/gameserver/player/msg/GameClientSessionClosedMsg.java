package com.hifun.soul.gameserver.player.msg;

import com.hifun.soul.core.msg.sys.SessionClosedMessage;
import com.hifun.soul.gameserver.startup.GameServerIoHandler;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 处理玩家连接和断开连接的消息，此消息在系统主线程中执行
 * 
 * @see GameServerIoHandler
 */
public class GameClientSessionClosedMsg extends
		SessionClosedMessage<MinaGameClientSession> {

	/**
	 * @param type
	 * @param session
	 */
	public GameClientSessionClosedMsg(MinaGameClientSession session) {
		super(session);
	}

	@Override
	public void execute() {
		// TODO: 玩家下线的业务处理
	}
}
