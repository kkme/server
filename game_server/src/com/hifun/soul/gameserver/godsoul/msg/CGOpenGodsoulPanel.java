package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开神魄面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenGodsoulPanel extends CGMessage{
	
	
	public CGOpenGodsoulPanel (){
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
		return MessageType.CG_OPEN_GODSOUL_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_GODSOUL_PANEL";
	}

	@Override
	public void execute() {
	}
}