package com.hifun.soul.gameserver.event;

/**
 * 收集物品事件;
 * 
 * @author crazyjohn
 * 
 */
public class CollectItemEvent implements IQuestEvent {
	private int itemId;

	@Override
	public EventType getType() {
		return EventType.COLLECT_ITEM_EVENT;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
