package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开角斗场面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenAbattoirPanel extends CGMessage{
	
	
	public CGOpenAbattoirPanel (){
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
		return MessageType.CG_OPEN_ABATTOIR_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_ABATTOIR_PANEL";
	}

	@Override
	public void execute() {
	}
}