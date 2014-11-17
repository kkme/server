package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开单笔充值标签
 * 
 * @author SevenSoul
 */
@Component
public class CGShowSingleRechargeTab extends CGMessage{
	
	
	public CGShowSingleRechargeTab (){
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
		return MessageType.CG_SHOW_SINGLE_RECHARGE_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_SINGLE_RECHARGE_TAB";
	}

	@Override
	public void execute() {
	}
}