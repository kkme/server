package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开充值活动面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenRechargeActivityPanel extends CGMessage{
	
	
	public CGOpenRechargeActivityPanel (){
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
		return MessageType.CG_OPEN_RECHARGE_ACTIVITY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_RECHARGE_ACTIVITY_PANEL";
	}

	@Override
	public void execute() {
	}
}