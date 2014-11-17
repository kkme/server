package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求查看邮件
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMail extends CGMessage{
	
	/** 邮件ID */
	private long mailId;
	
	public CGShowMail (){
	}
	
	public CGShowMail (
			long mailId ){
			this.mailId = mailId;
	}
	
	@Override
	protected boolean readImpl() {
		mailId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(mailId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MAIL";
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}

	@Override
	public void execute() {
	}
}