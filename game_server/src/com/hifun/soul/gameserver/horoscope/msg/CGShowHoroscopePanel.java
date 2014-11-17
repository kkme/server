package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开星运面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowHoroscopePanel extends CGMessage{
	
	
	public CGShowHoroscopePanel (){
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
		return MessageType.CG_SHOW_HOROSCOPE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_HOROSCOPE_PANEL";
	}

	@Override
	public void execute() {
	}
}