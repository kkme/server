package com.hifun.soul.manageserver.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;
import com.hifun.soul.proto.services.Services.Bulletin;

@Component
public class MGBulletinAdd extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{

	/** 公告实体 */
	Bulletin bulletin;
	/** 上下文id */
	private long contextId; 
	
	public long getContextId() {
		return contextId;
	}

	public void setContextId(long contextId) {
		this.contextId = contextId;
	}

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}

	@Override
	public short getType() {
		return MessageType.MG_BULLETIN_ADD;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean readImpl() {	
		contextId = readLong();
		Bulletin.Builder  builder = Bulletin.newBuilder();
		builder.setId(readInteger());
		builder.setBulletinType(readInteger());
		builder.setLevel(readInteger());
		builder.setShowTime(readInteger());
		builder.setContent(readString());
		builder.setPublishTime(readLong());
		builder.setValid(readBoolean());
		builder.setStartDate(readLong());
		builder.setEndDate(readLong());
		builder.setStartTime(readLong());
		builder.setEndTime(readLong());
		builder.setLastPublishTime(readLong());
		builder.setPublishInterval(readInteger());	
		builder.setCreateTime(readLong());
		this.bulletin = builder.build();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeInteger(bulletin.getId());
		writeInteger(bulletin.getBulletinType());
		writeInteger(bulletin.getLevel());
		writeInteger(bulletin.getShowTime());
		writeString(bulletin.getContent());
		writeLong(bulletin.getPublishTime());
		writeBoolean(bulletin.getValid());
		writeLong(bulletin.getStartDate());
		writeLong(bulletin.getEndDate());
		writeLong(bulletin.getStartTime());
		writeLong(bulletin.getEndTime());
		writeLong(bulletin.getLastPublishTime());
		writeInteger(bulletin.getPublishInterval());
		writeLong(bulletin.getCreateTime());
		return true;
	}

}
