package com.hifun.soul.manageweb.user.action;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.ManagerUpdateUserPasswordRequest;
import com.hifun.soul.proto.services.Services.ManagerUpdateUserPasswordResponse;
import com.hifun.soul.proto.services.Services.UpdateUserPasswordRequest;
import com.hifun.soul.proto.services.Services.UpdateUserPasswordResponse;
import com.hifun.soul.proto.services.Services.User;
import com.hifun.soul.proto.services.Services.UserRpcService.BlockingInterface;

public class UpdateUserPasswordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 210775502840183177L;
	
	private int userId;
	private String userName;
	private String oldPassword;
	private String newPassword;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String updateUserPasswordForward(){
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		this.userId = user.getId();
		this.userName = user.getUserName();
		return "success";
	}

	public String updateUserPassword() throws ServiceException, IOException {
		// 更新用户信息;
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");		
		int userId = this.userId =  user.getId();
		this.userName = user.getUserName();
		this.oldPassword = ServletActionContext.getRequest().getParameter("oldPassword");
		this.newPassword = ServletActionContext.getRequest().getParameter("newPassword");
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		UpdateUserPasswordResponse response = userService.updateUserPassword(null,
				UpdateUserPasswordRequest.newBuilder().setUserId(userId).setOldPassword(oldPassword).setNewPassword(newPassword).build());
		String params = MessageFormat.format("userId:{0}", userId);
		ManageLogger.log(ManageLogType.USER_PASSWORD_UPDATE,PermissionType.USER_PASSWORD_UPDATE, params, response.getResult());		
		if(response.getResult()){
			return "success";
		}
		else{
			ServletActionContext.getRequest().setAttribute("errorInfo", "保存失败。");
			return "error";
		}
	}
	
	public String managerUpdateUserPasswordForward(){		
		this.userId = Integer.parseInt(ServletActionContext.getRequest().getParameter("userId"));
		this.userName = ServletActionContext.getRequest().getParameter("userName");
		return "success";
	}
	
	public String managerUpdateUserPassword() throws ServiceException, IOException  {
		// 更新用户信息;		
		this.userId = Integer.parseInt(ServletActionContext.getRequest().getParameter("userId"));
		this.newPassword = ServletActionContext.getRequest().getParameter("newPassword");
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		ManagerUpdateUserPasswordResponse response = userService.managerUpdateUserPassword(null,
				ManagerUpdateUserPasswordRequest.newBuilder().setUserId(userId).setNewPassword(newPassword).build());
		String params = MessageFormat.format("userId:{0}", userId);
		ManageLogger.log(ManageLogType.MANAGER_UPDATE_USER_PASSWORD,PermissionType.MANAGER_UPDATE_USER_PASSWORD, params, response.getResult());
		if(response.getResult()){
			return "success";
		}
		else{
			ServletActionContext.getRequest().setAttribute("errorInfo", "保存失败。");
			return "error";
		}
	}
	
	public void validateUpdateUserPassword(){
		if(newPassword==null || newPassword.length() <6){
			this.addFieldError("newPassword", "密码不能小于6位");
		}
		if(oldPassword == null){
			this.addFieldError("oldPassword", "旧密码不能为空");
		}
	}

}
