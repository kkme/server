package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 检查玩家在线状态
 * 
 * @author magicstone
 *
 */
@Component
public class MGCheckOnlineState extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	
	private long contextId;
	private long humanGuid;
	
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

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	@Override
	public short getType() {		
		return MessageType.MG_CHECK_CHARACTER_ONLINE_STATE;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		this.humanGuid = readLong();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeLong(humanGuid);
		return true;
	}

}
