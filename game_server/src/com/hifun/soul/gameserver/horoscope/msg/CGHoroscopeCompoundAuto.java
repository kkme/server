package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 一键星运合成
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeCompoundAuto extends CGMessage{
	
	
	public CGHoroscopeCompoundAuto (){
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
		return MessageType.CG_HOROSCOPE_COMPOUND_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_COMPOUND_AUTO";
	}

	@Override
	public void execute() {
	}
}