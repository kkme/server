package com.hifun.soul.core.log;

import org.slf4j.Logger;

/**
 * 日志工厂接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ILoggerFactory {

	/**
	 * 获取指定的日志;
	 * 
	 * @param name
	 * @return
	 */
	public Logger getLogger(String name);

}
