package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新所有奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetWarriorQuestReward extends GCMessage{
	
	/** 奖励 */
	private com.hifun.soul.gameserver.warrior.WarriorQuestReward[] rewards;

	public GCGetWarriorQuestReward (){
	}
	
	public GCGetWarriorQuestReward (
			com.hifun.soul.gameserver.warrior.WarriorQuestReward[] rewards ){
			this.rewards = rewards;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rewards = new com.hifun.soul.gameserver.warrior.WarriorQuestReward[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.warrior.WarriorQuestReward objrewards = new com.hifun.soul.gameserver.warrior.WarriorQuestReward();
			rewards[i] = objrewards;
					objrewards.setQuestId(readInteger());
							objrewards.setCoin(readInteger());
							objrewards.setExp(readInteger());
							objrewards.setTechPoint(readInteger());
							objrewards.setState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(rewards.length);
	for(int i=0; i<rewards.length; i++){
	com.hifun.soul.gameserver.warrior.WarriorQuestReward objrewards = rewards[i];
				writeInteger(objrewards.getQuestId());
				writeInteger(objrewards.getCoin());
				writeInteger(objrewards.getExp());
				writeInteger(objrewards.getTechPoint());
				writeInteger(objrewards.getState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_WARRIOR_QUEST_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_WARRIOR_QUEST_REWARD";
	}

	public com.hifun.soul.gameserver.warrior.WarriorQuestReward[] getRewards(){
		return rewards;
	}

	public void setRewards(com.hifun.soul.gameserver.warrior.WarriorQuestReward[] rewards){
		this.rewards = rewards;
	}	
}