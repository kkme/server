package com.hifun.soul.gameserver;

import com.hifun.soul.core.session.ServerSession;

/**
 * 服务期间会话管理器;
 * 
 * @author crazyjohn
 * 
 */
public class ServerSessionManager {
	private ServerSession manageServer;

	public ServerSession getManageServer() {
		return manageServer;
	}

	public void setManageServer(ServerSession manageServer) {
		this.manageServer = manageServer;
	}
}
