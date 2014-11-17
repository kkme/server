package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 日常任务是否有可领取的奖励
 *
 * @author SevenSoul
 */
@Component
public class GCDailyQuestState extends GCMessage{
	
	/** 是否有奖励 */
	private boolean hasReward;

	public GCDailyQuestState (){
	}
	
	public GCDailyQuestState (
			boolean hasReward ){
			this.hasReward = hasReward;
	}

	@Override
	protected boolean readImpl() {
		hasReward = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(hasReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAILY_QUEST_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAILY_QUEST_STATE";
	}

	public boolean getHasReward(){
		return hasReward;
	}
		
	public void setHasReward(boolean hasReward){
		this.hasReward = hasReward;
	}
}