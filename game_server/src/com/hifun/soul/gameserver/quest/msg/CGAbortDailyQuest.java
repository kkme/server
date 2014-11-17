package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 取消日常任务
 * 
 * @author SevenSoul
 */
@Component
public class CGAbortDailyQuest extends CGMessage{
	
	/** 任务ID */
	private int questId;
	
	public CGAbortDailyQuest (){
	}
	
	public CGAbortDailyQuest (
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
		return MessageType.CG_ABORT_DAILY_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ABORT_DAILY_QUEST";
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