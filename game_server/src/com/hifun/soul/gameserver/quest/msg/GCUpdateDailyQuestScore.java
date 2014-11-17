package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端端更新玩家日常任务积分
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateDailyQuestScore extends GCMessage{
	
	/** 当前日常任务的积分 */
	private int currentScore;

	public GCUpdateDailyQuestScore (){
	}
	
	public GCUpdateDailyQuestScore (
			int currentScore ){
			this.currentScore = currentScore;
	}

	@Override
	protected boolean readImpl() {
		currentScore = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentScore);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_DAILY_QUEST_SCORE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_DAILY_QUEST_SCORE";
	}

	public int getCurrentScore(){
		return currentScore;
	}
		
	public void setCurrentScore(int currentScore){
		this.currentScore = currentScore;
	}
}