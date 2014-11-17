package com.hifun.soul.gamedb.agent;

import java.util.List;

/**
 * 数据预加载的接口。
 * <p>
 * 
 * 需要利用查询预加载数据的Agent需要实现此接口。该接口会结合<tt>DataPreloadQuery</tt><br>
 * 类利用查询语句将数据加载到实现的Agent中。
 * 
 * @param <T>
 * 
 * @author crazyjohn
 */
public interface IDataPreloadableAgent<T> {

	/**
	 * 将数据加载到缓存中。
	 * 
	 * @param value
	 *            利用查询语句得到的查询结果
	 * @return 加载后数据在缓存中的形式
	 */
	List<T> loadDataToCache(List<?> value);

	/**
	 * 从缓存中获取所有数据。
	 * 
	 * @return
	 */
	List<T> getAllDataFromCache();
}
