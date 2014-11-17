package com.hifun.soul.gameserver.event;

/**
 * 战斗胜利事件;
 * 
 * @author crazyjohn
 * 
 */
public class BattleWinEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.BATTLE_WIN_EVENT;
	}

}
