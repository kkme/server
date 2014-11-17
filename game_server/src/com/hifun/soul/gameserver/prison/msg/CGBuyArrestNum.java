package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 购买抓捕次数
 * 
 * @author SevenSoul
 */
@Component
public class CGBuyArrestNum extends CGMessage{
	
	
	public CGBuyArrestNum (){
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
		return MessageType.CG_BUY_ARREST_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_ARREST_NUM";
	}

	@Override
	public void execute() {
	}
}