package com.hifun.soul.gamedb;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.msg.IDBMessage;

/**
 * 数据层分发器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IDBDispatcher {

	/**
	 * 启动数据执行器
	 */
	public void start();

	/**
	 * 停止数据执行器
	 */
	public void stop();

	/**
	 * 向数据执行器投递消息
	 * 
	 * @param msg
	 */
	public void put(IDBMessage msg);

	/**
	 * 注册特殊查询;现在只有account查询;<br>
	 * 和角色其它查询分开的目的是这个查询比较特殊, 属于认证的业务, 可能去接平台;
	 * 
	 * @param specialQuery
	 */
	public void registerSpecialQuery(String specialQuery);

	/**
	 * 注册角色数据处理器的处理实体;
	 * 
	 * @param entityClasses
	 */
	void registerHumanEntityProcessor(Class<? extends IEntity>[] entityClasses);

	/**
	 * 处理其它通用实体数据处理器的处理实体;
	 * 
	 * @param entityClasses
	 */
	void registerCommonXXEntityProcessor(
			Class<? extends IEntity>[] entityClasses);

	/**
	 * 注册角色数据相关的查询;
	 * 
	 * @param queryName
	 */
	public void registerHumanEntityQuery(String queryName);

	/**
	 * 注册其它实体的查询;
	 * 
	 * @param queryName
	 */
	public void registerCommonXXEntityQuery(String queryName);
	
	public void addHeartBeatObject(final IHeartBeatable object, long period);

}
