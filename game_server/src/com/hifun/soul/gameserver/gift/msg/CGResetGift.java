package com.hifun.soul.gameserver.gift.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求重置天赋
 * 
 * @author SevenSoul
 */
@Component
public class CGResetGift extends CGMessage{
	
	
	public CGResetGift (){
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
		return MessageType.CG_RESET_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RESET_GIFT";
	}

	@Override
	public void execute() {
	}
}