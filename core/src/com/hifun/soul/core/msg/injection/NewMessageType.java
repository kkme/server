package com.hifun.soul.core.msg.injection;
/**
 * 消息类型
 * 
 * @author crazyjohn
 *
 */
public enum NewMessageType {
	CG_DI_MESSAGE(100);
	
	private short type;
	
	NewMessageType(int type) {
		this.type = (short)type;
	}

	public short getMessageNumber() {
		return type;
	}
}
