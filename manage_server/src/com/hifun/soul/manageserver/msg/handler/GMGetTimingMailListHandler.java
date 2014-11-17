package com.hifun.soul.manageserver.msg.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.msg.GMGetTimingMailList;
import com.hifun.soul.proto.services.Services.MailObject;
import com.hifun.soul.proto.services.Services.QueryTimingMailListResponse;

@Component
public class GMGetTimingMailListHandler implements IMessageHandlerWithType<GMGetTimingMailList> {
	@Autowired
	private RpcCallbackManager manager;
	private Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	@Override
	public short getMessageType() {		
		return MessageType.GM_GET_TIMING_MAIL_LIST;
	}

	@Override
	public void execute(GMGetTimingMailList message) {
		RpcCallback<QueryTimingMailListResponse> callback = manager
				.getCallbackContext(message.getContextId());
		if (callback == null) {
			logger.error(String.format("No such rpcCallback, contextId: %d",
					message.getContextId()));
			return;
		}
		QueryTimingMailListResponse.Builder response = QueryTimingMailListResponse.newBuilder();
		response.setTotalCount(message.getMailObjects().length);
		for(MailObject mailObj : message.getMailObjects()){
			response.addMails(mailObj);
		}		
		callback.run(response.build());
	}

}
