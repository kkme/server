package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 星运介绍
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeIntroduce extends CGMessage{
	
	
	public CGHoroscopeIntroduce (){
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
		return MessageType.CG_HOROSCOPE_INTRODUCE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_INTRODUCE";
	}

	@Override
	public void execute() {
	}
}