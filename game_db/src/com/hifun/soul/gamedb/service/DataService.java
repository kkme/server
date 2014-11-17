package com.hifun.soul.gamedb.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.DBCallbackFactory;
import com.hifun.soul.gamedb.IDBDispatcher;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.msg.DBDeleteMessage;
import com.hifun.soul.gamedb.msg.DBGetMessage;
import com.hifun.soul.gamedb.msg.DBInsertMessage;
import com.hifun.soul.gamedb.msg.DBQueryMessage;
import com.hifun.soul.gamedb.msg.DBUpdateMessage;

/**
 * 数据服务的实现;<br>
 * 
 * FIXME: crazyjohn 如果线程在投入消息的时候由于队列满而阻塞,然后又进一步被中断阻塞状态,如何处理此类异常?
 * 
 * @author crazyjohn
 * 
 */
@Component
public class DataService implements IDataService {
	/** 数据库执行器 */
	private IDBDispatcher dbExecutor;

	public void setDbExecutor(IDBDispatcher dbExecutor) {
		this.dbExecutor = dbExecutor;
	}

	@Override
	public <E extends IEntity> void get(long id, Class<E> entityClass,
			IDBCallback<E> dbCallback) {
		DBGetMessage<E> get = new DBGetMessage<E>(id, entityClass, dbCallback);
		dbExecutor.put(get);
	}

	@Override
	public <E extends IEntity> void insert(E entity, IDBCallback<?> dbCallback) {
		DBInsertMessage<E> insert = new DBInsertMessage<E>(dbCallback, entity);
		dbExecutor.put(insert);
	}

	@Override
	public void query(String queryName, String[] params, Object[] values,
			IDBCallback<List<?>> dbCallback) {
		DBQueryMessage query = new DBQueryMessage(queryName, params, values,
				dbCallback);
		dbExecutor.put(query);
	}

	@Override
	public void query(String queryName, IDBCallback<List<?>> dbCallback) {
		this.query(queryName, new String[] {}, new Object[0], dbCallback);
	}

	@Override
	public <E extends IEntity> void deleteById(long id, Class<E> entityClass) {
		// TODO: JOHN 是否需要添加这种支持???
	}

	@Override
	public <E extends IEntity> void update(E entity) {
		this.update(entity, DBCallbackFactory.<E> getNullDBCallback());
	}

	@Override
	public <E extends IEntity> void update(E entity, IDBCallback<E> dbCallback) {
		DBUpdateMessage<E> update = new DBUpdateMessage<E>(entity, dbCallback);
		dbExecutor.put(update);
	}

	@Override
	public <E extends IEntity> void delete(E entity, IDBCallback<Void> callback) {
		DBDeleteMessage<E> delete = new DBDeleteMessage<E>(entity, callback);
		dbExecutor.put(delete);
	}

	@Override
	public <E extends IEntity> void insert(E entity) {
		insert(entity, DBCallbackFactory.getNullDBCallback());
	}

	@Override
	public <E extends IEntity> void delete(E entity) {
		delete(entity, DBCallbackFactory.<Void>getNullDBCallback());
	}

}
