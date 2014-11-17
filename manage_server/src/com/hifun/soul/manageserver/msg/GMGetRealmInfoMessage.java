package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 游戏服返回的服务器组信息;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class GMGetRealmInfoMessage extends BaseSessionMessage<ServerSession>
		implements IAsyncCallbackMessage {
	private long contextId;
	private boolean open;

	@Override
	public long getContextId() {
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		this.contextId = id;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.GM_REALM_INFO;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		open = readBoolean();
		return true;
	}

	public boolean isOpen() {
		return this.open;
	}

	@Override
	protected boolean writeImpl() {
		throw new UnsupportedOperationException();
	}

}
