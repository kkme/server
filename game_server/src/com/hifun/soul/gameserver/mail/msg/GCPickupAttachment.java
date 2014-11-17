package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 请求拾取附件中物品
 *
 * @author SevenSoul
 */
@Component
public class GCPickupAttachment extends GCMessage{
	
	/** 邮件ID */
	private long mailId;
	/** 是否拾取成功 */
	private boolean isSuccess;

	public GCPickupAttachment (){
	}
	
	public GCPickupAttachment (
			long mailId,
			boolean isSuccess ){
			this.mailId = mailId;
			this.isSuccess = isSuccess;
	}

	@Override
	protected boolean readImpl() {
		mailId = readLong();
		isSuccess = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(mailId);
		writeBoolean(isSuccess);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PICKUP_ATTACHMENT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PICKUP_ATTACHMENT";
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}

	public boolean getIsSuccess(){
		return isSuccess;
	}
		
	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}
}