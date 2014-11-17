package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;
import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 管理服请求将某个角色踢下线
 * 
 * @author magicstone
 *
 */
@Component
public class MGKickOffCharacter extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private long contextId;
	private long humanGuid;
	
	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
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
		return MessageType.MG_KICK_OFF_CHARACTER;
	}

	@Override
	protected boolean readImpl() {	
		contextId = readLong();
		humanGuid = readLong();
		return true;
	}

	@Override
	protected boolean writeImpl() {	
		writeLong(contextId);
		writeLong(humanGuid);
		return true;
	}

}
