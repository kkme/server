package com.hifun.soul.manageserver.msg;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 管理服请求开启对外服务;
 * 
 * @author crazyjohn
 * 
 */
public class MGStartExternalServiceMessage extends
		BaseSessionMessage<ServerSession> {

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.MG_START_EXTERNAL_SERVICE;
	}

	@Override
	protected boolean readImpl() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean writeImpl() {
		return true;
	}

}
