package com.hifun.soul.gameserver.onlinereward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取在线奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetOnlineReward extends CGMessage{
	
	
	public CGGetOnlineReward (){
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
		return MessageType.CG_GET_ONLINE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_ONLINE_REWARD";
	}

	@Override
	public void execute() {
	}
}