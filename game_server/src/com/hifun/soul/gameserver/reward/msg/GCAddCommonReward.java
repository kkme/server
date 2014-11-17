package com.hifun.soul.gameserver.reward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 添加奖励到列表中
 *
 * @author SevenSoul
 */
@Component
public class GCAddCommonReward extends GCMessage{
	
	/** 奖励信息 */
	private com.hifun.soul.gameserver.reward.RewardBaseInfo reward;

	public GCAddCommonReward (){
	}
	
	public GCAddCommonReward (
			com.hifun.soul.gameserver.reward.RewardBaseInfo reward ){
			this.reward = reward;
	}

	@Override
	protected boolean readImpl() {
		reward = new com.hifun.soul.gameserver.reward.RewardBaseInfo();
						reward.setId(readInteger());
						reward.setName(readString());
						reward.setIcon(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(reward.getId());
		writeString(reward.getName());
		writeInteger(reward.getIcon());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ADD_COMMON_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_COMMON_REWARD";
	}

	public com.hifun.soul.gameserver.reward.RewardBaseInfo getReward(){
		return reward;
	}
		
	public void setReward(com.hifun.soul.gameserver.reward.RewardBaseInfo reward){
		this.reward = reward;
	}
}