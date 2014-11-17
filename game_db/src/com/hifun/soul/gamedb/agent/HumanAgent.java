package com.hifun.soul.gamedb.agent;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.persistence.AsyncPersistenceStrategy;
import com.hifun.soul.gamedb.agent.persistence.IPersistenceStrategy;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.converter.HumanCacheToEntityConverter;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.gamedb.monitor.DBOperationType;
import com.hifun.soul.gamedb.util.LRUHashMap;
import com.hifun.soul.gamedb.util.ModifiedSet;

/**
 * 角色代理入口;
 * 
 * @author crazyjohn
 * 
 */
public class HumanAgent extends AbstractEntityAgent<HumanEntity> implements
		ICacheableAgent<Long, HumanCacheData> {
	private static Logger logger = Loggers.DB_MAIN_LOGGER;
	private LRUHashMap<Long, HumanCacheData> humanCaches;
	private HumanCacheToEntityConverter converter = new HumanCacheToEntityConverter();
	/** 持久化策略 */
	private IPersistenceStrategy persistenceStrategy;
	/** 查询代理 */
	private XQLAgent queryAgent;
	/** 更改集 */
	private ModifiedSet<HumanCacheData> modifiedSet = new ModifiedSet<HumanCacheData>();

	@SuppressWarnings("unchecked")
	public HumanAgent(int maxCacheSize,
			IPersistenceStrategy persistenceStrategy, IDBService dbService,
			XQLAgent queryAgent) {
		
		super(dbService, new Class[] { HumanEntity.class });
		this.queryAgent = queryAgent;
		this.persistenceStrategy = new AsyncPersistenceStrategy<HumanCacheData>(
				modifiedSet);
		humanCaches = new LRUHashMap<Long, HumanCacheData>(maxCacheSize,
				new LRUHashMap.EvictPolicy<HumanCacheData>() {

					@Override
					public boolean evict(HumanCacheData value) {
						return true;
					}
				});
	}

	public HumanAgent(int maxCacheSize, IDBService dbService,
			XQLAgent queryAgent) {
		// TODO: crazyjohn 默认的淘汰策略, 而不是空;
		this(maxCacheSize, null, dbService, queryAgent);
	}

	@Override
	public HumanEntity get(Class<HumanEntity> entityClass, long id) {
		HumanCacheData cache = loadFormCacheOrDB(id);
		if (cache == null) {
			return null;
		}
		HumanEntity entity = converter.convert(cache);
		return entity;
	}

	private HumanCacheData loadFormCacheOrDB(long id) {
		HumanCacheData cache = this.getFormCache(id);
		if (cache == null) {
			cache = loadFromDB(id);
			if (cache != null) {
				this.setToCache(id, cache);
			}
		}
		return cache;
	}

	private HumanCacheData loadFromDB(long id) {
		this.addDBOperationTypeCount(HumanEntity.class, DBOperationType.GET);
		List<?> results = this.queryAgent.execute(
				DataQueryConstants.QUERY_CHARACTER_BY_ID,
				new String[] { "id" }, new Object[] { id });
		if (results == null || results.isEmpty()) {
			return null;
		}
		HumanEntity entity = (HumanEntity) results.get(0);
		HumanCacheData cache = null;
		if (entity != null) {
			cache = this.converter.reverseConvert(entity);
		}
		return cache;
	}

	@Override
	public Serializable insert(HumanEntity entity) {
		if (entity == null) {
			return null;
		}
		// put to cache
		HumanCacheData cache = this.converter.reverseConvert(entity);
		this.setToCache(entity.getId(), cache);
		this.addDBOperationTypeCount(entity.getClass(), DBOperationType.INSERT);
		return this.dbService.insert(entity);
	}

	@Override
	public void update(HumanEntity entity) {
		HumanCacheData cache = this.loadFormCacheOrDB(entity.getId());
		if (cache != null) {
			// update cache
			cache = this.converter.reverseConvert(entity);
			this.humanCaches.put(entity.getId(), cache);
			// 这里有个持久化策略,是直接存库还是异步存!!!
			this.persistenceStrategy.doUpdate(entity);
		} else {
			if (logger.isErrorEnabled()) {
				logger.error(String
						.format("Can not find such human in cache or db! humanGuid: %d",
								entity.getId()));
			}
		}
	}

	@Override
	public void delete(HumanEntity entity) {
		this.addDBOperationTypeCount(entity.getClass(), DBOperationType.DELETE);
		this.dbService.delete(entity);
	}

	@Override
	public void delete(Class<HumanEntity> entityClass, long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ModifiedSet<HumanCacheData> getModifiedSet() {
		return this.modifiedSet;
	}

	@Override
	public void evict() {
		// FIXME: crazyjohn 合适的淘汰机制;
		// logger.debug("No suit evict strategy, please told john to add,thank you.");
		// throw new UnsupportedOperationException();
	}

	@Override
	public HumanCacheData getFormCache(Long key) {
		return this.humanCaches.get(key);
	}

	@Override
	public void setToCache(Long key, HumanCacheData value) {
		if (value == null) {
			return;
		}
		this.humanCaches.put(key, value);
	}

}
