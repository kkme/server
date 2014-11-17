package com.hifun.soul.manageserver.service;

import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.manageserver.msg.MGQueryPlayerStateStatistic;
import com.hifun.soul.proto.services.Services.CharacterStatisticRpcService;
import com.hifun.soul.proto.services.Services.LevelCounter;
import com.hifun.soul.proto.services.Services.OccupationCounter;
import com.hifun.soul.proto.services.Services.QueryCharacterDistributeRequest;
import com.hifun.soul.proto.services.Services.QueryCharacterDistributeResponse;
import com.hifun.soul.proto.services.Services.QueryPlayerStateStatisticRequest;
import com.hifun.soul.proto.services.Services.QueryPlayerStateStatisticResponse;

public class CharacterStatisticService extends CharacterStatisticRpcService {
	private ServerSessionManager sessionManager;
	private RpcCallbackManager callbackManager;
	private DBServiceManager dbManager;

	public CharacterStatisticService(DBServiceManager dbManager,ServerSessionManager sessionManager,RpcCallbackManager callbackManager) {
		this.sessionManager = sessionManager;
		this.callbackManager = callbackManager;
		this.dbManager = dbManager;
	}
	@Override
	public void queryPlayerStateStatistic(RpcController controller, QueryPlayerStateStatisticRequest request,
			RpcCallback<QueryPlayerStateStatisticResponse> done) {
		MGQueryPlayerStateStatistic mgMsg = new MGQueryPlayerStateStatistic();
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	
	@Override
	public void queryCharacterDistribute(RpcController controller, QueryCharacterDistributeRequest request,
			RpcCallback<QueryCharacterDistributeResponse> done) {
		List<?> entityList = dbManager.getGameDbService().findByNamedQuery(DataQueryConstants.QUERY_CHARACTER_OCCUPATION_STATISTIC);
		if(entityList == null || entityList.isEmpty()){
			done.run(QueryCharacterDistributeResponse.newBuilder().build());
			return;
		}
		QueryCharacterDistributeResponse.Builder response = QueryCharacterDistributeResponse.newBuilder();
		for(Object obj : entityList){
			Object[] objArray = (Object[])obj;
			OccupationCounter.Builder counter = OccupationCounter.newBuilder();
			counter.setOccupation((Integer)objArray[0]);
			counter.setCount((Long)objArray[1]);
			response.addOccupationCount(counter);
		}
		entityList = dbManager.getGameDbService().findByNamedQuery(DataQueryConstants.QUERY_CHARACTER_LEVEL_STATISTIC);
		if(entityList == null || entityList.isEmpty()){
			done.run(response.build());
			return;
		}
		for(Object obj : entityList){
			Object[] objArray = (Object[])obj;
			LevelCounter.Builder counter = LevelCounter.newBuilder();
			counter.setLevel((Integer)objArray[0]);
			counter.setCount((Long)objArray[1]);
			response.addLevelCount(counter);
		}
		done.run(response.build());
	}

}
