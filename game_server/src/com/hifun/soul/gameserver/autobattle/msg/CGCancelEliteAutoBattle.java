package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 精英副本取消扫荡
 * 
 * @author SevenSoul
 */
@Component
public class CGCancelEliteAutoBattle extends CGMessage{
	
	
	public CGCancelEliteAutoBattle (){
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
		return MessageType.CG_CANCEL_ELITE_AUTO_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CANCEL_ELITE_AUTO_BATTLE";
	}

	@Override
	public void execute() {
	}
}