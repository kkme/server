package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知等待对方准备好
 *
 * @author SevenSoul
 */
@Component
public class GCBattleWaitingOtherUnitReady extends GCMessage{
	

	public GCBattleWaitingOtherUnitReady (){
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
		return MessageType.GC_BATTLE_WAITING_OTHER_UNIT_READY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_WAITING_OTHER_UNIT_READY";
	}
}