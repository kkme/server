package com.hifun.soul.gameserver.startup;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.handler.BaseFlashIoHandler;
import com.hifun.soul.core.msg.ISessionMessage;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.util.IpUtils;
import com.hifun.soul.gameserver.player.msg.GameClientSessionClosedMsg;
import com.hifun.soul.gameserver.player.msg.GameClientSessionOpenedMsg;

/**
 * Game Server的网络消息接收器
 * 
 * 
 */
public class GameServerIoHandler extends
		BaseFlashIoHandler<MinaGameClientSession> {

	private static final Logger log = Loggers.GAME_LOGGER;
	private final GameExecutorService executor;
	private volatile AtomicBoolean isChecking = new AtomicBoolean(false);
	private volatile ConcurrentHashMap<IoSession, IoSession> suspendReadSessions = new ConcurrentHashMap<IoSession, IoSession>();

	/**
	 * 
	 * @param flashSocketPolicy
	 *            Flash客户端建立socket连接时发送的policy请求的响应
	 * @param executor
	 */
	public GameServerIoHandler(String flashSocketPolicy,
			GameExecutorService executor, IMessageProcessor msgProcessor) {
		super(flashSocketPolicy, msgProcessor);
		this.executor = executor;
		this.executor.scheduleTask(createCheckSuspendTask(), 2000);
	}

	@Override
	public void messageReceived(IoSession session, Object obj) {
		super.messageReceived(session, obj);
		if (GameServerRuntime.isReadTrafficControl()) {
			if (msgProcessor.isFull()) {
				// 队列已经满了,停止session的读取
				session.suspendRead();
				suspendReadSessions.putIfAbsent(session, session);
				if (log.isWarnEnabled()) {
					log.warn("[#GS.GameServerIoHandler.messageReceived] [suspen read from session "
							+ session + "]");
				}
			}
		}
	}

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
		MinaGameClientSession minaSession = (MinaGameClientSession) session
				.getAttachment();
		if (log.isInfoEnabled() && minaSession != null) {
			log.info(String.format("Session opened: %s;",
					IpUtils.getIp(session)));
		}
	}

	@Override
	public void sessionClosed(IoSession session) {
		MinaGameClientSession ms = (MinaGameClientSession) session
				.getAttachment();
		super.sessionClosed(session);
		if (log.isInfoEnabled() && ms != null) {
			log.info(String.format("Session closed: %s;",
					IpUtils.getIp(session)));
		}
	}

	/**
	 * 检查被挂起的session,如果session还处于连接状态,则将其恢复读取
	 */
	private void checkSuspenReadSessions() {
		if (!this.isChecking.compareAndSet(false, true)) {
			return;
		}
		try {
			Iterator<IoSession> _sessionsSet = this.suspendReadSessions
					.keySet().iterator();
			while (_sessionsSet.hasNext()) {
				IoSession _session = _sessionsSet.next();
				_sessionsSet.remove();
				if (_session.isConnected()) {
					_session.resumeRead();
					if (log.isWarnEnabled()) {
						log.warn("[#GS.GameServerIoHandler.messageReceived] [resume read from session "
								+ _session + "]");
					}
				}
			}
		} finally {
			this.isChecking.set(false);
		}
	}

	/**
	 * 创建一个用于检查读取挂起的任务
	 * 
	 * @return
	 */
	private Runnable createCheckSuspendTask() {
		return new Runnable() {
			@Override
			public void run() {
				if (suspendReadSessions.isEmpty()) {
					return;
				}
				if (msgProcessor.isFull()) {
					return;
				} else {
					checkSuspenReadSessions();
				}
			}
		};
	}

	@Override
	protected MinaGameClientSession createSession(IoSession session) {
		return new MinaGameClientSession(session);
	}

	@Override
	protected ISessionMessage<MinaGameClientSession> createSessionOpenMessage(
			MinaGameClientSession session) {
		GameClientSessionOpenedMsg msg = new GameClientSessionOpenedMsg(session);
		return msg;
	}

	@Override
	protected ISessionMessage<MinaGameClientSession> createSessionCloseMessage(
			MinaGameClientSession session) {
		GameClientSessionClosedMsg msg = new GameClientSessionClosedMsg(session);
		return msg;
	}

}
