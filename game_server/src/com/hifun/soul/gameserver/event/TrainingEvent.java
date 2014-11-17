package com.hifun.soul.gameserver.event;

/**
 * 训练事件;
 * 
 * @author crazyjohn
 * 
 */
public class TrainingEvent implements IQuestEvent {

	@Override
	public EventType getType() {
		return EventType.TRAINING_EVENT;
	}

}
