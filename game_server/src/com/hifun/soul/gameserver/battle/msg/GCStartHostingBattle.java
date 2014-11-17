package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知托管战斗开始
 *
 * @author SevenSoul
 */
@Component
public class GCStartHostingBattle extends GCMessage{
	
	/** 被托管者GUID */
	private long beHostedGuid;

	public GCStartHostingBattle (){
	}
	
	public GCStartHostingBattle (
			long beHostedGuid ){
			this.beHostedGuid = beHostedGuid;
	}

	@Override
	protected boolean readImpl() {
		beHostedGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(beHostedGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_HOSTING_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_HOSTING_BATTLE";
	}

	public long getBeHostedGuid(){
		return beHostedGuid;
	}
		
	public void setBeHostedGuid(long beHostedGuid){
		this.beHostedGuid = beHostedGuid;
	}
}