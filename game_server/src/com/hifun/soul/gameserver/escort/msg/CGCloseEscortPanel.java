package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求关闭押运面板
 * 
 * @author SevenSoul
 */
@Component
public class CGCloseEscortPanel extends CGMessage{
	
	
	public CGCloseEscortPanel (){
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
		return MessageType.CG_CLOSE_ESCORT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLOSE_ESCORT_PANEL";
	}

	@Override
	public void execute() {
	}
}