package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新当前奖励领取状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateWarriorQuestRewardState extends GCMessage{
	
	/** 状态 */
	private int state;

	public GCUpdateWarriorQuestRewardState (){
	}
	
	public GCUpdateWarriorQuestRewardState (
			int state ){
			this.state = state;
	}

	@Override
	protected boolean readImpl() {
		state = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(state);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_WARRIOR_QUEST_REWARD_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_WARRIOR_QUEST_REWARD_STATE";
	}

	public int getState(){
		return state;
	}
		
	public void setState(int state){
		this.state = state;
	}
}