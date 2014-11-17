package com.hifun.soul.gamedb.service;

import java.util.List;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 数据服务对外的接口;<br>
 * 把数据服务封装了一套rpc接口供业务使用;<br>
 * <pre>
 * 1. 此服务的依赖和生命周期管理,交给spring来做;
 * 2. 如果其它业务要使用此服务可以使用{@link ApplicationContext#getApplicationContext()} .getBean(IDataService)来获取;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface IDataService {

	/**
	 * 获取实体的接口
	 * 
	 * @param id
	 *            实体ID
	 * @param entityClass
	 *            实体的Class类型
	 * @param dbCallback
	 *            数据库回调对象
	 */
	public <E extends IEntity> void get(long id, Class<E> entityClass,
			IDBCallback<E> dbCallback);

	/**
	 * 插入实体对象
	 * 
	 * @param entity
	 *            需要插入的实体对象
	 */
	public <E extends IEntity> void insert(E entity, IDBCallback<?> dbCallback);
	
	/**
	 * 插入实体对象
	 * 
	 * @param entity
	 *            需要插入的实体对象
	 */
	public <E extends IEntity> void insert(E entity);

	/**
	 * 执行指定的查询操作;
	 * 
	 * @param queryName
	 *            查询名称
	 * @param params
	 *            查询参数名称数组
	 * @param values
	 *            参数值数组
	 * @param dbCallback
	 *            数据库回调对象
	 */
	public void query(String queryName, String[] params, Object[] values,
			IDBCallback<List<?>> dbCallback);

	/**
	 * 执行指定的查询
	 * 
	 * @param queryName
	 *            查询名称
	 * @param dbCallback
	 *            数据库回调对象
	 */
	public void query(String queryName, IDBCallback<List<?>> dbCallback);

	/**
	 * 删除指定的实体
	 * 
	 * @param id
	 *            实体ID
	 * @param entityClass
	 *            实体Class类型
	 */
	public <E extends IEntity> void deleteById(long id, Class<E> entityClass);

	/**
	 * 刪除指定的实体;
	 * 
	 * @param entity
	 *            需要删除的实体;
	 * @param callback
	 *            执行删除结束后进行的回调;
	 */
	public <E extends IEntity> void delete(E entity, IDBCallback<Void> callback);
	
	public <E extends IEntity> void delete(E entity);

	/**
	 * 更新指定的实体对象
	 * 
	 * @param entity
	 *            需要别更新的实体对象
	 */
	public <E extends IEntity> void update(E entity);

	/**
	 * 需要被更新的实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param dbCallback
	 *            数据库回调对象
	 */
	public <E extends IEntity> void update(E entity, IDBCallback<E> dbCallback);
}
