package com.hifun.soul.gamedb.agent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.util.ModifiedSet;

/**
 * 通用的异步更新实体代理;
 * 
 * @author crazyjohn
 * 
 * @param <K>
 * @param <V>
 */
public class CommonAsyncUpdateEntityAgent<K extends Serializable, V extends IEntity> extends CommonEntityAgent<V> implements ICacheableAgent<K, V> {
	/** 是否使用缓存 */
	private boolean useCache = false;
	/** 缓存实体 */
	private Map<K, V> entities = new HashMap<K, V>();
	/** 更改集 */
	private ModifiedSet<V> modifiedEntities = new ModifiedSet<V>();
	
	public CommonAsyncUpdateEntityAgent(IDBService dbService,
			Class<? extends IEntity>[] bindClasses, boolean useCache) {
		super(dbService, bindClasses);
		this.useCache = useCache;
	}

	
	
	@Override
	public ModifiedSet<?> getModifiedSet() {
		return modifiedEntities;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(V entity) {
		if (useCache) {
			this.entities.put((K) entity.getId(), entity);
		}
		this.modifiedEntities.addModified(entity);
	}

	@Override
	public void evict() {
		// TODO Auto-generated method stub

	}

	@Override
	public V getFormCache(K key) {
		return entities.get(key);
	}

	@Override
	public void setToCache(K key, V value) {
		this.entities.put(key, value);
	}

}
