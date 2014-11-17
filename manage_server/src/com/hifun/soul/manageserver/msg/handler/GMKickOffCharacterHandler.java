package com.hifun.soul.manageserver.msg.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.msg.GMKickOffCharacter;
import com.hifun.soul.proto.services.Services.KickOffCharacterResponse;

@Component
public class GMKickOffCharacterHandler implements IMessageHandlerWithType<GMKickOffCharacter> {
	@Autowired
	private RpcCallbackManager manager;
	private Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	
	@Override
	public short getMessageType() {		
		return MessageType.GM_KICK_OFF_CHARACTER;
	}

	@Override
	public void execute(GMKickOffCharacter message) {
		RpcCallback<KickOffCharacterResponse> callback = manager
				.getCallbackContext(message.getContextId());
		if (callback == null) {
			logger.error(String.format("No such rpcCallback, contextId: %d",
					message.getContextId()));
			return;
		}
		callback.run(KickOffCharacterResponse.newBuilder()
				.setResult(message.getResult()).build());		
	}

}
