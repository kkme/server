package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetReward extends CGMessage{
	
	/** 领取奖励 */
	private int index;
	
	public CGGetReward (){
	}
	
	public CGGetReward (
			int index ){
			this.index = index;
	}
	
	@Override
	protected boolean readImpl() {
		index = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_REWARD";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	@Override
	public void execute() {
	}
}