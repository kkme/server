package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求打开日常任务奖励箱子
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenDailyQuestRewardBox extends CGMessage{
	
	/** 箱子ID */
	private int boxId;
	
	public CGOpenDailyQuestRewardBox (){
	}
	
	public CGOpenDailyQuestRewardBox (
			int boxId ){
			this.boxId = boxId;
	}
	
	@Override
	protected boolean readImpl() {
		boxId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(boxId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_DAILY_QUEST_REWARD_BOX;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_DAILY_QUEST_REWARD_BOX";
	}

	public int getBoxId(){
		return boxId;
	}
		
	public void setBoxId(int boxId){
		this.boxId = boxId;
	}

	@Override
	public void execute() {
	}
}