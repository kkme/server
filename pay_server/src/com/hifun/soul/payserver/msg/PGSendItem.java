package com.hifun.soul.payserver.msg;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.payserver.net.PSClientSession;

/**
 * 发货消息;
 * 
 * @author crazyjohn
 * 
 */
public class PGSendItem extends BaseSessionMessage<PSClientSession> {
	private String jsonProperties;

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.PG_SEND_ITEM;
	}

	@Override
	protected boolean readImpl() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean writeImpl() {
		this.writeString(jsonProperties);
		return true;
	}

	public String getJsonProperties() {
		return jsonProperties;
	}

	public void setJsonProperties(String jsonProperties) {
		this.jsonProperties = jsonProperties;
	}

}
