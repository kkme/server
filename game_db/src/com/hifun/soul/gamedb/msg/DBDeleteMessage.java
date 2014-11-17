package com.hifun.soul.gamedb.msg;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 负责数据库删除动作的消息;
 * 
 * @author crazyjohn
 * 
 */
public class DBDeleteMessage<E extends IEntity> extends AbstractDBMessage<Void> {
	/** 需要被删除的实体 */
	private E entity;

	public DBDeleteMessage(E entity, IDBCallback<Void> callback) {
		super(callback);
		this.entity = entity;
	}
	
	public E getEntity() {
		return entity;
	}

	@Override
	protected void doRealDBAction(IDBAgent dbAgent,
			DBCallbackMessage<Void> callbackMsg) {
		dbAgent.delete(this);
	}

	@Override
	public Class<? extends IEntity> getEntityClass() {
		return entity.getClass();
	}

}
