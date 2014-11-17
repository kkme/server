package com.hifun.soul.manageserver.msg.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.manageserver.Loggers;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.model.DBEntityOperationInfo;
import com.hifun.soul.manageserver.msg.GMDBEntityOperationInfo;
import com.hifun.soul.proto.services.Services.DBEntityOperationCounter;
import com.hifun.soul.proto.services.Services.DBQueryCounter;
import com.hifun.soul.proto.services.Services.GetDBEntityOperationListResponse;

@Component
public class GMDBEntityOperationInfoHandler implements IMessageHandlerWithType<GMDBEntityOperationInfo> {
	@Autowired
	private RpcCallbackManager manager;
	private Logger logger = Loggers.MANAGE_MAIN_LOGGER;
	@Override
	public short getMessageType() {
		return MessageType.GM_DB_ENTITY_OPERATION_INFO;
	}

	@Override
	public void execute(GMDBEntityOperationInfo message) {
		RpcCallback<GetDBEntityOperationListResponse> callback = manager
				.getCallbackContext(message.getContextId());
		if (callback == null) {
			logger.error(String.format("No such rpcCallback, contextId: %d",
					message.getContextId()));
			return;
		}
		GetDBEntityOperationListResponse.Builder builder = GetDBEntityOperationListResponse.newBuilder();
		DBEntityOperationInfo[] infoArray = message.getOperationInfos();
		Arrays.sort(infoArray,new Comparator<DBEntityOperationInfo>(){

			@Override
			public int compare(DBEntityOperationInfo arg0, DBEntityOperationInfo arg1) {
				int totalA = arg0.getUpdateCount()+arg0.getInsertCount()+arg0.getDeleteCount()+arg0.getGetCount()+arg0.getQueryCount();
				int totalB = arg1.getUpdateCount()+arg1.getInsertCount()+arg1.getDeleteCount()+arg1.getGetCount()+arg1.getQueryCount();
				return totalB-totalA;
			}
			
		});

		for(DBEntityOperationInfo info : infoArray){
			DBEntityOperationCounter.Builder counter = DBEntityOperationCounter.newBuilder();
			counter.setClassName(info.getClassName());
			counter.setUpdateCount(info.getUpdateCount());
			counter.setInsertCount(info.getInsertCount());
			counter.setDeleteCount(info.getDeleteCount());
			counter.setGetCount(info.getGetCount());
			counter.setQueryCount(info.getQueryCount());
			builder.addOperationInfo(counter);
		}
		
		Map<String, Integer> queryInfos = message.getDbQueryTimes();
		List<Map.Entry<String,Integer>> mappingList = new ArrayList<Map.Entry<String,Integer>>(queryInfos.entrySet());
		Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {				
				return o2.getValue() - o1.getValue();
			}
			
		});
		for(Map.Entry<String,Integer> entry : mappingList){
			DBQueryCounter.Builder counter = DBQueryCounter.newBuilder();
			counter.setQueryName(entry.getKey());
			counter.setCount(entry.getValue());
			builder.addQueryCounter(counter);
		}
		
		callback.run(builder.build());
	}

}
