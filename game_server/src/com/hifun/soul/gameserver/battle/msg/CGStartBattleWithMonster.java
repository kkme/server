package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求和地图怪开始战斗
 * 
 * @author SevenSoul
 */
@Component
public class CGStartBattleWithMonster extends CGMessage{
	
	/** 怪物模版ID */
	private int monsterId;
	
	public CGStartBattleWithMonster (){
	}
	
	public CGStartBattleWithMonster (
			int monsterId ){
			this.monsterId = monsterId;
	}
	
	@Override
	protected boolean readImpl() {
		monsterId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(monsterId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_START_BATTLE_WITH_MONSTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_BATTLE_WITH_MONSTER";
	}

	public int getMonsterId(){
		return monsterId;
	}
		
	public void setMonsterId(int monsterId){
		this.monsterId = monsterId;
	}

	@Override
	public void execute() {
	}
}