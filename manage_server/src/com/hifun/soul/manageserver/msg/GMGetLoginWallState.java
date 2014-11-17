package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class GMGetLoginWallState extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
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
		this.contextId = id;
	}
	

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public short getType() {		
		return MessageType.GM_GET_LOGIN_WALL_STATE;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		this.result = readBoolean();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(this.contextId);
		writeBoolean(this.result);
		return true;
	}

}
