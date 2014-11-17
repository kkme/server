package com.hifun.soul.manageserver.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.msg.GMQueryPlayerStateStatistic;
import com.hifun.soul.proto.services.Services.QueryPlayerStateStatisticResponse;

@Component
public class GMQueryPlayerStateStatisticHandler implements IMessageHandlerWithType<GMQueryPlayerStateStatistic> {
	@Autowired
	private RpcCallbackManager manager;
	@Override
	public short getMessageType() {
		return MessageType.GM_QUERY_PLAYER_STATE_STATISTIC;
	}

	@Override
	public void execute(GMQueryPlayerStateStatistic message) {
		RpcCallback<QueryPlayerStateStatisticResponse> callback = manager.getCallbackContext(message.getContextId());
		QueryPlayerStateStatisticResponse.Builder response = QueryPlayerStateStatisticResponse.newBuilder();
		response.setAuthronizedCount(message.getAuthronizedCount());
		response.setBattlingCount(message.getBattlingCount());
		response.setConnectedCount(message.getConnectedCount());
		response.setEnteringCount(message.getEnteringCount());
		response.setExitingCount(message.getExitingCount());
		response.setGameingCount(message.getGameingCount());
		response.setOtherCount(message.getOtherCount());
		callback.run(response.build());
	}

}
