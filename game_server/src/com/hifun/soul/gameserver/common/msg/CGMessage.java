package com.hifun.soul.gameserver.common.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 客户端发给服务端的消息的基类;
 * 
 * @author crazyjohn
 * 
 */
@Component
public abstract class CGMessage extends BaseSessionMessage<MinaGameClientSession> {
	
	
	public Player getPlayer() {
		return this.getSession().getPlayer();
	}
	@Override
	protected boolean writeImpl() {
		return false;
	}
}
