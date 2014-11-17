package com.hifun.soul.gamedb.agent.persistence;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;

/**
 * 同步持久化策略;
 * 
 * @author crazyjohn
 * 
 */
public class SynchronizationPersistStrategy implements IPersistenceStrategy {
	private IDBService dbService;

	public SynchronizationPersistStrategy(IDBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public void doUpdate(IEntity entity) {
		dbService.update(entity);
	}

}
