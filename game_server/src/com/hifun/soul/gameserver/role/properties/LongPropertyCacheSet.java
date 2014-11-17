package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.gamedb.cache.CacheEntry;

/**
 * 带缓存的长整形属性集;
 * 
 * @author crazyjohn
 * 
 */
public abstract class LongPropertyCacheSet extends LongPropertySet {
	private CacheEntry<Integer, Integer> propsCache = new CacheEntry<Integer, Integer>();

	public LongPropertyCacheSet(Class<Long> clazz, int size, int properType) {
		super(clazz, size, properType);
	}

	@Override
	public void setPropertyValue(int index, Long value) {
		super.setPropertyValue(index, value);
		this.propsCache.addUpdate(index, index);
	}
	
	/**
	 * 获取所有脏数据;
	 * 
	 * @return
	 */
	public CacheEntry<Integer, Integer> getCacheData() {
		return this.propsCache;
	}
	
	public boolean isModified() {
		return this.propsCache.getUpdateDataSize() > 0;
	}

}
