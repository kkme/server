package com.hifun.soul.core.rpc.msg;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.MinaSession;

/**
 * RPC错误消息;
 * 
 * @author crazyjohn
 * 
 */
public class RpcErrorMessage extends BaseSessionMessage<MinaSession> {
	/** 错误信息 */
	private String errorInfo;

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.RPC_ERROR;
	}

	@Override
	protected boolean readImpl() {
		this.errorInfo = readString();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeString(this.errorInfo);
		return true;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
