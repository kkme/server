package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 开启矿坑战斗客户端回复
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenMineFieldBattleResponse extends CGMessage{
	
	/** 是否挑战 */
	private boolean challenge;
	
	public CGOpenMineFieldBattleResponse (){
	}
	
	public CGOpenMineFieldBattleResponse (
			boolean challenge ){
			this.challenge = challenge;
	}
	
	@Override
	protected boolean readImpl() {
		challenge = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(challenge);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_MINE_FIELD_BATTLE_RESPONSE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_MINE_FIELD_BATTLE_RESPONSE";
	}

	public boolean getChallenge(){
		return challenge;
	}
		
	public void setChallenge(boolean challenge){
		this.challenge = challenge;
	}

	@Override
	public void execute() {
	}
}