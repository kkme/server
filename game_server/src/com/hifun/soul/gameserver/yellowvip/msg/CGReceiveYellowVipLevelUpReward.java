package com.hifun.soul.gameserver.yellowvip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取黄钻升级礼包请求
 * 
 * @author SevenSoul
 */
@Component
public class CGReceiveYellowVipLevelUpReward extends CGMessage{
	
	/** 升级奖励索引 */
	private int rewardIndex;
	
	public CGReceiveYellowVipLevelUpReward (){
	}
	
	public CGReceiveYellowVipLevelUpReward (
			int rewardIndex ){
			this.rewardIndex = rewardIndex;
	}
	
	@Override
	protected boolean readImpl() {
		rewardIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RECEIVE_YELLOW_VIP_LEVEL_UP_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RECEIVE_YELLOW_VIP_LEVEL_UP_REWARD";
	}

	public int getRewardIndex(){
		return rewardIndex;
	}
		
	public void setRewardIndex(int rewardIndex){
		this.rewardIndex = rewardIndex;
	}

	@Override
	public void execute() {
	}
}