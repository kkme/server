package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应加速完成日常任务
 *
 * @author SevenSoul
 */
@Component
public class GCApplyQuicklyCompleteDailyQuest extends GCMessage{
	
	/** 任务ID */
	private int questId;
	/** 消耗魔晶 */
	private int costCrystal;

	public GCApplyQuicklyCompleteDailyQuest (){
	}
	
	public GCApplyQuicklyCompleteDailyQuest (
			int questId,
			int costCrystal ){
			this.questId = questId;
			this.costCrystal = costCrystal;
	}

	@Override
	protected boolean readImpl() {
		questId = readInteger();
		costCrystal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questId);
		writeInteger(costCrystal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_APPLY_QUICKLY_COMPLETE_DAILY_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_QUICKLY_COMPLETE_DAILY_QUEST";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}

	public int getCostCrystal(){
		return costCrystal;
	}
		
	public void setCostCrystal(int costCrystal){
		this.costCrystal = costCrystal;
	}
}