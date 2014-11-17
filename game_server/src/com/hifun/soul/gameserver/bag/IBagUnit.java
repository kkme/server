package com.hifun.soul.gameserver.bag;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.gameserver.item.Item;

/**
 * 背包单元接口
 * @author magicstone
 *
 */
public interface IBagUnit {
	
	
	/**
	 * 判断单元格是否为空
	 * @return
	 */
	boolean isEmpty();

	/**
	 * 将物品放入单元格
	 * 若单元格中已经存在不可叠加物品（或者已存在物品可叠加，但是与放入物品不属于同类），会替换原有物品
	 * 若单元格中已存在相同类的可叠加物品，该单元格中的物品会累积
	 * @param item
	 */
	void putItem(Item item, boolean isNotifyUpdate, ItemLogReason reason, String param);
	
	/**
	 * 获取物品
	 * @return
	 */
	Item getItem();

	/**
	 * 移除物品
	 * @param isNotifyUpdate
	 * @param reason
	 * @param param
	 */
	void removeItem(boolean isNotifyUpdate, ItemLogReason reason, String param);
	
	/**
	 * 获取背包格的索引位置
	 */
	int getIndex();
	
	/**
	 * 发送更新消息
	 */
	void sendItemUpdateMessage();

	/**
	 * 更新物品
	 */
	void updateItem();
	
	/**
	 * 逻辑移除物品，不更新到数据库
	 * @param isNotifyUpdate
	 * @param reason
	 * @param param
	 */
	void logicRemoveItem(boolean isNotifyUpdate, ItemLogReason reason, String param);
}
