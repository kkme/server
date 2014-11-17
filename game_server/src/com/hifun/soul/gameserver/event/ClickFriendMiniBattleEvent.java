package com.hifun.soul.gameserver.event;

public class ClickFriendMiniBattleEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.CLICK_FRIEND_MINI_BATTLE_EVENT;
	}

}
