package com.hifun.soul.gameserver.common.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 服务器端发送给客户端的消息基类
 * 
 * @author crazyjohn
 * 
 */
@Component
public abstract class GCMessage extends
		BaseSessionMessage<MinaGameClientSession> {

	@Override
	protected boolean readImpl() {
		return false;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	public Player getPlayer() {
		return this.getSession().getPlayer();
	}

}
