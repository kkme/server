package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求领取首充奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetFirstRechargeReward extends CGMessage{
	
	
	public CGGetFirstRechargeReward (){
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
		return MessageType.CG_GET_FIRST_RECHARGE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_FIRST_RECHARGE_REWARD";
	}

	@Override
	public void execute() {
	}
}