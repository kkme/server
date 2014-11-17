package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求刷新对手
 * 
 * @author SevenSoul
 */
@Component
public class CGRefreshOpponent extends CGMessage{
	
	
	public CGRefreshOpponent (){
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
		return MessageType.CG_REFRESH_OPPONENT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_OPPONENT";
	}

	@Override
	public void execute() {
	}
}