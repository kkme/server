package com.hifun.soul.manageweb.action;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.QueryLoginWallStateRequest;
import com.hifun.soul.proto.services.Services.QueryLoginWallStateResponse;
import com.hifun.soul.proto.services.Services.RuntimeRpcService.BlockingInterface;
import com.hifun.soul.proto.services.Services.UpdateLoginWallStateRequest;

public class LoginWallAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1305204939072287649L;
	
	private boolean enabled;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String queryLoginWallState() throws ServiceException{
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		QueryLoginWallStateResponse response = runtimeService.queryLoginWallState(null, QueryLoginWallStateRequest.newBuilder().build());
		this.enabled = response.getEnabled();
		ManageLogger.log(ManageLogType.QUERY_LOGIN_WALL_STATE,PermissionType.QUERY_LOGIN_WALL_STATE, "", true);
		return "success";
	}
	
	public String updateLoginWallState() throws ServiceException{
		BlockingInterface runtimeService = Managers.getServiceManager()
				.getRuntimeServie();
		QueryLoginWallStateResponse response = runtimeService.updateLoginWallState(null, UpdateLoginWallStateRequest.newBuilder().setEnabled(enabled).build());
		this.enabled = response.getEnabled();
		ManageLogger.log(ManageLogType.UPDATE_LOGIN_WALL_STATE,PermissionType.UPDATE_LOGIN_WALL_STATE, "", true);
		return "success";
	}
}
