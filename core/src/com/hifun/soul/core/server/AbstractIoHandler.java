package com.hifun.soul.core.server;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.ISessionMessage;
import com.hifun.soul.core.session.ISession;
import com.hifun.soul.core.session.MinaSession;
import com.hifun.soul.core.util.IpUtils;

/**
 * 
 * 从Socket接收到的消息处理器,处理以下操作:
 * 
 * <pre>
 * 1.新连接 向MessageProcess队列中增加一个状态为SESSION_OPENED的SessionMessage消息,由IMessageHandler处理新连接的逻辑
 * 2.收到消息 只处理类型为ISenderMessage的消息,将收到的消息增加到MessageProcessor队列中
 * 3.连接断开 向MessageProcess队列中增加一个SESSION_CLOSED的SessionMessage消息,由IMessageHandler处理连接断开的逻辑
 * </pre>
 * 
 * 
 */

public abstract class AbstractIoHandler<T extends ISession> extends
		IoHandlerAdapter {
	/** 消息处理器 */
	protected IMessageProcessor msgProcessor;
	
	protected static final Logger log = Loggers.MSG_LOGGER;

	protected volatile int curSessionCount;
	
	public AbstractIoHandler() {
	}
	
	public AbstractIoHandler(IMessageProcessor msgProcessor) {
		this.msgProcessor = msgProcessor;
	}
	
	public void setMessageProcessor(IMessageProcessor msgProcessor) {
		this.msgProcessor = msgProcessor;
	}
	
	/**
	 * 处理流程:
	 * 
	 * <pre>
	 * 1.为新连接创建一个Sender实例,并将Sender与session绑定
	 * 2.创建一个系统消息SessionMessage实例,将其放入MessageProcessor的消息队列中等待处理
	 * </pre>
	 * 
	 */
	@Override
	public void sessionOpened(IoSession session) {
		curSessionCount++;
		T sessionInfo = createSession(session);
		session.setAttachment(sessionInfo);
		IMessage msg = createSessionOpenMessage(sessionInfo);
		if (msg == null) {
			return;
		}
		msgProcessor.put(msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void messageReceived(IoSession session, Object obj) {
		if (!(obj instanceof ISessionMessage)) {
			return;
		}
		ISessionMessage<MinaSession> msg = (ISessionMessage<MinaSession>) obj;
		MinaSession minaSession = (MinaSession) session.getAttachment();

		if (minaSession == null) {
			if (log.isWarnEnabled()) {
				log.warn("No sender");
			}
			// 可能，脱离关系的Session收到了消息，正好关闭之
			session.close();
			return;
		}

		// 设置消息的Sender对象
		msg.setSession(minaSession);
		msgProcessor.put(msg);
	}

	@Override
	public void sessionClosed(IoSession session) {
		curSessionCount--;
		@SuppressWarnings("unchecked")
		T sessionInfo = (T) session.getAttachment();
		if (sessionInfo != null) {
			session.setAttachment(null);
		}
		IMessage msg = createSessionCloseMessage(sessionInfo);
		if (msg == null) {
			return;
		}
		msgProcessor.put(msg);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable arg1) {
		String exceptMsg = arg1.getMessage();
		if (exceptMsg != null
				&& (exceptMsg.indexOf("reset by peer") > 0
						|| exceptMsg.indexOf("Connection timed out") > 0
						|| exceptMsg.indexOf("Broken pipe") > 0 || exceptMsg
						.indexOf("远程主机强迫关闭了") > -1)) {
			log.debug("#CLOSED.SESSION:" + IpUtils.getIp(session));
		} else {
			// 这个日志很重要，一定要输出
			log.error("#CLOSE.SESSION.EXCEPTION:" + IpUtils.getIp(session),
					arg1);
		}
		session.close();
	}

	/**
	 * 创建会话信息;
	 * 
	 * @param session
	 * @return 返回会话信息;
	 */
	protected T createSession(IoSession session) {
		return null;
	}

	/**
	 * 创建会话打开消息;
	 * 
	 * @param session
	 * @return
	 */
	protected ISessionMessage<T> createSessionOpenMessage(T session) {
		return null;
	}

	/**
	 * 创建关闭会话消息;
	 * 
	 * @param session
	 * @return
	 */
	protected ISessionMessage<T> createSessionCloseMessage(T session) {
		return null;
	}

}
