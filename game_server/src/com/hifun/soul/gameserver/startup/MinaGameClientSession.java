package com.hifun.soul.gameserver.startup;

import org.apache.mina.common.IoSession;

import com.hifun.soul.common.StatisticsLoggerHelper;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.session.MinaSession;
import com.hifun.soul.core.util.IpUtils;
import com.hifun.soul.gameserver.player.Player;

/**
 * 与 GameServer 连接的客户端的会话信息
 * 
 */
public class MinaGameClientSession extends MinaSession implements
		GameClientSession {

	/** 当前会话绑定的玩家对象，登录验证成功之后实例化 */
	private volatile Player player;

	public MinaGameClientSession(IoSession s) {
		super(s);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void write(IMessage msg) {
		if (session == null || !session.isConnected()) {
			return;
		}
		final IoSession _session = this.session;
		if (_session != null) {
			if (GameServerRuntime.isWriteTrafficControl()) {
				// 检查玩家的输出缓存是否达到上限,如果已经达到,则关闭连接,以此避免那些
				// 较慢连接对服务器的影响
				final int _sessionWriteSize = session.getScheduledWriteBytes();
				if (_sessionWriteSize > GameServerRuntime.MAX_WRITE_BYTES_INQUEUE) {
					Loggers.MSG_LOGGER.warn("SessionBufferSize" + msg);
					session.close();
					return;
				}
			}
			// FIXME: crazyjohn 这里需要改成异步的, 因为会有多线程调用, 容易成为系统瓶颈;
			if (Loggers.MSG_LOGGER.isDebugEnabled()) {
				if (player.getHuman() != null) {
					Loggers.MSG_LOGGER.debug("WRITE: " + msg + " to player : "
							+ player.getHuman().getName() + ", uuid : "
							+ player.getHuman().getId());
				} else {
					Loggers.MSG_LOGGER.debug("WRITE: " + msg);
				}
			}
			StatisticsLoggerHelper.logMessageSent(msg);
			_session.write(msg);
		}
	}

	@Override
	public String getIp() {
		return IpUtils.getIp(this.getIoSession());
	}
}
