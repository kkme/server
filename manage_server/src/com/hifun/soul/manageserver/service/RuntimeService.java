package com.hifun.soul.manageserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.core.session.ServerSession;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.manageserver.config.GameServerConfig;
import com.hifun.soul.manageserver.config.ManageServerConfigManager;
import com.hifun.soul.manageserver.msg.MGDBEntityOperationInfo;
import com.hifun.soul.manageserver.msg.MGGetLoginWallState;
import com.hifun.soul.manageserver.msg.MGGetRealmInfoMessage;
import com.hifun.soul.manageserver.msg.MGStartExternalServiceMessage;
import com.hifun.soul.manageserver.msg.MGStopExternalServiceMessage;
import com.hifun.soul.manageserver.msg.MGStopServer;
import com.hifun.soul.manageserver.msg.MGUpdateLoginWallState;
import com.hifun.soul.proto.services.Services.ChangeGameServerRequest;
import com.hifun.soul.proto.services.Services.ChangeGameServerResponse;
import com.hifun.soul.proto.services.Services.CheckConnectionRequest;
import com.hifun.soul.proto.services.Services.CheckConnectionResponse;
import com.hifun.soul.proto.services.Services.GameServerInfo;
import com.hifun.soul.proto.services.Services.GetDBEntityOperationListRequest;
import com.hifun.soul.proto.services.Services.GetDBEntityOperationListResponse;
import com.hifun.soul.proto.services.Services.GetGameServerListRequest;
import com.hifun.soul.proto.services.Services.GetGameServerListResponse;
import com.hifun.soul.proto.services.Services.GetGameServerStatusRequest;
import com.hifun.soul.proto.services.Services.GetGameServerStatusResponse;
import com.hifun.soul.proto.services.Services.GetRealmInfoRequest;
import com.hifun.soul.proto.services.Services.GetRealmInfoResponse;
import com.hifun.soul.proto.services.Services.QueryLoginWallStateRequest;
import com.hifun.soul.proto.services.Services.QueryLoginWallStateResponse;
import com.hifun.soul.proto.services.Services.RuntimeRpcService;
import com.hifun.soul.proto.services.Services.StartExternalServiceRequest;
import com.hifun.soul.proto.services.Services.StartExternalServiceResponse;
import com.hifun.soul.proto.services.Services.StopExternalServiceRequest;
import com.hifun.soul.proto.services.Services.StopExternalServiceResponse;
import com.hifun.soul.proto.services.Services.StopGameServerRequest;
import com.hifun.soul.proto.services.Services.StopGameServerResponse;
import com.hifun.soul.proto.services.Services.UpdateLoginWallStateRequest;

