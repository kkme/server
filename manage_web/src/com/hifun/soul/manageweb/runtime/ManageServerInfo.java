package com.hifun.soul.manageweb.runtime;

public class ManageServerInfo {
	private int serverId;
	private String serverName;
	private String host;
	private int port;
	private boolean connectionState;
	private int status;
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
	public boolean isConnectionState() {
		return connectionState;
	}
	public void setConnectionState(boolean connectionState) {
		this.connectionState = connectionState;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
