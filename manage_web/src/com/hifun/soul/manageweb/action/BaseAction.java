package com.hifun.soul.manageweb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 基础的Action;
 * 
 * @author crazyjohn
 * 
 */
public abstract class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407226574522553713L;
	private String alertMsg="";
	protected HttpServletResponse response; 
	protected String getRemoteIp(){
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getHeader("x-forwarded-for") == null) {      
			   return request.getRemoteAddr();      
			  }      
		return request.getHeader("x-forwarded-for");      	
	}
	/**
	 * 把错误信息放到请求对象中;
	 * 
	 * @param errorInfo
	 */
	protected void putErrorInfoToRequest(String errorInfo) {
		ServletActionContext.getRequest().setAttribute("errorInfo", errorInfo);
	}
	public String getAlertMsg() {
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
	
}
