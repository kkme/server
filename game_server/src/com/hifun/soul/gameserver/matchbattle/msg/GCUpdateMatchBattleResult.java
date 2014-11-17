package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 战斗完或轮空进行更新个人的消息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMatchBattleResult extends GCMessage{
	
	/** 个人战报内容 */
	private String content;
	/** 连胜次数 */
	private int consecutiveWinCount;
	/** 最大连胜次数 */
	private int maxConsecutiveWinCount;
	/** 胜利次数 */
	private int winCount;
	/** 失败次数 */
	private int loseCount;
	/** 奖励荣誉累计 */
	private int honourReward;
	/** 奖励金币累计 */
	private int coinReward;

	public GCUpdateMatchBattleResult (){
	}
	
	public GCUpdateMatchBattleResult (
			String content,
			int consecutiveWinCount,
			int maxConsecutiveWinCount,
			int winCount,
			int loseCount,
			int honourReward,
			int coinReward ){
			this.content = content;
			this.consecutiveWinCount = consecutiveWinCount;
			this.maxConsecutiveWinCount = maxConsecutiveWinCount;
			this.winCount = winCount;
			this.loseCount = loseCount;
			this.honourReward = honourReward;
			this.coinReward = coinReward;
	}

	@Override
	protected boolean readImpl() {
		content = readString();
		consecutiveWinCount = readInteger();
		maxConsecutiveWinCount = readInteger();
		winCount = readInteger();
		loseCount = readInteger();
		honourReward = readInteger();
		coinReward = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(content);
		writeInteger(consecutiveWinCount);
		writeInteger(maxConsecutiveWinCount);
		writeInteger(winCount);
		writeInteger(loseCount);
		writeInteger(honourReward);
		writeInteger(coinReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MATCH_BATTLE_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MATCH_BATTLE_RESULT";
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public int getConsecutiveWinCount(){
		return consecutiveWinCount;
	}
		
	public void setConsecutiveWinCount(int consecutiveWinCount){
		this.consecutiveWinCount = consecutiveWinCount;
	}

	public int getMaxConsecutiveWinCount(){
		return maxConsecutiveWinCount;
	}
		
	public void setMaxConsecutiveWinCount(int maxConsecutiveWinCount){
		this.maxConsecutiveWinCount = maxConsecutiveWinCount;
	}

	public int getWinCount(){
		return winCount;
	}
		
	public void setWinCount(int winCount){
		this.winCount = winCount;
	}

	public int getLoseCount(){
		return loseCount;
	}
		
	public void setLoseCount(int loseCount){
		this.loseCount = loseCount;
	}

	public int getHonourReward(){
		return honourReward;
	}
		
	public void setHonourReward(int honourReward){
		this.honourReward = honourReward;
	}

	public int getCoinReward(){
		return coinReward;
	}
		
	public void setCoinReward(int coinReward){
		this.coinReward = coinReward;
	}
}