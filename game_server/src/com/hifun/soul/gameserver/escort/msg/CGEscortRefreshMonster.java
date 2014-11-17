package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求刷新押运怪物 
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortRefreshMonster extends CGMessage{
	
	
	public CGEscortRefreshMonster (){
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
		return MessageType.CG_ESCORT_REFRESH_MONSTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_REFRESH_MONSTER";
	}

	@Override
	public void execute() {
	}
}