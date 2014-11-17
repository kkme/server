package com.hifun.soul.gamedb.agent.persistence;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.util.ModifiedSet;

/**
 * 异步保存策略;
 * 
 * @author crazyjohn
 * 
 */
public class AsyncPersistenceStrategy<E extends IEntity> implements IPersistenceStrategy {
	private ModifiedSet<E> modifiedSet = new ModifiedSet<E>();

	public AsyncPersistenceStrategy(ModifiedSet<E> modifiedSet) {
		this.modifiedSet = modifiedSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doUpdate(IEntity entity) {
		this.modifiedSet.addModified((E) entity);
	}

	

}
