package com.hifun.soul.gamedb.agent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.config.DBOperationConfig;
import com.hifun.soul.gamedb.monitor.DBOperationType;

/**
 * 抽象的实体代理;
 * 
 * @author crazyjohn
 * 
 * @param <E>
 */
public abstract class AbstractEntityAgent<E extends IEntity> implements
		IEntityAgent<E> {
	protected IDBService dbService;
	protected Class<? extends IEntity>[] bindClasses;
	protected Map<Class<?>, Map<DBOperationType, Integer>> entityDBOperationInfos = new ConcurrentHashMap<Class<?>, Map<DBOperationType, Integer>>();

	public AbstractEntityAgent(IDBService dbService,
			Class<? extends IEntity>[] bindClasses) {
		this.dbService = dbService;
		this.bindClasses = bindClasses;
		// 构建好通用代理所有实体的DB操作信息
		for (Class<?> each : bindClasses) {
			Map<DBOperationType, Integer> eachMap = new HashMap<DBOperationType, Integer>();
			for (DBOperationType opType : DBOperationType.values()) {
				eachMap.put(opType, 0);
			}
			this.entityDBOperationInfos.put(each, eachMap);
		}
	}

	@Override
	public Class<? extends IEntity>[] getBindClasses() {
		return this.bindClasses;
	}

	@Override
	public E get(Class<E> entityClass, long id) {
		// 记录
		addDBOperationTypeCount(entityClass, DBOperationType.GET);
		return dbService.get(entityClass, id);
	}

	/**
	 * 添加DB操作次数;
	 * 
	 * @param entityClass
	 * @param operationType
	 */
	protected void addDBOperationTypeCount(Class<?> entityClass,
			DBOperationType operationType) {
		// 检查开关是否打开;
		if (!DBOperationConfig.isOpen()) {
			return;
		}
		Map<DBOperationType, Integer> entityOperationMap = this.entityDBOperationInfos
				.get(entityClass);
		entityOperationMap.put(operationType,
				entityOperationMap.get(operationType) + 1);
	}

	@Override
	public Serializable insert(E entity) {
		// 记录
		addDBOperationTypeCount(entity.getClass(), DBOperationType.INSERT);
		return dbService.insert(entity);
	}

	@Override
	public void update(E entity) {
		addDBOperationTypeCount(entity.getClass(), DBOperationType.UPDATE);
		//editby:crazyjohn 使用saveOrUpdate接口为了解决如果实体未插入就更新导致报错的问题
		dbService.saveOrUpdate(entity);
	}

	@Override
	public void delete(Class<E> entityClass, long id) {
		addDBOperationTypeCount(entityClass, DBOperationType.DELETE);
		throw new UnsupportedOperationException();

	}

	@Override
	public void delete(E entity) {
		addDBOperationTypeCount(entity.getClass(), DBOperationType.DELETE);
		dbService.delete(entity);
	}

	@Override
	public Map<Class<?>, Map<DBOperationType, Integer>> getEntityDBOperationInfos() {
		return entityDBOperationInfos;
	}

}
