package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 参战
 * 
 * @author SevenSoul
 */
@Component
public class CGReadyForMatchBattle extends CGMessage{
	
	
	public CGReadyForMatchBattle (){
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
		return MessageType.CG_READY_FOR_MATCH_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_READY_FOR_MATCH_BATTLE";
	}

	@Override
	public void execute() {
	}
}