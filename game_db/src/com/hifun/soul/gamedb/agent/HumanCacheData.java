package com.hifun.soul.gamedb.agent;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.ICacheObject;
import com.hifun.soul.gamedb.cache.holder.HumanEntityHolderCreater;
import com.hifun.soul.gamedb.cache.holder.IEntityHolder;
import com.hifun.soul.gamedb.entity.HumanEntity;

/**
 * 角色缓存数据;
 * 
 * @author crazyjohn
 * 
 */
public class HumanCacheData implements ICacheObject, IEntity {
	private long humanGuid;
	/** 实体持有器集合 */
	private Map<Class<? extends IEntity>, IEntityHolder<? extends IEntity>> holders;

	public HumanCacheData() {
		this.holders = HumanEntityHolderCreater.getHumanEntityHolder();
	}

	public HumanEntity toEntity() {
		// TODO 把缓存转换为实体对象
		return null;
	}

	@SuppressWarnings("unchecked")
	public <E extends IEntity> void add(E entity) {
		IEntityHolder<E> holder = (IEntityHolder<E>) holders.get(entity
				.getClass());
		if (holder == null) {
			return;
		}
		holder.add(entity);
	}

	@SuppressWarnings("unchecked")
	public <E extends IEntity> void update(E entity) {
		IEntityHolder<E> holder = (IEntityHolder<E>) holders.get(entity
				.getClass());
		if (holder == null) {
			return;
		}
		holder.update(entity);
	}

	@SuppressWarnings("unchecked")
	public <E extends IEntity> void remove(E entity) {
		IEntityHolder<E> holder = (IEntityHolder<E>) holders.get(entity
				.getClass());
		if (holder == null) {
			return;
		}
		holder.remove(entity.getId());
	}

	/**
	 * 根据实体类型, 获取实体集;
	 * 
	 * @param entityClass
	 * @return 不会返回null;
	 */
	public <E extends IEntity> Collection<E> getEntites(Class<E> entityClass) {
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.holders
				.get(entityClass);
		if (holder == null) {
			return Collections.emptyList();
		}
		return holder.getEntities();
	}

	/**
	 * 获取指定类型的实体;
	 * 
	 * @param entityClass
	 * @return 如果没有此类型的实体, 返回null;
	 */
	public <E extends IEntity> E getEntity(Class<E> entityClass) {
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.holders
				.get(entityClass);
		if (holder == null) {
			return null;
		}
		return holder.getEntities().iterator().next();
	}

	/**
	 * 判断指定的实体是否已经更新了;
	 * 
	 * @param entityClass
	 * @return
	 */
	public <E extends IEntity> boolean isModified(Class<E> entityClass) {
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.holders
				.get(entityClass);
		if (holder == null) {
			return false;
		}
		return holder.isModified();
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	@Override
	public boolean isModified() {
		for (IEntityHolder<? extends IEntity> holder : holders.values()) {
			if (holder.isModified()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void resetModified() {
		for (IEntityHolder<? extends IEntity> holder : holders.values()) {
			holder.resetModified();
		}
	}

	@Override
	public Serializable getId() {
		return this.humanGuid;
	}

	@Override
	public void setId(Serializable id) {
		this.humanGuid = (Long) id;
	}

	@Override
	public void read(ByteBuffer buffer) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(ByteBuffer buffer) throws IOException {
		throw new UnsupportedOperationException();
	}

}
