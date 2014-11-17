package com.hifun.soul.core.rpc.msg;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.MinaSession;

/**
 * RPC响应消息;
 * 
 * @author crazyjohn
 * 
 */
public class RpcResponseMessage extends BaseSessionMessage<MinaSession> {
	/** 返回值 */
	private byte[] returnValues;

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.RPC_RESPONSE;
	}

	@Override
	protected boolean readImpl() {
		this.returnValues = readByteArray();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeByteArray(this.returnValues);
		return true;
	}

	public byte[] getReturnValues() {
		if (returnValues == null) {
			return new byte[0];
		}
		return returnValues;
	}

	public void setReturnValues(byte[] returnValues) {
		this.returnValues = returnValues;
	}

}
