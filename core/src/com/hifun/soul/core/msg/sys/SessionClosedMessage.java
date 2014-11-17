package com.hifun.soul.core.msg.sys;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.ISessionMessage;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.session.ISession;

/**
 * 会话关闭的消息
 * @param <T>
 * 
 */
public class SessionClosedMessage<T extends ISession> extends
		SysInternalMessage implements ISessionMessage<T> {

	protected T session;

	public SessionClosedMessage(T sender) {
		super.setType(MessageType.SYS_SESSION_CLOSE);
		super.setTypeName("SYS_SESSION_CLOSE");
		this.session = sender;
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
		session.close();
	}

	@Override
	public String toString() {
		return "SessionClosedMessage";
	}
}
