package com.hifun.soul.gamedb.agent;

import com.hifun.soul.gamedb.util.ModifiedSet;

/**
 * 带缓存的实体代理接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ICacheableAgent<K, V> {

	/**
	 * 获取更改数据集;
	 * 
	 * @return 返回此代理所代理数据的更改集;
	 */
	public ModifiedSet<?> getModifiedSet();

	/**
	 * 执行淘汰动作;
	 */
	public void evict();

	/**
	 * 从缓存中取出指定的数据;
	 * 
	 * @param key
	 *            指定数据的KEY值;
	 * @return 返回KEY对应的缓存数据, 如果数据不存在,可能会返回NULL;
	 */
	public V getFormCache(K key);

	/**
	 * 设置指定的数据到缓存中;
	 * 
	 * @param key
	 * @param value
	 */
	public void setToCache(K key, V value);
}
