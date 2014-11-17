package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新任务状态
 *
 * @author SevenSoul
 */
@Component
public class GCQuestState extends GCMessage{
	
	/** 任务ID */
	private int questId;
	/** 任务状态 */
	private int questState;

	public GCQuestState (){
	}
	
	public GCQuestState (
			int questId,
			int questState ){
			this.questId = questId;
			this.questState = questState;
	}

	@Override
	protected boolean readImpl() {
		questId = readInteger();
		questState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questId);
		writeInteger(questState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUEST_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUEST_STATE";
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
}