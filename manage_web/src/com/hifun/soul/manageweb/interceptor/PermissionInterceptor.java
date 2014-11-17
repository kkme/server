package com.hifun.soul.manageweb.interceptor;

import org.apache.struts2.ServletActionContext;

import com.hifun.soul.manageweb.permission.PermissionUtil;
import com.hifun.soul.proto.services.Services.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限验证拦截器;
 * 
 * @author crazyjohn
 * 
 */
@SuppressWarnings("serial")
public class PermissionInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 检查此用户是否已经登录
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if (user == null) {
			return "login";
		}
		// 是否有指定的权限
		if (!PermissionUtil.hasPermission(user, invocation.getProxy().getActionName())) {
			return forwardToErrorPage("你没有指定的权限");
		}
		return invocation.invoke();
	}

	private String forwardToErrorPage(String errorInfo) {
		ServletActionContext.getRequest().setAttribute("errorInfo", errorInfo);
		return "error";
	}

}
