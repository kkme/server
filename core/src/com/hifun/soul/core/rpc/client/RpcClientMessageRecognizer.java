package com.hifun.soul.core.rpc.client;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageParseException;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.recognizer.BaseMessageRecognizer;
import com.hifun.soul.core.rpc.msg.RpcResponseMessage;

/**
 * RpcClient消息识别器
 * 
 * @author crazyjohn
 * 
 */
public class RpcClientMessageRecognizer extends BaseMessageRecognizer {

	@Override
	public IMessage createMessageImpl(short type) throws MessageParseException {
		if (type == MessageType.RPC_RESPONSE) {
			return new RpcResponseMessage();
		} else {
			throw new MessageParseException("Unknown message type: " + type);
		}
	}

}
