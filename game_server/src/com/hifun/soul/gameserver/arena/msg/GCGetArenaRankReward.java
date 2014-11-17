package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取竞技场排名奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetArenaRankReward extends GCMessage{
	
	/** 排名奖励领取状态 */
	private int rewardState;

	public GCGetArenaRankReward (){
	}
	
	public GCGetArenaRankReward (
			int rewardState ){
			this.rewardState = rewardState;
	}

	@Override
	protected boolean readImpl() {
		rewardState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_ARENA_RANK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_ARENA_RANK_REWARD";
	}

	public int getRewardState(){
		return rewardState;
	}
		
	public void setRewardState(int rewardState){
		this.rewardState = rewardState;
	}
}