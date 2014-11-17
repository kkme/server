package com.hifun.soul.gamedb.cache;

import com.hifun.soul.gamedb.util.LRUHashMap;

/**
 * 可以被缓存的对象接口;<br>
 * 
 * <pre>
 * 1. 可以被缓存到 {@link LRUHashMap}中;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface ICacheObject {
	
	/**
	 * 实体是否被修改
	 * 
	 * @return 返回实体是否被修改 true 已经被修改; false 没有被修改
	 */
	public boolean isModified();

	/**
	 * 重新设置修改位
	 */
	public void resetModified();
	
}
