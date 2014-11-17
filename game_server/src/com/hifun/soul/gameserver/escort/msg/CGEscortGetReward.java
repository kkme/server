package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求领取奖励 
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortGetReward extends CGMessage{
	
	/** 奖励类型 */
	private int rewardType;
	
	public CGEscortGetReward (){
	}
	
	public CGEscortGetReward (
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
		return MessageType.CG_ESCORT_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_GET_REWARD";
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