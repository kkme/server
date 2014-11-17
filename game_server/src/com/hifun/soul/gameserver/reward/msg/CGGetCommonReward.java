package com.hifun.soul.gameserver.reward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetCommonReward extends CGMessage{
	
	/** 奖励id */
	private int id;
	
	public CGGetCommonReward (){
	}
	
	public CGGetCommonReward (
			int id ){
			this.id = id;
	}
	
	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_COMMON_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_COMMON_REWARD";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	@Override
	public void execute() {
	}
}