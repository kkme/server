package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应领取奖励
 *
 * @author SevenSoul
 */
@Component
public class GCEscortGetReward extends GCMessage{
	
	/** 奖励类型 */
	private int rewardType;
	/** 是否有奖励 */
	private boolean hasReward;

	public GCEscortGetReward (){
	}
	
	public GCEscortGetReward (
			int rewardType,
			boolean hasReward ){
			this.rewardType = rewardType;
			this.hasReward = hasReward;
	}

	@Override
	protected boolean readImpl() {
		rewardType = readInteger();
		hasReward = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardType);
		writeBoolean(hasReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_GET_REWARD";
	}

	public int getRewardType(){
		return rewardType;
	}
		
	public void setRewardType(int rewardType){
		this.rewardType = rewardType;
	}

	public boolean getHasReward(){
		return hasReward;
	}
		
	public void setHasReward(boolean hasReward){
		this.hasReward = hasReward;
	}
}