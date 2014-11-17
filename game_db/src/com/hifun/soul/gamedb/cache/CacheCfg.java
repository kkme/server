package com.hifun.soul.gamedb.cache;


/**
 * 缓存的配置类，包括是否使用缓存以及使用缓存时多长时间同步数据到Data Server。<p>
 * 
 * 该缓存的配置类需要在Game Server启动时在<tt>ServerConfig</tt>中配置，<br>
 * 配置方法：<code>CacheCfg.config(ServerConfig);</code>。
 *
 * @author crazyjohn
 *
 */
public class CacheCfg {
	
	private static boolean turnOff = false;
	
	
	/**
	 * 配置GameServer的缓存
	 * 
	 * @param cfg
	 */
	public static void config(boolean turnOn){
		turnOff = !turnOn;
	}
	
	/**
	 * 使Game Server使用缓存
	 */
	public static void turnOn(){
		turnOff = false;
	}
	
	/**
	 * 使Game Server停止使用缓存
	 */
	public static void turnOff(){
		turnOff = true;
	}
	
	public static boolean isOff(){
		return turnOff;
	}
	
}
