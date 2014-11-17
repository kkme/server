package com.hifun.soul.manageweb.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.DBEntityOperationCounter;
import com.hifun.soul.proto.services.Services.DBQueryCounter;
import com.hifun.soul.proto.services.Services.GetDBEntityOperationListRequest;
import com.hifun.soul.proto.services.Services.GetDBEntityOperationListResponse;
import com.hifun.soul.proto.services.Services.GetRealmInfoRequest;
import com.hifun.soul.proto.services.Services.GetRealmInfoResponse;
import com.hifun.soul.proto.services.Services.RuntimeRpcService.BlockingInterface;
import com.hifun.soul.proto.services.Services.StartExternalServiceRequest;
import com.hifun.soul.proto.services.Services.StopExternalServiceRequest;
import com.hifun.soul.proto.services.Services.StopGameServerRequest;
import com.hifun.soul.proto.services.Services.StopGameServerResponse;

/**
 * 服务器的运行时管理;
 * 
 * @author crazyjohn
 * 
 */
public class RuntimeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5356097801645137169L;

	/**
	 * 获取服务器组信息;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRealmInfo() throws Exception {
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		GetRealmInfoResponse response = runtimeService.getRealmInfo(null,
				GetRealmInfoRequest.newBuilder().build());
		ServletActionContext.getRequest().setAttribute("open",
				response.getOpen());
		ManageLogger.log(ManageLogType.RUN_TIME_GET_REALM_INFO,PermissionType.RUN_TIME_GET_REALM_INFO, "", true);
		return "realmInfo";
	}

	/**
	 * 关闭对外服务;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String stopExternalService() throws Exception {
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		runtimeService.stopExternalService(null, StopExternalServiceRequest
				.newBuilder().build());
		ManageLogger.log(ManageLogType.RUN_TIME_START_EXTERNAL_SERVICE,PermissionType.RUN_TIME_START_EXTERNAL_SERVICE, "", true);
		return "realmInfo";
	}

	/**
	 * 开启对外服务;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String startExternalService() throws Exception {
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		runtimeService.startExternalService(null, StartExternalServiceRequest
				.newBuilder().build());
		ManageLogger.log(ManageLogType.RUN_TIME_STOP_EXTERNAL_SERVICE,PermissionType.RUN_TIME_STOP_EXTERNAL_SERVICE, "", true);
		return "realmInfo";
	}	
	
	
	private List<DBEntityOperationCounter> dbOperationList;
	
	public List<DBEntityOperationCounter> getDbOperationList(){
		return dbOperationList;
	}
	
	private List<DBQueryCounter> dbQueryCounter;	
	
	public List<DBQueryCounter> getDbQueryCounter() {
		return dbQueryCounter;
	}

	public String queryDBEntityOperationInfo() throws ServiceException{
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		GetDBEntityOperationListResponse response = 
		runtimeService.getDBEntityOperationList(null, GetDBEntityOperationListRequest
				.newBuilder().build());
		dbOperationList = response.getOperationInfoList();
		dbQueryCounter = response.getQueryCounterList();
		ManageLogger.log(ManageLogType.QUERY_DB_ENTITY_OPERATION_INFO,PermissionType.QUERY_DB_ENTITY_OPERATION_INFO, "", true);
		return "success";
	}
	
	/**
	 * 停游戏服
	 * @return
	 * @throws ServiceException
	 */
	public String stopGameServer() throws ServiceException{
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		StopGameServerResponse response = runtimeService.stopGameServer(null, StopGameServerRequest.newBuilder().build());
		ManageLogger.log(ManageLogType.QUERY_DB_ENTITY_OPERATION_INFO,PermissionType.QUERY_DB_ENTITY_OPERATION_INFO, "", true);
		if(response.getResult()){
			return "success";
		}else{
			return "failed";
		}
	}

}
