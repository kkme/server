package com.hifun.soul.core.log;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

import com.hifun.soul.common.constants.Loggers;

/**
 * 实现LogIoHandler
 * 
 * 
 */
public class LogClientIoHandler extends IoHandlerAdapter {
	private UdpLoggerClient logger;

	public LogClientIoHandler(UdpLoggerClient logger) {
		this.logger = logger;
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		Loggers.SERVER_LOGGER.error(cause.getCause().toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		logger.closed();
	}
}
