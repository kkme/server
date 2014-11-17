package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;
@Component
public class MGOnlineHumanRequest extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{

	private long contextId;	
	
	@Override
	public void execute() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public long getContextId() {
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		this.contextId=id;		
	}

	@Override
	public short getType() {
		return MessageType.MG_ONLINE_HUMAN_REQUEST;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();		
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);		
		return true;
	}

}
