package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端发送挑战请求
 * 
 * @author SevenSoul
 */
@Component
public class CGStartWarriorBattleRequest extends CGMessage{
	
	
	public CGStartWarriorBattleRequest (){
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
		return MessageType.CG_START_WARRIOR_BATTLE_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_WARRIOR_BATTLE_REQUEST";
	}

	@Override
	public void execute() {
	}
}