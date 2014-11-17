package com.hifun.soul.manageserver.config;

import com.hifun.soul.core.orm.DBConfiguration;

/**
 * 游戏服配置
 * @author magicstone
 *
 */
public class GameServerConfig {
	private int serverId;
	private String serverName;
	private String host;
	private int port;
	private DBConfiguration gameDbConfig;
	
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
	public DBConfiguration getGameDbConfig() {
		return gameDbConfig;
	}
	public void setGameDbConfig(DBConfiguration gameDbConfig) {
		this.gameDbConfig = gameDbConfig;
	}
	
}
