package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开竞技场面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenArenaPanel extends CGMessage{
	
	
	public CGOpenArenaPanel (){
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
		return MessageType.CG_OPEN_ARENA_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_ARENA_PANEL";
	}

	@Override
	public void execute() {
	}
}