package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知战斗投降 
 *
 * @author SevenSoul
 */
@Component
public class GCBattleGiveUp extends GCMessage{
	
	/** 投降者GUID */
	private long losserGuid;

	public GCBattleGiveUp (){
	}
	
	public GCBattleGiveUp (
			long losserGuid ){
			this.losserGuid = losserGuid;
	}

	@Override
	protected boolean readImpl() {
		losserGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(losserGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_GIVE_UP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_GIVE_UP";
	}

	public long getLosserGuid(){
		return losserGuid;
	}
		
	public void setLosserGuid(long losserGuid){
		this.losserGuid = losserGuid;
	}
}