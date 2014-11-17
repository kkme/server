package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求拾取附件中物品
 * 
 * @author SevenSoul
 */
@Component
public class CGPickupAttachment extends CGMessage{
	
	/** 邮件ID */
	private long mailId;
	
	public CGPickupAttachment (){
	}
	
	public CGPickupAttachment (
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
		return MessageType.CG_PICKUP_ATTACHMENT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PICKUP_ATTACHMENT";
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