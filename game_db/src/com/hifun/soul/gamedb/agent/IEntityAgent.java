package com.hifun.soul.gamedb.agent;

import java.io.Serializable;
import java.util.Map;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.monitor.DBOperationType;

/**
 * 数据库实体代理接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IEntityAgent<E extends IEntity> {

	public E get(Class<E> entityClass, long id);

	public Serializable insert(E entity);

	public void update(E entity);

	public void delete(Class<E> entityClass, long id);

	public void delete(E entity);

	public Class<? extends IEntity>[] getBindClasses();

	/**
	 * 获取实体的DB操作信息;
	 * 
	 * @return
	 */
	Map<Class<?>, Map<DBOperationType, Integer>> getEntityDBOperationInfos();
}
