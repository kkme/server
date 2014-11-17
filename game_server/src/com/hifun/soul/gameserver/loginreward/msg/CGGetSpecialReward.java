package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取特殊奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetSpecialReward extends CGMessage{
	
	/** 特殊奖励类型 */
	private int specialRewardType;
	
	public CGGetSpecialReward (){
	}
	
	public CGGetSpecialReward (
			int specialRewardType ){
			this.specialRewardType = specialRewardType;
	}
	
	@Override
	protected boolean readImpl() {
		specialRewardType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(specialRewardType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_SPECIAL_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SPECIAL_REWARD";
	}

	public int getSpecialRewardType(){
		return specialRewardType;
	}
		
	public void setSpecialRewardType(int specialRewardType){
		this.specialRewardType = specialRewardType;
	}

	@Override
	public void execute() {
	}
}