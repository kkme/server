package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 请求发送邮件
 *
 * @author SevenSoul
 */
@Component
public class GCSendMail extends GCMessage{
	
	/** 发送是否成功 */
	private boolean sendResult;

	public GCSendMail (){
	}
	
	public GCSendMail (
			boolean sendResult ){
			this.sendResult = sendResult;
	}

	@Override
	protected boolean readImpl() {
		sendResult = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(sendResult);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SEND_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SEND_MAIL";
	}

	public boolean getSendResult(){
		return sendResult;
	}
		
	public void setSendResult(boolean sendResult){
		this.sendResult = sendResult;
	}
}