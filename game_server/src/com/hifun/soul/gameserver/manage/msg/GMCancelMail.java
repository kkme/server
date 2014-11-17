package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class GMCancelMail extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private long contextId;
	private boolean result;
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
		return MessageType.GM_CANCEL_MAIL;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		this.result = readBoolean();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeBoolean(result);
		return true;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
