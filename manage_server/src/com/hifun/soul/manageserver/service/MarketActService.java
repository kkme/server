package com.hifun.soul.manageserver.service;

import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.MarketActivitySettingEntity;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.manageserver.msg.MGUpdateMarketActSetting;
import com.hifun.soul.proto.services.Services.MarketActRpcService;
import com.hifun.soul.proto.services.Services.MarketActivitySetting;
import com.hifun.soul.proto.services.Services.QueryMarketActListRequest;
import com.hifun.soul.proto.services.Services.QueryMarketActListResponse;
import com.hifun.soul.proto.services.Services.UpdateMarketActSettingRequest;
import com.hifun.soul.proto.services.Services.UpdateMarketActSettingResponse;

public class MarketActService extends MarketActRpcService {

	private ServerSessionManager sessionManager;
	private DBServiceManager dbManager;
	private RpcCallbackManager callbackManager;

	public MarketActService(DBServiceManager dbManager,ServerSessionManager sessionManager,RpcCallbackManager callbackManager) {
		this.dbManager = dbManager;
		this.sessionManager = sessionManager;
		this.callbackManager = callbackManager;
	}
	
	@Override
	public void queryMarketActList(RpcController controller, QueryMarketActListRequest request,
			RpcCallback<QueryMarketActListResponse> done) {
		QueryMarketActListResponse.Builder response = QueryMarketActListResponse.newBuilder();
		List<?> result = dbManager.getGameDbService().findByNamedQuery(DataQueryConstants.QUERY_MARKET_ACTIVITY_SETTING);
		if(result == null || result.isEmpty()){
			done.run(response.build());
			return;
		}
		for(Object obj : result){
			MarketActivitySettingEntity entity = (MarketActivitySettingEntity)obj;
			MarketActivitySetting.Builder setting = MarketActivitySetting.newBuilder();
			setting.setMarketActType(entity.getType());
			setting.setEnable(entity.isEnable());
			setting.setRoleLevel(entity.getRoleLevel());
			setting.setVipLevel(entity.getVipLevel());
			response.addSettings(setting);
		}
		done.run(response.build());
	}

	@Override
	public void updateMarketActSetting(RpcController controller, UpdateMarketActSettingRequest request,
			RpcCallback<UpdateMarketActSettingResponse> done) {
		MGUpdateMarketActSetting mgMsg = new MGUpdateMarketActSetting();
		mgMsg.setMarketActType(request.getMarketActType());
		mgMsg.setEnable(request.getEnable());
		mgMsg.setRoleLevel(request.getRoleLevel());
		mgMsg.setVipLevel(request.getVipLevel());
		callbackManager.registerCallback(mgMsg,done);
		sessionManager.getGameServer().write(mgMsg);
	}

}
