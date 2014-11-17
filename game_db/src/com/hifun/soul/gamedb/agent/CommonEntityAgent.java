package com.hifun.soul.gamedb.agent;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;

/**
 * 通用的实体代理;
 * <p>
 * 没有特殊业务的实体可以选用此代理;
 * <p>
 * 
 * @author crazyjohn
 * 
 * @param <E>
 */
public class CommonEntityAgent<E extends IEntity> extends
		AbstractEntityAgent<E> {

	public CommonEntityAgent(IDBService dbService,
			Class<? extends IEntity>[] bindClasses) {
		super(dbService, bindClasses);
	}

}
