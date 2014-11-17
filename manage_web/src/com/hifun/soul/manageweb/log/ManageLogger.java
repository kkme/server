package com.hifun.soul.manageweb.log;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.manageweb.permission.PermissionUtil;
import com.hifun.soul.manageweb.service.ServiceManager;
import com.hifun.soul.proto.services.LogServices.AddOperationLogRequest;
import com.hifun.soul.proto.services.LogServices.OperationLog;
import com.hifun.soul.proto.services.Services.User;
import com.hifun.soul.proto.services.LogServices.ManageLogRpcService;

/**
 * 用户操作记录器
 * @author magicstone
 *
 */
public class ManageLogger implements Runnable{
	/** 日志队列 */
	private static BlockingQueue<OperationLogKeyInfo> operationList = new LinkedBlockingQueue<OperationLogKeyInfo>();	
	/** 日志线程是否运行标识 */
	private static boolean isStop = true;
	/** 日志线程 */
	private static Thread manageLogThread= null;
	private static ServiceManager serviceManager;	
	
	public static void start(){
		manageLogThread = new Thread(new ManageLogger(),"manageWebLoggerThread");
		isStop = false;
		manageLogThread.start();		
	}
	
	public static void stop(){
		if(manageLogThread != null){
			isStop = true;
			manageLogThread = null;
		}
	}

	@Override
	public void run() {
		while (!isStop) {
			if (operationList.isEmpty()) {
				continue;
			}
			OperationLogKeyInfo logInfo = null;
			try {
				logInfo = operationList.take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (logInfo == null) {
				continue;
			}
			User user = logInfo.getUser();
			ManageLogType logType = logInfo.getLogType();
			PermissionType permission = logInfo.getPermissionType();
			boolean hasPermission = false;
			if(permission != null){
				hasPermission = PermissionUtil.hasPermission(user, permission.getAction());
			}
			ManageLogRpcService.BlockingInterface logService = getLogService();
			AddOperationLogRequest.Builder request = AddOperationLogRequest.newBuilder();
			OperationLog.Builder logBuilder = OperationLog.newBuilder();
			logBuilder.setId(1);
			logBuilder.setUserId(user.getId());
			logBuilder.setUserName(user.getUserName());
			logBuilder.setOperationType(logType.getLogType());
			logBuilder.setOperationText(logType.getDesc());
			logBuilder.setHasPermission(hasPermission);
			logBuilder.setParam(logInfo.getParams());
			logBuilder.setResult(logInfo.getResult());
			logBuilder.setOperateTime(new Date().getTime());
			request.setOperationLog(logBuilder.build());
			try {
				logService.addOperationLog(null, request.build());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(ManageLogType logType,PermissionType permission,String params, boolean result) {
		if(isStop){
			return;
		}
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if (user == null) {
			return;
		}
		OperationLogKeyInfo logKeyInfo = new ManageLogger().new OperationLogKeyInfo(user,logType,permission,params,result);
		try {
			operationList.put(logKeyInfo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static ManageLogRpcService.BlockingInterface getLogService(){		
		if(serviceManager == null){
			serviceManager = ApplicationContext.getApplicationContext().getBean(ServiceManager.class);
		}
		ManageLogRpcService.BlockingInterface manageLogService = serviceManager.getManageLogService();
		return manageLogService;
	}
	
	
	class OperationLogKeyInfo{
		User user;
		ManageLogType logType;
		PermissionType permissionType;
		String params;
		boolean result = true;
		public OperationLogKeyInfo(){}
		public OperationLogKeyInfo(User user,ManageLogType logType,PermissionType permissionType,String params,boolean result){
			this.user = user;
			this.logType = logType;
			this.permissionType = permissionType;
			this.params = params;
			this.result = result;
		}
		
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}		
		public ManageLogType getLogType() {
			return logType;
		}
		public void setLogType(ManageLogType logType) {
			this.logType = logType;
		}
		public PermissionType getPermissionType() {
			return permissionType;
		}
		public void setPermissionType(PermissionType permissionType) {
			this.permissionType = permissionType;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
		public boolean getResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
	}
}
