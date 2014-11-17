package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 通知等待对方接受战斗邀请
 *
 * @author SevenSoul
 */
@Component
public class GCWaittingBattleResponse extends GCMessage{
	

	public GCWaittingBattleResponse (){
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
		return MessageType.GC_WAITTING_BATTLE_RESPONSE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WAITTING_BATTLE_RESPONSE";
	}
}