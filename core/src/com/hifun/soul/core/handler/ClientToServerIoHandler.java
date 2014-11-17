package com.hifun.soul.core.handler;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.session.ExternalDummyServerSession;

/**
 * 用于客户端连接到Server的网络消息接收器
 * 
 * @see {@link ExternalDummyServerSession}
 * 
 */
public class ClientToServerIoHandler extends
		AbstractIoHandler<ExternalDummyServerSession> {

	public ClientToServerIoHandler(IMessageProcessor msgProcessor) {
		super(msgProcessor);
	}

	@Override
	protected ExternalDummyServerSession createSession(IoSession session) {
		return new ExternalDummyServerSession(session);
	}

	@Override
	public void sessionClosed(IoSession session) {
		super.sessionClosed(session);
	}
}
