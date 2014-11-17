package com.hifun.soul.core.rpc.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.MinaSession;

/**
 * RPC请求消息;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class RpcRequestMessage extends BaseSessionMessage<MinaSession> {
	/** 服务标识 */
	private String serviceIndentifier;
	/** 方法标识 */
	private String methodIndentifier;
	/** 参数 */
	private byte[] params;

	public String getServiceIndentifier() {
		return serviceIndentifier;
	}

	public void setServiceIndentifier(String serviceIndentifier) {
		this.serviceIndentifier = serviceIndentifier;
	}

	public String getMethodIndentifier() {
		return methodIndentifier;
	}

	public void setMethodIndentifier(String methodIndentifier) {
		this.methodIndentifier = methodIndentifier;
	}

	public byte[] getParams() {
		return params;
	}

	public void setParams(byte[] params) {
		this.params = params;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getType() {
		return MessageType.RPC_REQUEST;
	}

	@Override
	protected boolean readImpl() {
		this.serviceIndentifier = readString();
		this.methodIndentifier = readString();
		this.params = readByteArray();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeString(this.serviceIndentifier);
		writeString(this.methodIndentifier);
		writeByteArray(this.params);
		return true;
	}

}
