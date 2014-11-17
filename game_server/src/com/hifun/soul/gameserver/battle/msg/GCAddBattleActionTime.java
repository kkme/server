package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 通知客户端额外回合
 *
 * @author SevenSoul
 */
@Component
public class GCAddBattleActionTime extends GCMessage{
	
	/** GUID */
	private long unitGuid;

	public GCAddBattleActionTime (){
	}
	
	public GCAddBattleActionTime (
			long unitGuid ){
			this.unitGuid = unitGuid;
	}

	@Override
	protected boolean readImpl() {
		unitGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(unitGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ADD_BATTLE_ACTION_TIME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_BATTLE_ACTION_TIME";
	}

	public long getUnitGuid(){
		return unitGuid;
	}
		
	public void setUnitGuid(long unitGuid){
		this.unitGuid = unitGuid;
	}
}