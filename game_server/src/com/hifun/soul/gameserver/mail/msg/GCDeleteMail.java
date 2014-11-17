package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 删除邮件
 *
 * @author SevenSoul
 */
@Component
public class GCDeleteMail extends GCMessage{
	
	/** 邮件ID */
	private long[] mailId;

	public GCDeleteMail (){
	}
	
	public GCDeleteMail (
			long[] mailId ){
			this.mailId = mailId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mailId = new long[count];
		for(int i=0; i<count; i++){
			mailId[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(mailId.length);
	for(int i=0; i<mailId.length; i++){
	Long objmailId = mailId[i];
			writeLong(objmailId);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DELETE_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DELETE_MAIL";
	}

	public long[] getMailId(){
		return mailId;
	}

	public void setMailId(long[] mailId){
		this.mailId = mailId;
	}	
}