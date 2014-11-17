package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 添加竞技场战斗次数
 * 
 * @author SevenSoul
 */
@Component
public class CGAddArenaBattleTime extends CGMessage{
	
	
	public CGAddArenaBattleTime (){
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
		return MessageType.CG_ADD_ARENA_BATTLE_TIME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ADD_ARENA_BATTLE_TIME";
	}

	@Override
	public void execute() {
	}
}