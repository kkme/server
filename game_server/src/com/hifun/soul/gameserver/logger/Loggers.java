package com.hifun.soul.gameserver.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志静态工厂;
 * 
 * @author crazyjohn
 * 
 */
public class Loggers {
	/** 任务相关的日志 */
	public static final Logger HUMAN_QUEST_LOGGER = LoggerFactory
			.getLogger("hifun.soul.human.quest");

	/** 登录认证相关的日志 */
	public static final Logger HUMAN_LOGIN_LOGGER() {
		return LoggerFactory
				.getLogger("hifun.soul.human.login");
	}
}
