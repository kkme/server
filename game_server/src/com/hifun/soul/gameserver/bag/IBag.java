package com.hifun.soul.gameserver.bag;

import java.util.List;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.gameserver.item.Item;

public interface IBag {
	/**
	 * 背包容量大小
	 * @return
	 */
	int getSize();
	
	/**
	 * 放物品
	 * @param item
	 * @throws Exception 
	 */
	boolean putItem(Item item,boolean isNotifyUpdate, ItemLogReason reason, String param);
	
	/**
	 * 将物品放入指定的背包格
	 * @param item
	 * @param index
	 */
	void putItem(Item item,int index,boolean isNotifyUpdate, ItemLogReason reason, String param);
	
	/**
	 * 获取物品
	 * @param index
	 * @return
	 */
	Item getItem(int index);
	
	/**
	 * 获取所有物品列表
	 * @return
	 */
	List<Item> getItems();
	
	/**
	 * 移除物品
	 * @param index
	 */
	void removeItem(int index,boolean isNotifyUpdate, ItemLogReason reason, String param);
	
	/**
	 * 移除指定位置一定数量的物品
	 * @param index
	 */
	void removeItem(int index,int removeNum,boolean isNotifyUpdate, ItemLogReason reason, String param);
	
	/**
	 * 清空物品
	 */
	void clear(ItemLogReason reason, String param);
	
	/**
	 * 是否已满
	 * @return
	 */
	boolean isFull();
	
	/**
	 * 获取物品列表
	 * @param fromIndex 背包起始位置
	 * @param count 物品数
	 * @return
	 */
	List<Item> getItems(int fromIndex, int count);
	
	/**
	 * 获取背包类型
	 * 
	 * @return
	 */
	BagType getBagType();
	

	/**
	 * 根据物品Uuid获取物品
	 * @param itemUuid
	 * @return
	 */
	Item getItemByUuid(String itemUuid);
	
	
	/**
	 * 移动物品
	 * @param sourceIndex
	 * @param targetIndex
	 */
	void moveItem(int sourceIndex, int targetIndex, ItemLogReason reason, String param);
}
