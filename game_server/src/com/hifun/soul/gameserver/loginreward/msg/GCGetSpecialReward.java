package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取特殊奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetSpecialReward extends GCMessage{
	
	/** 特殊奖励类型 */
	private int specialRewardType;
	/** 奖励状态 */
	private int state;

	public GCGetSpecialReward (){
	}
	
	public GCGetSpecialReward (
			int specialRewardType,
			int state ){
			this.specialRewardType = specialRewardType;
			this.state = state;
	}

	@Override
	protected boolean readImpl() {
		specialRewardType = readInteger();
		state = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(specialRewardType);
		writeInteger(state);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_SPECIAL_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_SPECIAL_REWARD";
	}

	public int getSpecialRewardType(){
		return specialRewardType;
	}
		
	public void setSpecialRewardType(int specialRewardType){
		this.specialRewardType = specialRewardType;
	}

	public int getState(){
		return state;
	}
		
	public void setState(int state){
		this.state = state;
	}
}