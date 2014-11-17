package com.hifun.soul.core.msg.sys;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.ISessionMessage;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.session.ISession;

/**
 * 会话打开的消息
 * @param <T>
 * 
 */
public class SessionOpenedMessage<T extends ISession> extends
		SysInternalMessage implements ISessionMessage<T> {

	protected volatile T session;

	public SessionOpenedMessage(T session) {
		super.setType(MessageType.SYS_SESSION_OPEN);
		super.setTypeName("SYS_SESSION_OPEN");
		this.session = session;
	}

	@Override
	public T getSession() {
		return this.session;
	}

	public void setSession(T session) {
		this.session = session;
	}

	@Override
	public void execute() {
	}

	@Override
	public String toString() {
		return "SessionOpenedMessage";
	}
}
