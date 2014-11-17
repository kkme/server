package com.hifun.soul.manageserver.msg;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public  class GMOnlineHuman extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private long contextId;
	private long[] onlineHumanIds;
	
	@Override
	public void execute() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public long getContextId() {		
		return this.contextId;
	}

	@Override
	public void setContextId(long id) {
		contextId = id;
	}
	
	@Override
	public short getType() {		
		return MessageType.GM_ONLINE_HUMAN;
	}

	public long[] getOnlineHumanIds() {
		return onlineHumanIds;
	}

	public void setOnlineHumanIds(long[] onlineHumanIds) {
		this.onlineHumanIds = onlineHumanIds;
	}

	@Override
	protected boolean readImpl() {
		contextId = readLong();
		int count = readInteger();
		count = count < 0 ? 0 : count;
		onlineHumanIds = new long[count];
		for(int i=0;i<count;i++){
			onlineHumanIds[i] = readLong();
		}		
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		int count = onlineHumanIds==null ? 0 : onlineHumanIds.length;
		writeInteger(count);
		if(count > 0){
			for(int i=0;i<onlineHumanIds.length;i++){				
				writeLong(onlineHumanIds[i]);				
			}
		}
		return true;
	}
	
}
