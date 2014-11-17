package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 为了新手引导加的一个没用的消息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowCharacterPanel extends CGMessage{
	
	
	public CGShowCharacterPanel (){
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
		return MessageType.CG_SHOW_CHARACTER_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_CHARACTER_PANEL";
	}

	@Override
	public void execute() {
	}
}