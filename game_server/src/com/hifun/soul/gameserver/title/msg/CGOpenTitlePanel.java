package com.hifun.soul.gameserver.title.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开军衔面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenTitlePanel extends CGMessage{
	
	
	public CGOpenTitlePanel (){
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
		return MessageType.CG_OPEN_TITLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_TITLE_PANEL";
	}

	@Override
	public void execute() {
	}
}