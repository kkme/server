package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 查询在线玩家数量请求消息
 * 
 * @author magicstone
 *
 */
@Component
public class MGQueryOnlineNum extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage {
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
		return MessageType.MG_QUERY_ONLINE_NUMBER;
	}

	@Override
	protected boolean readImpl() {
		contextId = readLong();
		return true;
	}

	@Override
	protected boolean writeImpl() {		
		writeLong(contextId);
		return true;
	}

}
