package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新任务
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateWarriorQuest extends GCMessage{
	
	/** 任务列表 */
	private com.hifun.soul.gameserver.warrior.WarriorQuest[] warriorQuests;

	public GCUpdateWarriorQuest (){
	}
	
	public GCUpdateWarriorQuest (
			com.hifun.soul.gameserver.warrior.WarriorQuest[] warriorQuests ){
			this.warriorQuests = warriorQuests;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		warriorQuests = new com.hifun.soul.gameserver.warrior.WarriorQuest[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.warrior.WarriorQuest objwarriorQuests = new com.hifun.soul.gameserver.warrior.WarriorQuest();
			warriorQuests[i] = objwarriorQuests;
					objwarriorQuests.setId(readInteger());
							objwarriorQuests.setTotalCount(readInteger());
							objwarriorQuests.setCompleteCount(readInteger());
							objwarriorQuests.setDamageHpPercent(readInteger());
							objwarriorQuests.setQuestState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(warriorQuests.length);
	for(int i=0; i<warriorQuests.length; i++){
	com.hifun.soul.gameserver.warrior.WarriorQuest objwarriorQuests = warriorQuests[i];
				writeInteger(objwarriorQuests.getId());
				writeInteger(objwarriorQuests.getTotalCount());
				writeInteger(objwarriorQuests.getCompleteCount());
				writeInteger(objwarriorQuests.getDamageHpPercent());
				writeInteger(objwarriorQuests.getQuestState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_WARRIOR_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_WARRIOR_QUEST";
	}

	public com.hifun.soul.gameserver.warrior.WarriorQuest[] getWarriorQuests(){
		return warriorQuests;
	}

	public void setWarriorQuests(com.hifun.soul.gameserver.warrior.WarriorQuest[] warriorQuests){
		this.warriorQuests = warriorQuests;
	}	
}