package com.hifun.soul.gameserver;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.session.ServerSession;

public class ManageIoHandler extends AbstractIoHandler<ServerSession> {

	private ServerSessionManager sessionManager;

	public ManageIoHandler(ServerSessionManager sessionManager,
			IMessageProcessor msgProcessor) {
		super(msgProcessor);
		this.sessionManager = sessionManager;
	}

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
		ServerSession manageServer = (ServerSession) session.getAttachment();
		sessionManager.setManageServer(manageServer);
	}

	@Override
	protected ServerSession createSession(IoSession session) {
		return new ServerSession(session);
	}

}
