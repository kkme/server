package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新刷新精英副本的计数器信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateEliteStageChallengeCounter extends GCMessage{
	
	/** 总共的次数 */
	private int totalCount;
	/** 剩余次数 */
	private int remainCount;
	/** 当前操作花费魔晶数量 */
	private int currencyNum;

	public GCUpdateEliteStageChallengeCounter (){
	}
	
	public GCUpdateEliteStageChallengeCounter (
			int totalCount,
			int remainCount,
			int currencyNum ){
			this.totalCount = totalCount;
			this.remainCount = remainCount;
			this.currencyNum = currencyNum;
	}

	@Override
	protected boolean readImpl() {
		totalCount = readInteger();
		remainCount = readInteger();
		currencyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalCount);
		writeInteger(remainCount);
		writeInteger(currencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_ELITE_STAGE_CHALLENGE_COUNTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_ELITE_STAGE_CHALLENGE_COUNTER";
	}

	public int getTotalCount(){
		return totalCount;
	}
		
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getRemainCount(){
		return remainCount;
	}
		
	public void setRemainCount(int remainCount){
		this.remainCount = remainCount;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}
}