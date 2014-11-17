package com.hifun.soul.gamedb.cache;

import java.util.List;

import com.hifun.soul.core.orm.IEntity;

/**
 * 可以注册为缓存的组件接口。<br>
 * 实现了该接口的类可以注册到CacheManager中，<br>
 * 由CacheManager定时发送缓存的数据到DB。
 * 
 * @author crazyjohn
 * 
 */
public interface ICachableComponent {
	/**
	 * 获取所有需要更新的实体;
	 * 
	 * @return
	 */
	List<IEntity> getUpdateEntities();

	/**
	 * 获取所有需要删除的实体;
	 * 
	 * @return
	 */
	List<IEntity> getDeleteEntities();
}
