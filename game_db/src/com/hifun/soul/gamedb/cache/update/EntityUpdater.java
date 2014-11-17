package com.hifun.soul.gamedb.cache.update;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;

/**
 * 直接使用DBService.update(entity)方法进行更新.使用的是全字段更新
 * 
 * @author crazyjohn
 * 
 */
public class EntityUpdater implements
		IEntityUpdater<IEntity>{
	private IDBService service;
	
	public EntityUpdater(IDBService service) {
		this.service = service;
	}

	@Override
	public boolean update(IEntity entity) {
		service.update(entity);
		return true;
	}

}
