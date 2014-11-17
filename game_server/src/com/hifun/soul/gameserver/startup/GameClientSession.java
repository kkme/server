package com.hifun.soul.gameserver.startup;

import com.hifun.soul.core.session.ISession;
import com.hifun.soul.gameserver.player.Player;

/**
 * 与 GameServer 连接的客户端的会话信息
 * 
 */
public interface GameClientSession extends ISession {
	void setPlayer(Player player);

	Player getPlayer();

	String getIp();
}
