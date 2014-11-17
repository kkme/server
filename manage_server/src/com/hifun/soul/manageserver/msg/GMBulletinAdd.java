package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class GMBulletinAdd extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{

	/** 执行结果 */
	private boolean result;
	/** 上下文id */
	private long contextId; 
	
	public long getContextId() {
		return contextId;
	}

	public void setContextId(long contextId) {
		this.contextId = contextId;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();	
	}

	@Override
	public short getType() {		
		return MessageType.GM_BULLETIN_ADD;
	}

	@Override
	protected boolean readImpl() {
		contextId = readLong();
		this.result = readBoolean();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeBoolean(result);
		return true;
	}

}
