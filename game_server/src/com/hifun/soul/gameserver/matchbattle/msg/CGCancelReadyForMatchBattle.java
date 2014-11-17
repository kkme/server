package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 取消参战
 * 
 * @author SevenSoul
 */
@Component
public class CGCancelReadyForMatchBattle extends CGMessage{
	
	
	public CGCancelReadyForMatchBattle (){
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
		return MessageType.CG_CANCEL_READY_FOR_MATCH_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CANCEL_READY_FOR_MATCH_BATTLE";
	}

	@Override
	public void execute() {
	}
}