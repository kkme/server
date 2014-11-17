package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 展示解救标签页
 * 
 * @author SevenSoul
 */
@Component
public class CGShowRescueTab extends CGMessage{
	
	
	public CGShowRescueTab (){
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
		return MessageType.CG_SHOW_RESCUE_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_RESCUE_TAB";
	}

	@Override
	public void execute() {
	}
}