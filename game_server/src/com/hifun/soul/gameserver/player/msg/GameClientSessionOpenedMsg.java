package com.hifun.soul.gameserver.player.msg;

import com.hifun.soul.core.msg.sys.SessionOpenedMessage;
import com.hifun.soul.gameserver.startup.GameServerIoHandler;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 处理玩家连接和断开连接的消息，此消息在系统主线程中执行
 * 
 * @see GameServerIoHandler
 * 
 */
public class GameClientSessionOpenedMsg extends
		SessionOpenedMessage<MinaGameClientSession> {

	/**
	 * @param type
	 * @param session
	 */
	public GameClientSessionOpenedMsg(MinaGameClientSession session) {
		super(session);
	}

	@Override
	public void execute() {	
		
	}
}
