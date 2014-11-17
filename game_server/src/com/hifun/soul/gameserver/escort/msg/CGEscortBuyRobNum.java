package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求购买拦截次数
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortBuyRobNum extends CGMessage{
	
	
	public CGEscortBuyRobNum (){
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
		return MessageType.CG_ESCORT_BUY_ROB_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_BUY_ROB_NUM";
	}

	@Override
	public void execute() {
	}
}