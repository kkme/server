package com.hifun.soul.manageweb.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.config.ManageServerConfig;
import com.hifun.soul.manageweb.config.ManageServerConfigManager;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.manageweb.runtime.GameServerStatus;
import com.hifun.soul.manageweb.runtime.ManageServerBaseInfo;
import com.hifun.soul.manageweb.runtime.ManageServerInfo;
import com.hifun.soul.manageweb.service.ServiceManager;
import com.hifun.soul.proto.services.Services.ChangeGameServerRequest;
import com.hifun.soul.proto.services.Services.ChangeGameServerResponse;
import com.hifun.soul.proto.services.Services.GameServerInfo;
import com.hifun.soul.proto.services.Services.GetGameServerListRequest;
import com.hifun.soul.proto.services.Services.GetGameServerListResponse;
import com.hifun.soul.proto.services.Services.GetGameServerStatusRequest;
import com.hifun.soul.proto.services.Services.GetGameServerStatusResponse;
import com.hifun.soul.proto.services.Services.StopGameServerRequest;
import com.hifun.soul.proto.services.Services.StopGameServerResponse;
import com.hifun.soul.proto.services.Services.RuntimeRpcService.BlockingInterface;

public class GameServerListAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1398512099693225994L;
	
	private List<GameServerInfo.Builder> gameServerList;
	private int gameServerId;
	private int manageServerId;
	private boolean gameServerState = false;
	private List<ManageServerInfo> manageServerList;
	
	public List<GameServerInfo.Builder> getGameServerList() {
		return gameServerList;
	}


	public void setGameServerList(List<GameServerInfo.Builder> gameServerList) {
		this.gameServerList = gameServerList;
	}

	public int getGameServerId() {
		return gameServerId;
	}

	public void setGameServerId(int gameServerId) {
		this.gameServerId = gameServerId;
	}
	
	public int getManageServerId() {
		return manageServerId;
	}

	public void setManageServerId(int manageServerId) {
		this.manageServerId = manageServerId;
	}

	public boolean isGameServerState() {
		return gameServerState;
	}

	public List<ManageServerInfo> getManageServerList() {
		return manageServerList;
	}


	public String queryGameServerList() throws ServiceException{
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		GetGameServerListResponse response = runtimeService.getGameServerList(null, GetGameServerListRequest.newBuilder().build());
		List<GameServerInfo> serverList = response.getGameServersList();
		ManageLogger.log(ManageLogType.QUERY_GAME_SERVER_LIST, PermissionType.QUERY_GAME_SERVER_LIST, "", true);
		gameServerList = new ArrayList<GameServerInfo.Builder>();
		for(GameServerInfo serverInfo:serverList){
			gameServerList.add(serverInfo.toBuilder());
			if(serverInfo.getConnectionState()){
				this.gameServerId = serverInfo.getServerId();				
			}
		}
		Collections.sort(gameServerList,new Comparator<GameServerInfo.Builder>() {			

			@Override
			public int compare(GameServerInfo.Builder o1, GameServerInfo.Builder o2) {
				return o1.getServerId()-o2.getServerId();
			}
		});		
		return "success";
	}
	
	public String changeGameServer() throws ServiceException{
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		ChangeGameServerRequest.Builder request = ChangeGameServerRequest.newBuilder();
		request.setServerId(gameServerId);
		ChangeGameServerResponse response = runtimeService.changeGameServer(null, request.build());
		ManageLogger.log(ManageLogType.CHANGE_GAME_SERVER, PermissionType.CHANGE_GAME_SERVER, "", response.getResult());
		if(response.getResult())
			return "success";		
		else 
			return "failed";
	}
	
	/**
	 * 停游戏服
	 * @return
	 * @throws ServiceException
	 */
	public String stopGameServer() throws ServiceException{
		int serverId = Integer.parseInt(ServletActionContext.getRequest().getParameter("serverId"));
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		StopGameServerResponse response = runtimeService.stopGameServer(null, StopGameServerRequest.newBuilder().setServerId(serverId).build());
		ManageLogger.log(ManageLogType.STOP_GAME_SERVER, PermissionType.STOP_GAME_SERVER, "", response.getResult());
		if(response.getResult()){
			return "success";
		}else{
			return "failed";
		}
	}
	
	public String queryManageServerList(){
		ManageServerConfigManager serverConfigManager = ApplicationContext.getApplicationContext()
				.getBean(ManageServerConfigManager.class);
		this.manageServerId = serverConfigManager.getCurrentManagerServerId();
		Collection<ManageServerConfig> configList = serverConfigManager.getAllManageServers();
		manageServerList = new ArrayList<ManageServerInfo>();
		for(ManageServerConfig config : configList){
			ManageServerInfo serverInfo = new ManageServerInfo();
			serverInfo.setServerId(config.getServerId());
			serverInfo.setServerName(config.getServerName());
			serverInfo.setHost(config.getHost());			
			if(serverInfo.getServerId() == this.manageServerId){
				gameServerState = getGameServerStatus();
				if(gameServerState){
					serverInfo.setStatus(GameServerStatus.OPENED);
				}else{
					serverInfo.setStatus(GameServerStatus.STOPED);
				}
				serverInfo.setConnectionState(true);
			}else{
				serverInfo.setConnectionState(false);
				serverInfo.setStatus(GameServerStatus.UNKNOWN);
			}
			manageServerList.add(serverInfo);
		}
		ManageLogger.log(ManageLogType.QUERY_MANAGE_SERVER_LIST, PermissionType.QUERY_MANAGE_SERVER_LIST, "", true);
		return "success";
	}
	
	private boolean getGameServerStatus(){
		boolean status = false;
		try {
			GetGameServerStatusResponse response = Managers.getServiceManager().getRuntimeServie().getGameServerStatus(null, GetGameServerStatusRequest.newBuilder().build());
			if(response != null){
				status = response.getStatus();
			}
		} catch (ServiceException e) {			
			e.printStackTrace();
		}
		return status;
	}

	public String changeManageServer(){
		ManageServerConfigManager serverConfigManager = ApplicationContext.getApplicationContext()
				.getBean(ManageServerConfigManager.class);
		ManageServerConfig serverConfig = serverConfigManager.getManageServerConfig(manageServerId);
		if(serverConfig != null){
			serverConfigManager.updateCurrentServerConfig(manageServerId);
			ApplicationContext.getApplicationContext().getBean(ServiceManager.class).init(serverConfig);
		}
		return "success";
	}
	
	private String serverBaseInfoList;
	public String getServerBaseInfoList(){
		return serverBaseInfoList;
	}
	
	public String queryManageServerListByJson(){
		ManageServerConfigManager serverConfigManager = ApplicationContext.getApplicationContext()
				.getBean(ManageServerConfigManager.class);
		Collection<ManageServerConfig> configList = serverConfigManager.getAllManageServers();
		List<ManageServerBaseInfo> serverBaseList = new ArrayList<ManageServerBaseInfo>();
		for(ManageServerConfig config : configList){
			ManageServerBaseInfo baseInfo = new ManageServerBaseInfo();
			baseInfo.setServerId(config.getServerId());
			baseInfo.setServerName(config.getServerName());
			serverBaseList.add(baseInfo);
		}
		serverBaseInfoList = JSONSerializer.toJSON(serverBaseList).toString();
		return "success";
	}

}
