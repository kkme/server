package com.hifun.soul.manageserver.msg.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.msg.GMKickOffAllCharacter;
import com.hifun.soul.proto.services.Services.KickOffAllCharacterResponse;

@Component
public class GMKickOffAllCharacterHandler implements IMessageHandlerWithType<GMKickOffAllCharacter> {
	@Autowired
	private RpcCallbackManager manager;
	private Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	
	@Override
	public short getMessageType() {		
		return MessageType.GM_KICK_OFF_ALL_CHARACTER;
	}

	@Override
	public void execute(GMKickOffAllCharacter message) {
		RpcCallback<KickOffAllCharacterResponse> callback = manager
				.getCallbackContext(message.getContextId());
		if (callback == null) {
			logger.error(String.format("No such rpcCallback, contextId: %d",
					message.getContextId()));
			return;
		}
		callback.run(KickOffAllCharacterResponse.newBuilder()
				.setResult(message.getResult()).build());		
	}

}
