package com.hifun.soul.gamedb.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 各个业务Manager用来存放更新的缓存。<p>
 * 
 * 使用方法：<br>
 *   1. 创建：<br>
 *   (<b>注意，创建时需要指定唯一识别某个更新的key的类型以及更新的数据类型，<br>
 *   一般情况下，key的类型为GUID的类型，更新的数据类型为业务类型</b>)<pre>
 *     <code>CacheEntry<Long, Item> cache = new CacheEntry<Long, Item>();</code></pre>
 *   2. 增加一个更新的数据缓存：<br><pre>
 *     <code>cache.addUpdate(111111L, item);</code></pre>
 *   3. 增加一个删除的数据缓存：<br><pre>
 *     <code>cache.addDelete(111111L);</code></pre>
 *   4. 删除一个已经存在的更新的数据缓存:<br>
 *   (<b>注意：这里只删除更新的数据缓存，删除的数据缓存不会变化</b>)<br><pre>
 *     <code>cache.addUpdate(111111L, item);</code>
 *     <code>cache.addUpdate(222222L, item);</code>
 *     <code>cache.addDelete(333333L);</code> 
 *     <code>cache.deleteUpdateFromCache(111111L); //该操作会将先前加入的111111L的更新缓存删除!</code>
 *     <code>cache.deleteUpdateFromCache(333333L); //该操作不会发生任何变化!</code>
 *     </pre>
 *   5. 获取需要更新的全部数据：<br><pre>
 *     <code>List<Item> list = ce.getAllUpdateData();</code></pre>
 *   6. 获取需要删除的全部key：<br><pre>
 *     <code>List<Long> list = ce.getAllDeleteKey();</code></pre>
 *
 * @param <K> 唯一识别缓存的key的类型
 * @param <V> 缓存的数据类型
 * 
 * @author crazyjohn
 * 
 */
public class CacheEntry<K, V> {
	
	private Map<K, V> updateCacheMap = new HashMap<K, V>();
	private Map<K, V> deleteCacheSet = new HashMap<K, V>();
	
	/**
	 * 增加一个需要更新的数据缓存。<p>
	 * 如果之前该数据已经被缓存，则只做相应的更新。
	 * 
	 * @param id	唯一识别更新数据的ID(key)
	 * @param data	需要更新的数据
	 */
	public void addUpdate(K id, V data){
		if(CacheCfg.isOff()) return;
		if(id == null) throw new IllegalArgumentException("The id of cache can not be null!");
		if(data == null) throw new IllegalArgumentException("The data of cache can not be null!");
		
		if(deleteCacheSet.containsKey(id)) deleteCacheSet.remove(id);

		updateCacheMap.put(id, data);
	}
	
	/**
	 * 增加一个需要删除的数据的ID。<p>
	 * 如果之前该ID已经被缓存，则只做相应的更新。
	 * 
	 * @param id 需要删除的数据的ID(key)
	 */
	public void addDelete(K id, V value){
		if(CacheCfg.isOff()) return;
		if(id == null) throw new IllegalArgumentException("The CacheEntry can not add a delete cache with id NULL!");
		
		if(updateCacheMap.containsKey(id)) {//已经是脏数据了
			updateCacheMap.remove(id);
		}
		
		deleteCacheSet.put(id, value);
	}
	
	/**
	 * 从缓存中删除一个已经加入到缓存中的需要更新的数据。<p>
	 * <b>注意：该方法只能删除缓存中标志为"更新"的数据。<br>
	 * 如果该数据在缓存中标志为"删除"，则该方法不会发生任何作用</b>
	 * 
	 * @param id 唯一识别更新数据的ID(key)
	 */
	public void cancelAddUpdate(K id){
		if(CacheCfg.isOff()) return;
		if(id == null) throw new IllegalArgumentException("The CacheEntry can not delete a update operation with id NULL!");
		
		if(updateCacheMap.containsKey(id)) updateCacheMap.remove(id);
	}
	
	/**
	 * 获取所有需要更新的数据。
	 * 
	 * @return
	 */
	public List<V> getAllUpdateData(){
		List<V> list = new ArrayList<V>(updateCacheMap.values());
		updateCacheMap.clear();
		return list;
	}

	/**
	 * 获取所有需要删除的ID。
	 * 
	 * @return
	 */
	public List<V> getAllDeleteData() {
		List<V> keyList = new ArrayList<V>(deleteCacheSet.values());
		deleteCacheSet.clear();
		return keyList;
	}
	
	public int getUpdateDataSize() {
		return this.updateCacheMap.size();
	}
	
}
