package com.hifun.soul.gamedb.agent.persistence;

import com.hifun.soul.core.orm.IEntity;

/**
 * 持久化策略接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IPersistenceStrategy {

	public void doUpdate(IEntity entity);

}
