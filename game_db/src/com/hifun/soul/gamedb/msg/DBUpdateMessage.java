package com.hifun.soul.gamedb.msg;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 负责数据库更新的消息;
 * 
 * @author crazyjohn
 * 
 * @param <E>
 */
public class DBUpdateMessage<E extends IEntity> extends AbstractDBMessage<E> {
	private E entity;

	public DBUpdateMessage(E entity, IDBCallback<E> callback) {
		super(callback);
		this.entity = entity;
	}

	public E getEntity() {
		return this.entity;
	}

	@Override
	protected void doRealDBAction(IDBAgent dbAgent,
			DBCallbackMessage<E> callbackMsg) {
		dbAgent.update(this);
	}

	@Override
	public Class<? extends IEntity> getEntityClass() {
		return entity.getClass();
	}

}
