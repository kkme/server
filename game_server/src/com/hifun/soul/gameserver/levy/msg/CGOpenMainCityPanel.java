package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开主城面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenMainCityPanel extends CGMessage{
	
	
	public CGOpenMainCityPanel (){
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
		return MessageType.CG_OPEN_MAIN_CITY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_MAIN_CITY_PANEL";
	}

	@Override
	public void execute() {
	}
}