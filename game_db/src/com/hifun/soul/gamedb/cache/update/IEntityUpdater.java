package com.hifun.soul.gamedb.cache.update;

import com.hifun.soul.core.orm.IEntity;

/**
 * 实体的更新器
 * <p>
 * 用于在更新线程UpdateDBTask中进行实体的更新入库
 * </p>
 * 
 * @author crazyjohn
 * 
 */
public interface IEntityUpdater<T extends IEntity>{

	/**
	 * 进行实体的如数据库更新操作
	 * 
	 * @param entity
	 *            需要更新的实体
	 */
	public boolean update(T entity);

}
