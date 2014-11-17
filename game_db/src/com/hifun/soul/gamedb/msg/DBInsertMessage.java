package com.hifun.soul.gamedb.msg;

import java.io.Serializable;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;
/**
 * 负责数据库插入动作的消息;
 * 
 * @author crazyjohn
 *
 * @param <Entity>
 */
public class DBInsertMessage<E extends IEntity> extends AbstractDBMessage<Serializable> {
	/** 需要被插入的实体 */
	private E entity;
	
	@SuppressWarnings("unchecked")
	public DBInsertMessage(IDBCallback<?> callback, E entity) {
		super((IDBCallback<Serializable>) callback);
		this.entity = entity;
	}
	
	public E getEntity() {
		return this.entity;
	}

	@Override
	protected void doRealDBAction(IDBAgent dbAgent,
			DBCallbackMessage<Serializable> callbackMsg) {
		callbackMsg.setResult(dbAgent.insert(this));
	}

	@Override
	public Class<? extends IEntity> getEntityClass() {
		return entity.getClass();
	}



}
