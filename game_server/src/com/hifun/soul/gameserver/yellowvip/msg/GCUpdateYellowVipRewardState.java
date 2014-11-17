package com.hifun.soul.gameserver.yellowvip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新黄钻礼包领取状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateYellowVipRewardState extends GCMessage{
	
	/** 领取奖励类型 */
	private int rewardType;
	/** 领取状态(0不可领取,1可领取，2已领取) */
	private int[] rewardState;

	public GCUpdateYellowVipRewardState (){
	}
	
	public GCUpdateYellowVipRewardState (
			int rewardType,
			int[] rewardState ){
			this.rewardType = rewardType;
			this.rewardState = rewardState;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		rewardType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardState = new int[count];
		for(int i=0; i<count; i++){
			rewardState[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardType);
	writeShort(rewardState.length);
	for(int i=0; i<rewardState.length; i++){
	Integer objrewardState = rewardState[i];
			writeInteger(objrewardState);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_YELLOW_VIP_REWARD_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_YELLOW_VIP_REWARD_STATE";
	}

	public int getRewardType(){
		return rewardType;
	}
		
	public void setRewardType(int rewardType){
		this.rewardType = rewardType;
	}

	public int[] getRewardState(){
		return rewardState;
	}

	public void setRewardState(int[] rewardState){
		this.rewardState = rewardState;
	}	
}