package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知战斗单元死亡
 *
 * @author SevenSoul
 */
@Component
public class GCBattleUnitDead extends GCMessage{
	
	/** 死亡的战斗单元GUID */
	private long deadBattileUnitGuid;

	public GCBattleUnitDead (){
	}
	
	public GCBattleUnitDead (
			long deadBattileUnitGuid ){
			this.deadBattileUnitGuid = deadBattileUnitGuid;
	}

	@Override
	protected boolean readImpl() {
		deadBattileUnitGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(deadBattileUnitGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_UNIT_DEAD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_UNIT_DEAD";
	}

	public long getDeadBattileUnitGuid(){
		return deadBattileUnitGuid;
	}
		
	public void setDeadBattileUnitGuid(long deadBattileUnitGuid){
		this.deadBattileUnitGuid = deadBattileUnitGuid;
	}
}