package com.hifun.soul.manageserver.msg;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 管理服向游戏服请求服务器状态;
 * 
 * @author crazyjohn
 * 
 */
public class MGGetRealmInfoMessage extends BaseSessionMessage<ServerSession>
		implements IAsyncCallbackMessage {
	private long contextId;

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.MG_GET_REALM_INFO;
	}

	@Override
	protected boolean readImpl() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean writeImpl() {
		writeLong(this.contextId);
		return true;
	}

	@Override
	public long getContextId() {
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		this.contextId = id;

	}

}
