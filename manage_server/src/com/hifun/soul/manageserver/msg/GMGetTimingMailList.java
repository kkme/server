package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;
import com.hifun.soul.proto.services.Services.MailObject;

@Component
public class GMGetTimingMailList extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage {
	private long contextId;
	private MailObject[] mailObjects;

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
		this.contextId = id;
	}

	public MailObject[] getMailObjects() {
		return mailObjects;
	}

	public void setMailObjects(MailObject[] mailObjects) {
		this.mailObjects = mailObjects;
	}

	@Override
	public short getType() {
		return MessageType.GM_GET_TIMING_MAIL_LIST;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		int count = readInteger();
		count = count < 0 ? 0 : count;
		mailObjects = new MailObject[count];
		for (int i = 0; i < count; i++) {
			MailObject.Builder builder = MailObject.newBuilder();
			builder.setMailId(readLong());
			builder.setMailType(readInteger());
			builder.setReceiveHumanIds(readString());
			builder.setTheme(readString());
			builder.setContent(readString());
			builder.setSendHumanId(readLong());
			builder.setSendHumanName(readString());
			builder.setExpireDate(readLong());
			int itemCount = readInteger();
			itemCount = itemCount > 0 ? itemCount : 0;
			for (int j = 0; j < itemCount; j++) {
				builder.addItemId(readInteger());
			}
			builder.setSendMemo(readString());
			builder.setIsTimingMail(readBoolean());
			builder.setLastEditTime(readLong());
			builder.setPlanSendTime(readLong());
			mailObjects[i] = builder.build();
		}
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		int count = mailObjects == null ? 0 : mailObjects.length;
		writeInteger(count);
		for (int i = 0; i < count; i++) {
			writeLong(mailObjects[i].getMailId());
			writeInteger(mailObjects[i].getMailType());
			writeString(mailObjects[i].getReceiveHumanIds());
			writeString(mailObjects[i].getTheme());
			writeString(mailObjects[i].getContent());
			writeLong(mailObjects[i].getSendHumanId());
			writeString(mailObjects[i].getSendHumanName());
			writeLong(mailObjects[i].getExpireDate());
			int itemCount = mailObjects[i].getItemIdList() != null ? mailObjects[i].getItemIdList().size() : 0;
			writeInteger(itemCount);
			for (int j = 0; j < itemCount; j++) {
				writeInteger(mailObjects[i].getItemIdList().get(i));
			}
			writeString(mailObjects[i].getSendMemo());
			writeBoolean(mailObjects[i].getIsTimingMail());
			writeLong(mailObjects[i].getLastEditTime());
			writeLong(mailObjects[i].getPlanSendTime());
		}
		return true;
	}

}
