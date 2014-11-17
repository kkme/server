package com.hifun.soul.manageweb.user.action;

import java.text.MessageFormat;
import java.util.Collection;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.config.ManageServerConfig;
import com.hifun.soul.manageweb.config.ManageServerConfigManager;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.manageweb.service.ServiceManager;
import com.hifun.soul.proto.services.Services.DoLoginRequest;
import com.hifun.soul.proto.services.Services.DoLoginResponse;
import com.hifun.soul.proto.services.Services.UserRpcService.BlockingInterface;

/**
 * 登录控制器;
 * 
 * @author crazyjohn
 * 
 */

public class LoginAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int serverId;
	private String userName;
	private String password;
	private String loginInfo;

	/**
	 * 登录;
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String doLogin() throws ServiceException {
		if (serverId == 0) {
			return "loginFailed";
		}
		if (userName == null || "".equals(userName)) {
			return "loginFailed";
		}
		if (password == null || "".equals(password)) {
			return "loginFailed";
		}
		// 检查serverId是否合法
		boolean serverIdExists = false;
		ManageServerConfigManager serverConfigManager = ApplicationContext.getApplicationContext().getBean(
				ManageServerConfigManager.class);
		Collection<ManageServerConfig> configList = serverConfigManager.getAllManageServers();
		for (ManageServerConfig config : configList) {
			if (config.getServerId() == serverId) {
				serverIdExists = true;
				if (serverId != serverConfigManager.getCurrentManagerServerId()) {
					serverConfigManager.updateCurrentServerConfig(serverId);
					ApplicationContext.getApplicationContext().getBean(ServiceManager.class).init(config);
				}
				break;
			}
		}
		if (!serverIdExists) {
			loginInfo = "选择的服务器不存在";
			return "loginFailed";
		}
		// 查询是否有此用户
		BlockingInterface userService = Managers.getServiceManager().getUserService();
		DoLoginResponse response = userService.doLogin(null, DoLoginRequest.newBuilder().setUserName(userName)
				.setPassword(password).build());
		if (response == null) {
			loginInfo = "连接服务器失败，请确认管理服务器是否开启";
			return "loginFailed";
		}
		String params = MessageFormat
				.format("userName:{0},password:{1},ip:{2}", userName, password, this.getRemoteIp());
		ManageLogger.log(ManageLogType.USER_LOGIN, PermissionType.USER_LOGIN_OUT, params, response.getResult());
		if (!response.getResult()) {
			loginInfo = "用户名或密码错误";
			return "loginFailed";
		}
		ServletActionContext.getRequest().getSession().setAttribute("user", response.getUser());
		return "success";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getLoginInfo() {
		return loginInfo;
	}
}
