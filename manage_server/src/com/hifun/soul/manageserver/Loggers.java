package com.hifun.soul.manageserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理服日志静态工厂;
 * 
 * @author crazyjohn
 * 
 */
public class Loggers {
	/** 管理服主日志 */
	public static final Logger MANAGE_MAIN_LOGGER = LoggerFactory
			.getLogger("com.hifun.soul.manageServer");
	
	public static final Logger SERVER_LOG_DB_LOGGER = LoggerFactory
			.getLogger("com.hifun.soul.manageServer.serverLog.db");
}
