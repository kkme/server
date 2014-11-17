package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 展示抓捕页面
 * 
 * @author SevenSoul
 */
@Component
public class CGShowArrestTab extends CGMessage{
	
	
	public CGShowArrestTab (){
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
		return MessageType.CG_SHOW_ARREST_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_ARREST_TAB";
	}

	@Override
	public void execute() {
	}
}