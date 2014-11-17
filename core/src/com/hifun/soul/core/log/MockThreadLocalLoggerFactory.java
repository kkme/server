package com.hifun.soul.core.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于类似TheadLocal的日志工程;<br>
 * 此工厂会为每一个线程保留一个指定的logger备份;用于解决在单进程多线程时候, 日志并发写的时候会极大程度的降低程序性能;
 * 
 * @author crazyjohn
 * 
 */
@Deprecated
public class MockThreadLocalLoggerFactory implements ILoggerFactory {
	private Map<String, Logger> loggers = new ConcurrentHashMap<String, Logger>();

	@Override
	public Logger getLogger(String name) {
		// 名字会添加当前的线程名字
		String fullName = getFullName(name);
		Logger logger = loggers.get(fullName);
		if (logger == null) {
			logger = LoggerFactory.getLogger(fullName);
			loggers.put(fullName, logger);
		}
		// 打印出日志记录器名字;
		System.out.println(String.format(
				"[TestLoggerPerformance] loggerName: %s",
				(fullName + "=" + logger.toString())));
		return logger;
	}

	private String getFullName(String name) {
		StringBuilder nameBuilder = new StringBuilder(name);
		nameBuilder.append(".").append(Thread.currentThread().getName());
		String result = nameBuilder.toString();
		return result;
	}
}
