package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class GMBulletinRemove extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	
	private boolean result;
	
	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

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
		this.contextId = id;
		
	}

	@Override
	public short getType() {		
		return MessageType.GM_BULLETIN_REMOVE;
	}

	@Override
	protected boolean readImpl() {
		contextId = readLong();
		result = readBoolean();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeBoolean(result);
		return true;
	}

}
