package com.hifun.soul.gameserver.crystalexchange.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 魔晶兑换
 * 
 * @author SevenSoul
 */
@Component
public class CGCrystalExchange extends CGMessage{
	
	
	public CGCrystalExchange (){
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
		return MessageType.CG_CRYSTAL_EXCHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CRYSTAL_EXCHANGE";
	}

	@Override
	public void execute() {
	}
}