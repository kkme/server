package com.hifun.soul.gameserver.common;

import com.hifun.soul.core.orm.IDBService;

/**
 * 全局管理器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IGlobalManager {
	/**
	 * 从db加载数据, 构建响应的业务数据;
	 * 
	 * @param dbService
	 */
	public void loadDataFromDB(IDBService dbService);
}
