package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知玩家可以开始行动了
 *
 * @author SevenSoul
 */
@Component
public class GCStartBattleAction extends GCMessage{
	
	/** 行动的角色GUID */
	private long guid;

	public GCStartBattleAction (){
	}
	
	public GCStartBattleAction (
			long guid ){
			this.guid = guid;
	}

	@Override
	protected boolean readImpl() {
		guid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(guid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_BATTLE_ACTION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_BATTLE_ACTION";
	}

	public long getGuid(){
		return guid;
	}
		
	public void setGuid(long guid){
		this.guid = guid;
	}
}