package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开勇士之路面板请求
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenWarriorWayPanel extends CGMessage{
	
	
	public CGOpenWarriorWayPanel (){
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
		return MessageType.CG_OPEN_WARRIOR_WAY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_WARRIOR_WAY_PANEL";
	}

	@Override
	public void execute() {
	}
}