package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class MGBulletinRemove extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private int bulletinId;
	private long contextId;
	public int getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}

	public void setContextId(long contextId) {
		this.contextId = contextId;
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
	public short getType() {		
		return MessageType.MG_BULLETIN_REMOVE;
	}

	@Override
	protected boolean readImpl() {
		contextId = readLong();
		bulletinId = readInteger();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeInteger(bulletinId);
		return true;
	}

}
