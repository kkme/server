package com.hifun.soul.manageserver.msg.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.msg.GMUpdateMail;
import com.hifun.soul.proto.services.Services.UpdateTimingMailResponse;

@Component
public class GMUpdateMailHandler implements IMessageHandlerWithType<GMUpdateMail> {
	@Autowired
	private RpcCallbackManager manager;
	private Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	@Override
	public short getMessageType() {
		return MessageType.GM_UPDATE_MAIL;
	}

	@Override
	public void execute(GMUpdateMail message) {
		RpcCallback<UpdateTimingMailResponse> callback = manager
				.getCallbackContext(message.getContextId());
		if (callback == null) {
			logger.error(String.format("No such rpcCallback, contextId: %d",
					message.getContextId()));
			return;
		}
		callback.run(UpdateTimingMailResponse.newBuilder()
				.setResult(message.getResult()).build());
	}

}