/**
 * 运行时管理服务;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class RuntimeService extends RuntimeRpcService {
	@Autowired
	private ServerSessionManager sessionManager;
	@Autowired
	private RpcCallbackManager callbackManager;
	@Autowired
	private DBServiceManager dbServiceManager;
	@Autowired
	private ManageServerConfigManager configManager;

	@Override
	public void getRealmInfo(RpcController controller,
			GetRealmInfoRequest request, RpcCallback<GetRealmInfoResponse> done) {
		MGGetRealmInfoMessage getRealmMsg = new MGGetRealmInfoMessage();
		callbackManager.registerCallback(getRealmMsg, done);
		sessionManager.getGameServer().write(getRealmMsg);
	}

	@Override
	public void startExternalService(RpcController controller,
			StartExternalServiceRequest request,
			RpcCallback<StartExternalServiceResponse> done) {
		MGStartExternalServiceMessage startMsg = new MGStartExternalServiceMessage();
		sessionManager.getGameServer().write(startMsg);
		done.run(StartExternalServiceResponse.newBuilder().build());

	}

	@Override
	public void stopExternalService(RpcController controller,
			StopExternalServiceRequest request,
			RpcCallback<StopExternalServiceResponse> done) {
		MGStopExternalServiceMessage stopMsg = new MGStopExternalServiceMessage();
		sessionManager.getGameServer().write(stopMsg);
		done.run(StopExternalServiceResponse.newBuilder().build());

	}

	@Override
	public void changeGameServer(RpcController controller, ChangeGameServerRequest request,
			RpcCallback<ChangeGameServerResponse> done) {
		int serverId = request.getServerId();
		boolean result = false;
		if (configManager.getGameServerConfigs().containsKey(serverId)) {
			dbServiceManager.changeServer(serverId);
			result = sessionManager.changeCurrentGameServer(serverId);
		}
		done.run(ChangeGameServerResponse.newBuilder().setResult(result).build());
	}

	@Override
	public void getGameServerList(RpcController controller, GetGameServerListRequest request,
			RpcCallback<GetGameServerListResponse> done) {	
		GetGameServerListResponse.Builder response = GetGameServerListResponse.newBuilder();
		List<Integer> needCheckServers = new ArrayList<Integer>();
		for(GameServerConfig gameServerConfig : configManager.getGameServerConfigs().values()){
			GameServerInfo.Builder gameServerInfo = GameServerInfo.newBuilder();
			gameServerInfo.setServerId(gameServerConfig.getServerId());
			gameServerInfo.setServerName(gameServerConfig.getServerName());
			if(sessionManager.getCurrenctGameServerId() == gameServerConfig.getServerId()){
				gameServerInfo.setConnectionState(true);
			}else{			
				gameServerInfo.setConnectionState(false);
			}
			ServerSession gameServer = sessionManager.getGameServer(gameServerInfo.getServerId());			
			if(gameServer == null){
				//重连检查游戏服务器状态
				needCheckServers.add(gameServerInfo.getServerId());			
				gameServerInfo.setRunState(sessionManager.getGameServer(gameServerInfo.getServerId())!=null);
			}else{
				gameServerInfo.setRunState(true);
			}
			response.addGameServers(gameServerInfo);
		}		
		done.run(response.build());
		for(Integer serverId:needCheckServers){
			sessionManager.openConnection(serverId);
		}
	}

	@Override
	public void getDBEntityOperationList(RpcController controller, GetDBEntityOperationListRequest request,
			RpcCallback<GetDBEntityOperationListResponse> done) {
		MGDBEntityOperationInfo mgMsg = new MGDBEntityOperationInfo();
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	@Override
	public void checkConnection(RpcController controller, CheckConnectionRequest request,
			RpcCallback<CheckConnectionResponse> done) {
		done.run(CheckConnectionResponse.newBuilder().setState(true).build());
	}

	@Override
	public void stopGameServer(RpcController controller, StopGameServerRequest request,
			RpcCallback<StopGameServerResponse> done) {
		MGStopServer mgMsg = new MGStopServer();
		sessionManager.getGameServer().write(mgMsg);
		done.run(StopGameServerResponse.newBuilder().setResult(true).build());
	}

	@Override
	public void queryLoginWallState(RpcController controller, QueryLoginWallStateRequest request,
			RpcCallback<QueryLoginWallStateResponse> done) {
		MGGetLoginWallState mgMsg = new MGGetLoginWallState();
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);		
	}

	@Override
	public void updateLoginWallState(RpcController controller, UpdateLoginWallStateRequest request,
			RpcCallback<QueryLoginWallStateResponse> done) {
		MGUpdateLoginWallState mgMsg = new MGUpdateLoginWallState();
		mgMsg.setEnable(request.getEnabled());
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);	
		
	}

	@Override
	public void getGameServerStatus(RpcController controller, GetGameServerStatusRequest request,
			RpcCallback<GetGameServerStatusResponse> done) {
		boolean result = false;
		ServerSession gameServer = sessionManager.getGameServer();			
		if(gameServer == null){			
			result = false;			
		}else{
			result = true;
		}
		done.run(GetGameServerStatusResponse.newBuilder().setStatus(result).build());
		if(!result){
			sessionManager.openConnection();
		}
	}

}
