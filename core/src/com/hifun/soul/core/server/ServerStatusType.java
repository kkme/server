package com.hifun.soul.core.server;

/**
 * 
 * 服务器状态
 * 
 * @author magicstone
 *
 */
public enum ServerStatusType {
	/** 初始化状态 */
	STATUS_INIT("INIT"),
	/** 受限访问状态 */
	STATUS_LIMITED("LIMITED"),
	/** 运行状态 */
	STATUS_RUN("RUN"),
	/** 准备停止状态 */
	STATUS_STOPPING("STOPPING"),
	/** 停止状态 */
	STATUS_STOPPED("STOPPED"),
	/** 有错误 */
	STATUS_ERROR("ERROR"),
	/** 服务器过载 */
	STATUS_OVERLOAD("OVERLOAD"),
	;
	
	/** 服务器状态码 */
	private final String code;
	
	private ServerStatusType(String code) {
		this.code = code;
	}

	public String getStatusCode() {
		return code;
	}
}
