package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

/**
 * 管理服请求关闭对外服务;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class MGStopExternalServiceMessage extends
		BaseSessionMessage<ServerSession> {

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.MG_STOP_EXTERNAL_SERVICE;
	}

	@Override
	protected boolean readImpl() {
		return true;
	}

	@Override
	protected boolean writeImpl() {
		throw new UnsupportedOperationException();
	}

}
