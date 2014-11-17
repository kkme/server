package com.hifun.soul.payserver.net;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.payserver.PayServlet;

/**
 * 游戏服时间处理器;
 * 
 * @author crazyjohn
 * 
 */
public class GameIoHandler extends AbstractIoHandler<PSClientSession> {

	public GameIoHandler(PayServlet payServlet,
			QueueMessageProcessor messageProcessor, String serverIp) {
		super(messageProcessor);
	}

	@Override
	public void sessionClosed(IoSession session) {
		super.sessionClosed(session);
		System.out.println("Session closed: " + session);
	}

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
		System.out.println("Session opened: " + session);
	}

}
