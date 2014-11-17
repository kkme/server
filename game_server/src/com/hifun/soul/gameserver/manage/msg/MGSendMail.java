package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;
import com.hifun.soul.proto.services.Services.MailObject;
@Component
public class MGSendMail extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{

	private long contextId;
	private MailObject mailObject;
	
	@Override
	public void execute() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public long getContextId() {
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		this.contextId=id;		
	}

	@Override
	public short getType() {
		return MessageType.MG_SEND_MAIL;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		MailObject.Builder builder = MailObject.newBuilder();
		builder.setMailId(readLong());
		builder.setMailType(readInteger());
		builder.setReceiveHumanIds(readString());
		builder.setTheme(readString());
		builder.setContent(readString());
		builder.setSendHumanId(readLong());
		builder.setSendHumanName(readString());
		builder.setExpireDate(readLong());
		int count = readInteger();
		count = count>0? count : 0;
		for(int i=0; i<count; i++){
			builder.addItemId(readInteger());
		}	
		builder.setSendMemo(readString());		
		builder.setIsTimingMail(readBoolean());
		builder.setLastEditTime(readLong());
		builder.setPlanSendTime(readLong());
		int numCount = readInteger();
		numCount = numCount>0?numCount:0;
		for(int i=0;i<numCount;i++){
			builder.addItemNum(readInteger());
		}
		this.mailObject = builder.build();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeLong(mailObject.getMailId());
		writeInteger(mailObject.getMailType());
		writeString(mailObject.getReceiveHumanIds());
		writeString(mailObject.getTheme());
		writeString(mailObject.getContent());
		writeLong(mailObject.getSendHumanId());
		writeString(mailObject.getSendHumanName());
		writeLong(mailObject.getExpireDate());
		int count = mailObject.getItemIdList() !=null ? mailObject.getItemIdList().size() : 0;
		writeInteger(count);
		for(int i=0;i<count;i++){
			writeInteger(mailObject.getItemIdList().get(i));
		}
		writeString(mailObject.getSendMemo());
		writeBoolean(mailObject.getIsTimingMail());
		writeLong(mailObject.getLastEditTime());
		writeLong(mailObject.getPlanSendTime());
		int numCount = mailObject.getItemNumList() !=null ? mailObject.getItemNumList().size() : 0;
		writeInteger(numCount);
		for(int i=0;i<numCount;i++){
			writeInteger(mailObject.getItemNumList().get(i));
		}
		return true;
	}

	public MailObject getMailObject() {
		return mailObject;
	}

	public void setMailObject(MailObject mailObject) {
		this.mailObject = mailObject;
	}

}
