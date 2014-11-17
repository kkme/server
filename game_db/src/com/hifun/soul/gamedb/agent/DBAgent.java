package com.hifun.soul.gamedb.agent;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;

import com.hifun.soul.core.orm.DataAccessException;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.gamedb.monitor.DBOperationType;
import com.hifun.soul.gamedb.msg.DBDeleteMessage;
import com.hifun.soul.gamedb.msg.DBGetMessage;
import com.hifun.soul.gamedb.msg.DBInsertMessage;
import com.hifun.soul.gamedb.msg.DBQueryMessage;
import com.hifun.soul.gamedb.msg.DBUpdateMessage;

/**
 * 数据库代理的实现类;
 * 
 * @author crazyjohn
 * 
 */
public class DBAgent implements IDBAgent {
	private Logger logger = Loggers.DB_MAIN_LOGGER;
	/** XQL代理 */
	private XQLAgent xqlAgent;
	/** 实体代理集合;使用并发容器 */
	private Map<String, IEntityAgent<? extends IEntity>> entityAgents = new ConcurrentHashMap<String, IEntityAgent<? extends IEntity>>();
	/** 缓存实体代理集合; 使用并发容器 */
	private List<ICacheableAgent<?, ?>> cacheableAgents = new CopyOnWriteArrayList<ICacheableAgent<?, ?>>();

	public void registerXQLAgent(XQLAgent xqlAgent) {
		if (xqlAgent == null) {
			throw new IllegalArgumentException("XQLAgent can not be null.");
		}
		this.xqlAgent = xqlAgent;
	}

	/**
	 * 注册实体代理;
	 * 
	 * @param entityAgent
	 */
	public void registerEntityAgent(IEntityAgent<? extends IEntity> entityAgent) {
		if (entityAgent == null) {
			throw new IllegalArgumentException("EntityAgent can not be null.");
		}
		for (Class<? extends IEntity> entityClass : entityAgent
				.getBindClasses()) {
			this.entityAgents.put(entityClass.getSimpleName(), entityAgent);
		}
	}

	/**
	 * 注册可以缓存的实体代理;
	 * 
	 * @param agent
	 */
	@Override
	public void registerCacheableAgent(
			ICacheableAgent<?, ?> agent) {
		if (!this.cacheableAgents.contains(agent)) {
			this.cacheableAgents.add(agent);
		}
	}

	@Override
	public <E extends IEntity> Serializable insert(DBInsertMessage<E> msg) {
		E entity = msg.getEntity();
		if (entity == null) {
			throw new NullPointerException("The insert entity can not bu null.");
		}
		@SuppressWarnings("unchecked")
		IEntityAgent<E> agent = (IEntityAgent<E>) this.entityAgents.get(entity
				.getClass().getSimpleName());
		if (agent == null) {
			logger.error(String.format("No Agent for this entity: %d",
					entity.getClass()));
			return null;
		}
		Serializable result = null;
		try {
			result = agent.insert(entity);
		} catch (Exception e) {
			logger.error(String.format(
					"Insert entity error, entityClass:%s, entityId: %d",
					entity.getClass(), entity.getId()), e);
			throw new DataAccessException(e);
		}
		return result;
	}

	@Override
	public <E extends IEntity> void delete(DBDeleteMessage<E> msg) {
		E entity = msg.getEntity();
		if (entity == null) {
			throw new NullPointerException("The delete entity can not bu null.");
		}
		@SuppressWarnings("unchecked")
		IEntityAgent<E> agent = (IEntityAgent<E>) this.entityAgents.get(entity
				.getClass().getSimpleName());
		if (agent == null) {
			logger.error(String.format("No Agent for this entity: %d",
					entity.getClass()));
			return;
		}
		try {
			agent.delete(entity);
		} catch (Exception e) {
			logger.error(String.format(
					"Delete entity error, entityClass:%s, entityId: %d",
					entity.getClass(), entity.getId()), e);

			throw new DataAccessException(e);
		}
	}

	@Override
	public <E extends IEntity> void update(DBUpdateMessage<E> msg) {
		E entity = msg.getEntity();
		if (entity == null) {
			throw new NullPointerException("The update entity can not bu null.");
		}
		@SuppressWarnings("unchecked")
		IEntityAgent<E> agent = (IEntityAgent<E>) this.entityAgents.get(entity
				.getClass().getSimpleName());
		if (agent == null) {
			logger.error(String.format("No Agent for this entity: %d",
					entity.getClass()));
			return;
		}
		try {
			agent.update(entity);
		} catch (Exception e) {
			logger.error(String.format(
					"Update entity error, entityClass:%s, entityId: %d",
					entity.getClass(), entity.getId()), e);

			throw new DataAccessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IEntity> E get(DBGetMessage<E> msg) {
		Class<E> entityClass = msg.getEntityClass();
		long id = msg.getId();
		if (entityClass == null) {
			throw new NullPointerException(
					"The get entityClass can not bu null.");
		}
		IEntityAgent<E> agent = (IEntityAgent<E>) this.entityAgents
				.get(entityClass.getSimpleName());
		if (agent == null) {
			logger.error(String.format("No Agent for this entity: %d",
					entityClass));
			return null;
		}
		E result = null;
		try {
			result = agent.get((Class<E>) entityClass, id);
		} catch (Exception e) {
			logger.error(String.format(
					"Get entity error, entityClass:%s, entityId: %d",
					entityClass, id), e);

			throw new DataAccessException(e);
		}
		return result;
	}

	@Override
	public List<?> query(DBQueryMessage msg) {
		return this.xqlAgent.execute(msg.getQueryName(), msg.getParams(),
				msg.getValues());
	}

	/**
	 * 获取更改
	 * 
	 * @return
	 */
	public List<ICacheableAgent<?, ?>> getModifiedEntityAgent() {
		return Collections.unmodifiableList(this.cacheableAgents);
	}

	@Override
	public Map<Class<?>, Map<DBOperationType, Integer>> getEntityDBOperationInfos() {
		Map<Class<?>, Map<DBOperationType, Integer>> entityDBOperationInfos = new ConcurrentHashMap<Class<?>, Map<DBOperationType, Integer>>();
		for(IEntityAgent<? extends IEntity> agent : entityAgents.values()){
			entityDBOperationInfos.putAll(agent.getEntityDBOperationInfos());
		}
		return entityDBOperationInfos;
	}

	@Override
	public Map<String, Integer> getDbQueryTimesInfo() {
		if(xqlAgent == null){
			return null;
		}
		return xqlAgent.getDbQueryTimesInfo();
	}
	
	

}
