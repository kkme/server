package com.hifun.soul.logserver.common;

import com.hifun.soul.common.model.LogServerStatus;
import com.hifun.soul.core.server.ShutdownHooker;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.logserver.LogServerConfig;

public class LogServerAssist {
	private static LogServerConfig config;
	private static TimeService timeService;
	/** 日志服务器状态 */
	private static LogServerStatus logServerStatus;
	private static ShutdownHooker shutdownHooker;

	public static void init(LogServerConfig cfg) {
		config = cfg;
		timeService = new SystemTimeService();
		shutdownHooker = new ShutdownHooker();
		logServerStatus = buildLogServerStatus(config);
	}

	public static LogServerConfig getServerConfig() {
		return config;
	}

	public static TimeService getTimeService() {
		return timeService;
	}
	
	public static LogServerStatus getLogServerStatus() {
		return logServerStatus;
	}
	
	public static ShutdownHooker getShutdownHooker() {
		return shutdownHooker;
	}

	/**
	 * 获取日志服务器状态
	 * @param logServerConfig
	 * @return
	 */
	private static LogServerStatus buildLogServerStatus(LogServerConfig logServerConfig)
	{
		LogServerStatus logServerStatus = new LogServerStatus();
		
		logServerStatus.setServerIndex(logServerConfig.getServerIndex());
		logServerStatus.setServerId(logServerConfig.getServerId());
		logServerStatus.setServerName(logServerConfig.getServerName());
		logServerStatus.setIp(logServerConfig.getServerHost());
		logServerStatus.setPort(String.valueOf(logServerConfig.getPort()));		
		logServerStatus.setVersion(logServerConfig.getVersion());
		
		return logServerStatus;
	}
}
