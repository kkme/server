package com.hifun.soul.gameserver.yellowvip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取黄钻礼包请求
 * 
 * @author SevenSoul
 */
@Component
public class CGReceiveYellowVipReward extends CGMessage{
	
	/** 领取奖励类型 */
	private int rewardType;
	
	public CGReceiveYellowVipReward (){
	}
	
	public CGReceiveYellowVipReward (
			int rewardType ){
			this.rewardType = rewardType;
	}
	
	@Override
	protected boolean readImpl() {
		rewardType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RECEIVE_YELLOW_VIP_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RECEIVE_YELLOW_VIP_REWARD";
	}

	public int getRewardType(){
		return rewardType;
	}
		
	public void setRewardType(int rewardType){
		this.rewardType = rewardType;
	}

	@Override
	public void execute() {
	}
}