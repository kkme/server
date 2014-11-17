package com.hifun.soul.core.msg;

import com.hifun.soul.core.session.ISession;

/**
 * 基于会话的消息
 * 
 * @param <T>
 * 
 */
public interface ISessionMessage<T extends ISession> extends IMessage {
	/**
	 * 取得此消息的发送者
	 * 
	 * @return
	 */
	public T getSession();

	/**
	 * 设置该消息的发送者
	 * 
	 * @param sender
	 */
	public void setSession(T session);
}
