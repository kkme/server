package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端更新计数器
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateQuestCounter extends GCMessage{
	
	/** 任务ID */
	private int questId;
	/** 任务目标 */
	private com.hifun.soul.gameserver.human.quest.QuestAimInfo[] questAims;

	public GCUpdateQuestCounter (){
	}
	
	public GCUpdateQuestCounter (
			int questId,
			com.hifun.soul.gameserver.human.quest.QuestAimInfo[] questAims ){
			this.questId = questId;
			this.questAims = questAims;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		questId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		questAims = new com.hifun.soul.gameserver.human.quest.QuestAimInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.human.quest.QuestAimInfo objquestAims = new com.hifun.soul.gameserver.human.quest.QuestAimInfo();
			questAims[i] = objquestAims;
					objquestAims.setAimType(readInteger());
							objquestAims.setAimIndex(readInteger());
							objquestAims.setAimValue(readInteger());
							objquestAims.setCurrentValue(readInteger());
							objquestAims.setDesc(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questId);
	writeShort(questAims.length);
	for(int i=0; i<questAims.length; i++){
	com.hifun.soul.gameserver.human.quest.QuestAimInfo objquestAims = questAims[i];
				writeInteger(objquestAims.getAimType());
				writeInteger(objquestAims.getAimIndex());
				writeInteger(objquestAims.getAimValue());
				writeInteger(objquestAims.getCurrentValue());
				writeString(objquestAims.getDesc());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_QUEST_COUNTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_QUEST_COUNTER";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}

	public com.hifun.soul.gameserver.human.quest.QuestAimInfo[] getQuestAims(){
		return questAims;
	}

	public void setQuestAims(com.hifun.soul.gameserver.human.quest.QuestAimInfo[] questAims){
		this.questAims = questAims;
	}	
}