package com.hifun.soul.gameserver.human;

import com.hifun.soul.gamedb.entity.HumanEntity;

/**
 * 玩家身上有持久化业务的管理器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IHumanPersistenceManager {

	/**
	 * 获取所属的玩家;
	 * 
	 * @return 返回所属的玩家;
	 */
	public Human getOwner();

	/**
	 * 从角色实体中加载数据;
	 * 
	 * @param humanEntity
	 *            角色实体;
	 */
	public void onLoad(HumanEntity humanEntity);

	/**
	 * 把数据持持久化到DB中;
	 * 
	 * @param humanEntity
	 */
	public void onPersistence(HumanEntity humanEntity);
}
