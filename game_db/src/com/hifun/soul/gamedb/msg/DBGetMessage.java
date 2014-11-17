package com.hifun.soul.gamedb.msg;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 负责数据库获取操作的消息
 * 
 * @author crazyjohn
 * 
 */
public class DBGetMessage<E extends IEntity> extends AbstractDBMessage<E> {
	private long id;
	private Class<E> entityClass;

	public DBGetMessage(long id, Class<E> entityClass,IDBCallback<E> callback) {
		super(callback);
		this.id = id;
		this.entityClass = entityClass;
	}
	
	public long getId() {
		return this.id;
	}
	
	public Class<E> getEntityClass() {
		return this.entityClass;
	}

	@Override
	protected void doRealDBAction(IDBAgent dbAgent,
			DBCallbackMessage<E> callbackMsg) {
		E entity = dbAgent.<E>get(this);
		callbackMsg.setResult(entity);
	}

}
