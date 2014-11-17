package com.hifun.soul.gamedb.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggers {

	public static final Logger DB_MAIN_LOGGER = LoggerFactory
			.getLogger("hifun.soul.dbmain");

	/** 数据库更新日志 */
	public static final Logger UPDATE_THREAD_LOGGER = LoggerFactory
			.getLogger("hifun.soul.cache.update");
	
	/** 平台相关日志 */
	public static final Logger LOCAL_LOGGER = LoggerFactory
			.getLogger("hifun.soul.local.qq");
}
