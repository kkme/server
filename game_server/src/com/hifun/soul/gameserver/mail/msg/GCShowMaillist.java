package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回邮件列表给客户端
 *
 * @author SevenSoul
 */
@Component
public class GCShowMaillist extends GCMessage{
	
	/** 邮件列表 */
	private com.hifun.soul.gameserver.mail.model.MailListItemInfo[] mailList;

	public GCShowMaillist (){
	}
	
	public GCShowMaillist (
			com.hifun.soul.gameserver.mail.model.MailListItemInfo[] mailList ){
			this.mailList = mailList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mailList = new com.hifun.soul.gameserver.mail.model.MailListItemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mail.model.MailListItemInfo objmailList = new com.hifun.soul.gameserver.mail.model.MailListItemInfo();
			mailList[i] = objmailList;
					objmailList.setMailId(readLong());
							objmailList.setMailType(readInteger());
							objmailList.setTheme(readString());
							objmailList.setSendHumanName(readString());
							objmailList.setIsRead(readBoolean());
							objmailList.setIsAttachment(readBoolean());
							objmailList.setIsPickUp(readBoolean());
							objmailList.setExpireDays(readInteger());
							objmailList.setChecked(readBoolean());
							objmailList.setSendTime(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(mailList.length);
	for(int i=0; i<mailList.length; i++){
	com.hifun.soul.gameserver.mail.model.MailListItemInfo objmailList = mailList[i];
				writeLong(objmailList.getMailId());
				writeInteger(objmailList.getMailType());
				writeString(objmailList.getTheme());
				writeString(objmailList.getSendHumanName());
				writeBoolean(objmailList.getIsRead());
				writeBoolean(objmailList.getIsAttachment());
				writeBoolean(objmailList.getIsPickUp());
				writeInteger(objmailList.getExpireDays());
				writeBoolean(objmailList.getChecked());
				writeString(objmailList.getSendTime());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_MAILLIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MAILLIST";
	}

	public com.hifun.soul.gameserver.mail.model.MailListItemInfo[] getMailList(){
		return mailList;
	}

	public void setMailList(com.hifun.soul.gameserver.mail.model.MailListItemInfo[] mailList){
		this.mailList = mailList;
	}	
}