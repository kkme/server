package com.hifun.soul.core.session;

import org.apache.mina.common.IoSession;

/**
 * 服务器之间的回话信息;
 * 
 * @author crazyjohn
 * 
 */
public class ServerSession extends MinaSession {
	/** 服务器类型 */
	private int serverType;

	public ServerSession(IoSession s) {
		super(s);
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

}
