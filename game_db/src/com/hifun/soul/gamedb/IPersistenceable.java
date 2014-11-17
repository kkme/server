package com.hifun.soul.gamedb;

 

import com.hifun.soul.core.orm.IEntity;

/**
 * 可持久化接口;<br>
 * 
 * <pre>
 * 1. 需要持久化的业务实体可实现此接口;
 * 2. 提供了业务实体转化成可以持久化的实体{{@link #toEntity()} ;
 * 3. 提供了实体对象转化成业务对象的接口{{@link #fromEntity(IEntity)} ;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface IPersistenceable<E extends IEntity> {
	/**
	 * 获取实体的GUID
	 * 
	 * @return 返回实体的GUID
	 */
	public long getGuid();

	/**
	 * 业务对象转化为持久化实体的接口;
	 * 
	 * @return 返回可以持久化的实体;
	 */
	public E toEntity();

	/**
	 * 把实体转化成对应的业务对象;
	 * 
	 * @param entity
	 *            对应的实体对象
	 */
	public void fromEntity(E entity);
}
