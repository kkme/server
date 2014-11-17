package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;
import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 游戏服返回将某个角色踢下线的结果
 * 
 * @author magicstone
 *
 */
@Component
public class GMKickOffCharacter extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private long contextId;
	private boolean result;
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
	public long getContextId() {
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		contextId = id;
	}

	@Override
	public short getType() {		
		return MessageType.GM_KICK_OFF_CHARACTER;
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
