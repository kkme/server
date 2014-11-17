package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.gamedb.cache.CacheEntry;

/**
 * 带缓存的Int属性集;
 * 
 * @author crazyjohn
 * 
 */
public abstract class IntPropertyCacheSet extends IntPropertySet {
	/** 属性缓存 */
	private CacheEntry<Integer, Integer> propsCache = new CacheEntry<Integer, Integer>();

	public IntPropertyCacheSet(Class<Integer> clazz, int size, int properType) {
		super(clazz, size, properType);
	}

	@Override
	public void setPropertyValue(int index, Integer value) {
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
