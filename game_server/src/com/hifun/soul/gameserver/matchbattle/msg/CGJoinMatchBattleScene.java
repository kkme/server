package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 进入匹配战场景
 * 
 * @author SevenSoul
 */
@Component
public class CGJoinMatchBattleScene extends CGMessage{
	
	
	public CGJoinMatchBattleScene (){
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
		return MessageType.CG_JOIN_MATCH_BATTLE_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JOIN_MATCH_BATTLE_SCENE";
	}

	@Override
	public void execute() {
	}
}