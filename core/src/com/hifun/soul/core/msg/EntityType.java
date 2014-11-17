package com.hifun.soul.core.msg;

import com.hifun.soul.core.orm.IEntity;

/**
 * 实体类型定义
 * 
 * @param <T>
 */
public class EntityType<T extends IEntity> {
	public final Class<T> entityClass;
	public final short type;
	public final EntityCreator<T> creator;

	public EntityType(Class<T> entityClass, short type, EntityCreator<T> creator) {
		this.creator = creator;
		this.entityClass = entityClass;
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		EntityType<?> _e = (EntityType<?>) obj;
		return _e.entityClass == this.entityClass;
	}

	@Override
	public int hashCode() {
		return this.entityClass.hashCode();
	}

	/**
	 * 实体类型创建器
	 * 
	 * @author SevenSoul
	 * 
	 * @param <T>
	 */
	public static interface EntityCreator<T extends IEntity> {
		/**
		 * 创建实体对象
		 * 
		 * @return
		 */
		public T createEntity();

		/**
		 * 创建实体对象的消息对象
		 * 
		 * @return
		 */
		public BaseEntityMsg<T> creageEntityMessage();
	}
}
