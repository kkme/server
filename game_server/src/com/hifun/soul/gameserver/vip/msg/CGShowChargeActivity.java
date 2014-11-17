package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示充值活动信息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowChargeActivity extends CGMessage{
	
	
	public CGShowChargeActivity (){
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
		return MessageType.CG_SHOW_CHARGE_ACTIVITY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_CHARGE_ACTIVITY";
	}

	@Override
	public void execute() {
	}
}