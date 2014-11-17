package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开押运面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenEscortPanel extends CGMessage{
	
	
	public CGOpenEscortPanel (){
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
		return MessageType.CG_OPEN_ESCORT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_ESCORT_PANEL";
	}

	@Override
	public void execute() {
	}
}