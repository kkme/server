package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 游戏服返回在线玩家数量
 * 
 * @author magicstone
 *
 */
@Component
public class GMQueryOnlineNum extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage {
	private long contextId;
	/** 在线玩家数量 */
	private int onlineNum;
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

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	@Override
	public short getType() {		
		return MessageType.GM_QUERY_ONLINE_NUMBER;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		this.onlineNum = readInt();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeInt(onlineNum);
		return true;
	}

}
