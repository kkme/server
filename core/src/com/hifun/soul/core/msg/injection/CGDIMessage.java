package com.hifun.soul.core.msg.injection;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.session.MinaSession;

/**
 * 测试依赖注入(Dependency Injection)的消息;
 * 
 * @author crazyjohn
 * 
 */
public class CGDIMessage extends BaseSessionMessage<MinaSession> {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public short getType() {
		return NewMessageType.CG_DI_MESSAGE.getMessageNumber();
	}

	@Override
	protected boolean readImpl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean writeImpl() {
		// TODO Auto-generated method stub
		return false;
	}



}
