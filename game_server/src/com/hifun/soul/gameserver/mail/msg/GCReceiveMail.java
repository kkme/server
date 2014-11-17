package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 收到邮件
 *
 * @author SevenSoul
 */
@Component
public class GCReceiveMail extends GCMessage{
	

	public GCReceiveMail (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECEIVE_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECEIVE_MAIL";
	}
}