package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开首充面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenFirstRechargePanel extends CGMessage{
	
	
	public CGOpenFirstRechargePanel (){
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
		return MessageType.CG_OPEN_FIRST_RECHARGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_FIRST_RECHARGE_PANEL";
	}

	@Override
	public void execute() {
	}
}