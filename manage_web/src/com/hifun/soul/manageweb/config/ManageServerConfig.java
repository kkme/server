package com.hifun.soul.manageweb.config;


/**
 * 游戏服配置
 * @author magicstone
 *
 */
public class ManageServerConfig {
	private int serverId;
	private String serverName;
	private String host;
	private int port;
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
