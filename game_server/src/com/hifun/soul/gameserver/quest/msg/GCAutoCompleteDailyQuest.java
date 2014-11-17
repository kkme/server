package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应自动完成日常任务
 *
 * @author SevenSoul
 */
@Component
public class GCAutoCompleteDailyQuest extends GCMessage{
	
	/** 任务ID */
	private int questId;
	/** 任务状态 */
	private int questState;
	/** 剩余时间 */
	private int remainTime;

	public GCAutoCompleteDailyQuest (){
	}
	
	public GCAutoCompleteDailyQuest (
			int questId,
			int questState,
			int remainTime ){
			this.questId = questId;
			this.questState = questState;
			this.remainTime = remainTime;
	}

	@Override
	protected boolean readImpl() {
		questId = readInteger();
		questState = readInteger();
		remainTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questId);
		writeInteger(questState);
		writeInteger(remainTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_AUTO_COMPLETE_DAILY_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_AUTO_COMPLETE_DAILY_QUEST";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}

	public int getQuestState(){
		return questState;
	}
		
	public void setQuestState(int questState){
		this.questState = questState;
	}

	public int getRemainTime(){
		return remainTime;
	}
		
	public void setRemainTime(int remainTime){
		this.remainTime = remainTime;
	}
}