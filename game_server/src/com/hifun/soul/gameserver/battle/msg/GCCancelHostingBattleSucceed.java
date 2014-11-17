package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知取消托管成功
 *
 * @author SevenSoul
 */
@Component
public class GCCancelHostingBattleSucceed extends GCMessage{
	
	/** 被托管者GUID */
	private long beHostedGuid;

	public GCCancelHostingBattleSucceed (){
	}
	
	public GCCancelHostingBattleSucceed (
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
		return MessageType.GC_CANCEL_HOSTING_BATTLE_SUCCEED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CANCEL_HOSTING_BATTLE_SUCCEED";
	}

	public long getBeHostedGuid(){
		return beHostedGuid;
	}
		
	public void setBeHostedGuid(long beHostedGuid){
		this.beHostedGuid = beHostedGuid;
	}
}