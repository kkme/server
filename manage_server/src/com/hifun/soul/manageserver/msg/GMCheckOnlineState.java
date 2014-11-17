package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 游戏服返回的玩家在线状态消息
 * 
 * @author magicstone
 *
 */
@Component
public class GMCheckOnlineState extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private long contextId;
	private boolean isOnline;
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
	
	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	@Override
	public short getType() {		
		return MessageType.GM_CHECK_CHARACTER_ONLINE_STATE;
	}

	@Override
	protected boolean readImpl() {	
		this.contextId = readLong();
		this.isOnline = readBoolean();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeBoolean(isOnline);
		return true;
	}

}
