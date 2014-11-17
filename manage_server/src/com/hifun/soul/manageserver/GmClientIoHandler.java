package com.hifun.soul.manageserver;

import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.session.GmClientSession;

/**
 * 管理服务IO事件处理器;
 * 
 * @author crazyjohn
 * 
 */
public class GmClientIoHandler extends AbstractIoHandler<GmClientSession> {
	public GmClientIoHandler(IMessageProcessor msgProcessor) {
		super(msgProcessor);
	}

	private Logger logger = LoggerFactory.getLogger(GmClientIoHandler.class);

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
		logger.info("GMSession opened: " + session);
	}
	@Override
	public void sessionClosed(IoSession session) {
		logger.info("GMSession closed: " + session);
		super.sessionClosed(session);
	}

	@Override
	protected GmClientSession createSession(IoSession session) {
		return new GmClientSession(session);
	}

}
