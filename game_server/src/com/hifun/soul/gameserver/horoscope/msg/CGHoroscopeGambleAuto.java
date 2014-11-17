package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 一键星运占星
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeGambleAuto extends CGMessage{
	
	
	public CGHoroscopeGambleAuto (){
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
		return MessageType.CG_HOROSCOPE_GAMBLE_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_GAMBLE_AUTO";
	}

	@Override
	public void execute() {
	}
}