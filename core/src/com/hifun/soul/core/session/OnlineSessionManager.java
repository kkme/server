package com.hifun.soul.core.session;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;

/**
 * 维护服务器上所有的会话
 * 
 * 
 */
@Scope("singleton")
@Component
@Deprecated
public class OnlineSessionManager<E extends MinaSession> {
	private List<E> sessions = new CopyOnWriteArrayList<E>();
	
	public boolean addSession(E session) {
		sessions.add(session);
		return true;
	}

	public void removeSession(E session) {
		sessions.remove(session);
	}

	/**
	 * @param msg
	 */
	public void broadcast(IMessage msg) {
		for (ISession session : sessions) {
			if (Loggers.MSG_LOGGER.isDebugEnabled()) {
				Loggers.MSG_LOGGER.debug("【Broadcast】" + msg);
			}
			session.write(msg);
		}
	}
}
