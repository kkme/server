package com.hifun.soul.gamedb.agent;

import java.io.Serializable;

import org.slf4j.Logger;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.gamedb.annotation.NotBlobHumanSubEntity;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.gamedb.monitor.DBOperationType;

/**
 * 角色子实体代理;<br>
 * 
 * <pre>
 * 1. 物品;
 * 2. 任务;
 * 3. 属性;
 * </pre>
 * 
 * @author crazyjohn
 * 
 * @param <E>
 */
public class HumanSubEntityAgent<E extends IHumanSubEntity & IEntity>
		extends AbstractEntityAgent<E> {
	private static Logger logger = Loggers.DB_MAIN_LOGGER;
	private HumanAgent humanAgent;
	
	public HumanSubEntityAgent(HumanAgent humanAgent, IDBService dbService,
			Class<? extends IEntity>[] bindClasses) {
		super(dbService, bindClasses);
		this.humanAgent = humanAgent;
	}

	@Override
	public E get(Class<E> entityClass, long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Serializable insert(E entity) {
		long humanGuid = entity.getHumanGuid();
		HumanCacheData cache = this.humanAgent.getFormCache(humanGuid);
		if (cache != null) {
			cache.add(entity);
			// 分表子实体的处理;
			if (entity.getClass().getAnnotation(NotBlobHumanSubEntity.class) != null) {
				this.addDBOperationTypeCount(entity.getClass(), DBOperationType.INSERT);
				this.dbService.insert(entity);
				return entity.getId();
			}
			// 对角色实体进行更新;
			doHumanEntityUpdate(cache);
		} else {
			if (logger.isErrorEnabled()) {
				logger.error(String
						.format("The human guid: %d is not online, can not insert entity entityId: %d, type: %s",
								humanGuid, entity.getId(), entity.getClass().getSimpleName()));
			}
		}
		return entity.getId();
	}

	@Override
	public void update(E entity) {
		long humanGuid = entity.getHumanGuid();
		HumanCacheData cache = this.humanAgent.getFormCache(humanGuid);
		if (cache != null) {
			cache.update(entity);
			//cache.add(entity);
			// 分表子实体的处理;
			if (entity.getClass().isAnnotationPresent(NotBlobHumanSubEntity.class)) {
				this.addDBOperationTypeCount(entity.getClass(), DBOperationType.UPDATE);
				this.dbService.update(entity);
				return;
			}
			doHumanEntityUpdate(cache);
		} else {
			if (logger.isErrorEnabled()) {
				logger.error(String
						.format("The human guid: %d is not online, can not insert entity entityId: %d, type: %s",
								humanGuid, entity.getId(), entity.getClass().getSimpleName()));
			}
		}
	}

	private boolean doHumanEntityUpdate(HumanCacheData cache) {
		// 对角色实体进行更新;
//		HumanEntity humanEntity = this.converter.convert(cache);
//		if (cache == null) {
//			return false;
//		}
//		this.dbService.update(humanEntity);
		// editby: crazyjohn 使用异步更新策略;
		humanAgent.getModifiedSet().addModified(cache);
		return true;
	}

	@Override
	public void delete(Class<E> entityClass, long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(E entity) {
		long humanGuid = entity.getHumanGuid();
		HumanCacheData cache = this.humanAgent.getFormCache(humanGuid);
		if (cache != null) {
			cache.remove(entity);
			// 对角色实体进行更新;
			doHumanEntityUpdate(cache);
		} else {
			if (logger.isErrorEnabled()) {
				logger.error(String
						.format("The human guid: %d is not online, can not insert entity entityId: %d, type: %s",
								humanGuid, entity.getId(), entity.getClass().getSimpleName()));
			}
		}
	}

	@Override
	public Class<? extends IEntity>[] getBindClasses() {
		return bindClasses;
	}

}
