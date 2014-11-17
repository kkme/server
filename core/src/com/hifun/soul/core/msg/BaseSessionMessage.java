package com.hifun.soul.core.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.session.MinaSession;

/**
 * 带有消息发送者的消息基类
 * 
 * 
 * @param <T>
 */
@Component
public abstract class BaseSessionMessage<T extends MinaSession> extends
		BaseMessage implements ISessionMessage<T> {
	private volatile T session;

	public T getSession() {
		return session;
	}

	public void setSession(T s) {
		this.session = s;
	}

	public abstract short getType();
}
