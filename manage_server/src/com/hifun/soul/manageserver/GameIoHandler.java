package com.hifun.soul.manageserver;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.session.ServerSession;

public class GameIoHandler extends AbstractIoHandler<ServerSession> {
	int serverId = 0;
	private ServerSessionManager serverSessionManager;

	public GameIoHandler(ServerSessionManager serverSessionManager,
			IMessageProcessor msgProcessor,int serverId) {
		super(msgProcessor);
		this.serverSessionManager = serverSessionManager;
		this.serverId = serverId;
	}

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
		ServerSession gameServer = (ServerSession) session.getAttachment();
		// 初始化会话信息
		serverSessionManager.setGameServer(serverId,gameServer);
	}

	@Override
	protected ServerSession createSession(IoSession session) {
		return new ServerSession(session);
	}
	
	@Override
	public void sessionClosed(IoSession session){
		// 移除gameServer
		serverSessionManager.removeGameServer(serverId);
		super.sessionClosed(session);
	}
	

}
