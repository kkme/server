package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 管理请求服务器组信息;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class MGGetRealmInfoMessage extends BaseSessionMessage<ServerSession>
		implements IAsyncCallbackMessage {
	private long contextId;

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
		return MessageType.MG_GET_REALM_INFO;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		throw new UnsupportedOperationException();
	}

}
