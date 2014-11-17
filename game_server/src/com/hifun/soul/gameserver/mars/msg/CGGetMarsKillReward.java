package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取战神之巅击杀奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetMarsKillReward extends CGMessage{
	
	
	public CGGetMarsKillReward (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_MARS_KILL_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_MARS_KILL_REWARD";
	}

	@Override
	public void execute() {
	}
}