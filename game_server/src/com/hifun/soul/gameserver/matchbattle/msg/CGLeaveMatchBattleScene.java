package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 离开匹配战场景
 * 
 * @author SevenSoul
 */
@Component
public class CGLeaveMatchBattleScene extends CGMessage{
	
	
	public CGLeaveMatchBattleScene (){
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
		return MessageType.CG_LEAVE_MATCH_BATTLE_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEAVE_MATCH_BATTLE_SCENE";
	}

	@Override
	public void execute() {
	}
}