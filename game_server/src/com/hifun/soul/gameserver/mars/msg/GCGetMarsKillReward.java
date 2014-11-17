package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应领取战神之巅击杀奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetMarsKillReward extends GCMessage{
	
	/** 奖励领取状态 */
	private int rewardState;

	public GCGetMarsKillReward (){
	}
	
	public GCGetMarsKillReward (
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
		return MessageType.GC_GET_MARS_KILL_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_MARS_KILL_REWARD";
	}

	public int getRewardState(){
		return rewardState;
	}
		
	public void setRewardState(int rewardState){
		this.rewardState = rewardState;
	}
}