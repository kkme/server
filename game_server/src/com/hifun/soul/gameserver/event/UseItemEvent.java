package com.hifun.soul.gameserver.event;

/**
 * 使用物品事件;
 * 
 * @author crazyjohn
 * 
 */
public class UseItemEvent implements IQuestEvent {
	private int itemId;
	
	@Override
	public EventType getType() {
		return EventType.USE_ITEM_EVENT;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
