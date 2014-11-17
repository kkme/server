package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知打开日常任务奖励箱子状态积分
 *
 * @author SevenSoul
 */
@Component
public class GCDailyQuestRewardBoxStateUpdate extends GCMessage{
	
	/** 箱子ID */
	private int boxId;
	/** 箱子状态 */
	private int state;

	public GCDailyQuestRewardBoxStateUpdate (){
	}
	
	public GCDailyQuestRewardBoxStateUpdate (
			int boxId,
			int state ){
			this.boxId = boxId;
			this.state = state;
	}

	@Override
	protected boolean readImpl() {
		boxId = readInteger();
		state = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(boxId);
		writeInteger(state);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAILY_QUEST_REWARD_BOX_STATE_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAILY_QUEST_REWARD_BOX_STATE_UPDATE";
	}

	public int getBoxId(){
		return boxId;
	}
		
	public void setBoxId(int boxId){
		this.boxId = boxId;
	}

	public int getState(){
		return state;
	}
		
	public void setState(int state){
		this.state = state;
	}
}