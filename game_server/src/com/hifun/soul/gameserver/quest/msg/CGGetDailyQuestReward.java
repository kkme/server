package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求领取日常任务奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetDailyQuestReward extends CGMessage{
	
	/** 任务ID */
	private int questId;
	
	public CGGetDailyQuestReward (){
	}
	
	public CGGetDailyQuestReward (
			int questId ){
			this.questId = questId;
	}
	
	@Override
	protected boolean readImpl() {
		questId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_DAILY_QUEST_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_DAILY_QUEST_REWARD";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}

	@Override
	public void execute() {
	}
}