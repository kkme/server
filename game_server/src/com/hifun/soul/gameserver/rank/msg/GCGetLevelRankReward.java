package com.hifun.soul.gameserver.rank.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取排行奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetLevelRankReward extends GCMessage{
	
	/** 获得奖励的金币数 */
	private int rewardCoinNum;

	public GCGetLevelRankReward (){
	}
	
	public GCGetLevelRankReward (
			int rewardCoinNum ){
			this.rewardCoinNum = rewardCoinNum;
	}

	@Override
	protected boolean readImpl() {
		rewardCoinNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardCoinNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_LEVEL_RANK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_LEVEL_RANK_REWARD";
	}

	public int getRewardCoinNum(){
		return rewardCoinNum;
	}
		
	public void setRewardCoinNum(int rewardCoinNum){
		this.rewardCoinNum = rewardCoinNum;
	}
}