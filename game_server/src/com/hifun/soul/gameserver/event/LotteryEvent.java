package com.hifun.soul.gameserver.event;

/**
 * 抽奖事件;
 * 
 * @author crazyjohn
 * 
 */
public class LotteryEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.LOTTERY_EVENT;
	}

}
