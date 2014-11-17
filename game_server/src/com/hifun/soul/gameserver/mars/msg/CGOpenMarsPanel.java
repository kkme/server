package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开战神之巅面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenMarsPanel extends CGMessage{
	
	
	public CGOpenMarsPanel (){
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
		return MessageType.CG_OPEN_MARS_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_MARS_PANEL";
	}

	@Override
	public void execute() {
	}
}