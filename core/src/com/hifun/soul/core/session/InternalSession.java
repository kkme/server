package com.hifun.soul.core.session;

import com.hifun.soul.core.msg.IMessage;

/**
 * 
 * 
 */
public class InternalSession implements ISession {
	@Override
	public void close() {
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void write(IMessage msg) {
	}

	@Override
	public boolean closeOnException() {
		return false;
	}
}