package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知新的战斗回合
 *
 * @author SevenSoul
 */
@Component
public class GCBattleNewRound extends GCMessage{
	
	/** 当前的回合数 */
	private int currentRound;

	public GCBattleNewRound (){
	}
	
	public GCBattleNewRound (
			int currentRound ){
			this.currentRound = currentRound;
	}

	@Override
	protected boolean readImpl() {
		currentRound = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentRound);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_NEW_ROUND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_NEW_ROUND";
	}

	public int getCurrentRound(){
		return currentRound;
	}
		
	public void setCurrentRound(int currentRound){
		this.currentRound = currentRound;
	}
}