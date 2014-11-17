package com.hifun.soul.manageweb.user.action;

import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.manageweb.permission.PermissionUtil;
import com.hifun.soul.proto.services.Services.AddUserRequest;
import com.hifun.soul.proto.services.Services.AddUserResponse;
import com.hifun.soul.proto.services.Services.DeleteUserRequest;
import com.hifun.soul.proto.services.Services.GetUserByIdRequest;
import com.hifun.soul.proto.services.Services.GetUserByIdResponse;
import com.hifun.soul.proto.services.Services.GetUserListRequest;
import com.hifun.soul.proto.services.Services.GetUserListResponse;
import com.hifun.soul.proto.services.Services.UpdateUserPermissionRequest;
import com.hifun.soul.proto.services.Services.UpdateUserPermissionResponse;
import com.hifun.soul.proto.services.Services.User;
import com.hifun.soul.proto.services.Services.UserRpcService.BlockingInterface;

/**
 * 用户控制器;
 * 
 * @author crazyjohn
 * 
 */
public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 742916750134970539L;

	/**
	 * 获取用户列表;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserList() throws Exception {
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		GetUserListResponse response = userService.getUserList(null,
				GetUserListRequest.newBuilder().build());
		ServletActionContext.getRequest().setAttribute("userList",
				response.getUserList());
		ManageLogger.log(ManageLogType.USER_LIST,PermissionType.USER_LIST, "", true);
		return "userList";
	}

	/**
	 * 添加用户;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUser() throws Exception {
		String userName = ServletActionContext.getRequest().getParameter(
				"userName");
		String password = ServletActionContext.getRequest().getParameter(
				"password");
		if(password.length()<6){
			return "addFailed";
		}
		String[] permissions = ServletActionContext.getRequest()
				.getParameterValues("permission");
		String initPermissions = PermissionUtil
				.generatePermissions(permissions);
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		AddUserResponse response = userService.addUser(null, AddUserRequest
				.newBuilder().setUserName(userName).setPassword(password)
				.setPermissions(initPermissions).build());
		String params = MessageFormat.format("userName:{0},permission:{1}", userName,initPermissions);
		ManageLogger.log(ManageLogType.USER_ADD,PermissionType.USER_ADD, params, true);
		if (!response.getResult()) {
			return "addFailed";
		}
		return "addUserSuccess";
	}

	/**
	 * 添加用户转向;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUserForward() throws Exception {
		return "addUserForward";
	}

	public String deleteUser() throws Exception {
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		String id = ServletActionContext.getRequest().getParameter("userId");
		if (id == null) {			
			ServletActionContext.getRequest().setAttribute("error", "删除失败!");
			return "error";
		}
		int userId = Integer.parseInt(id);
		userService.deleteUser(null,
				DeleteUserRequest.newBuilder().setUserId(userId).build());
		ManageLogger.log(ManageLogType.USER_DELETE,PermissionType.USER_DELETE, "userId:"+id, true);
		return "userList";
	}

	/**
	 * 注销登录;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		// 移除用户信息
		ServletActionContext.getRequest().getSession().removeAttribute("user");
		return "login";
	}

	/**
	 * 更新用户;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateUserPermission() throws Exception {
		// 更新用户信息;
		String sId = ServletActionContext.getRequest().getParameter("userId");
		Integer userId = Integer.parseInt(sId);			
		// 取出权限
		String[] arr = ServletActionContext.getRequest().getParameterValues(
				"permission");
		String permissions = PermissionUtil.generatePermissions(arr);
		
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		UpdateUserPermissionResponse response = userService.updateUserPermission(null,
				UpdateUserPermissionRequest.newBuilder().setUserId(userId).setPermissions(permissions).build());
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");	
		if(user != null && user.getId()==userId && response.getResult()){
			user = user.toBuilder().setPermissions(permissions).build();
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
		}
		String params = MessageFormat.format("userId:{0},permission:{1}", userId,permissions);
		ManageLogger.log(ManageLogType.USER_PERMISSION_UPDATE,PermissionType.USER_PERMISSION_UPDATE, params, response.getResult());
		return "userList";
	}

	/**
	 * 更新用户转向;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateUserPermissionForward() throws Exception {
		Integer userId = Integer.parseInt(ServletActionContext.getRequest()
				.getParameter("userId"));
		BlockingInterface userService = Managers.getServiceManager()
				.getUserService();
		GetUserByIdResponse response = userService.getUserById(null,
				GetUserByIdRequest.newBuilder().setUserId(userId).build());
		if (!response.getResult()) {
			return "userList";
		}
		ServletActionContext.getRequest().setAttribute("user", response.getUser());
		return "success";
	}
}
