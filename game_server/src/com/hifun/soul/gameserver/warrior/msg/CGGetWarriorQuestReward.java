package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetWarriorQuestReward extends CGMessage{
	
	
	public CGGetWarriorQuestReward (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_WARRIOR_QUEST_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_WARRIOR_QUEST_REWARD";
	}

	@Override
	public void execute() {
	}
}