package com.hifun.soul.gameserver.faction.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求随机加入阵营
 * 
 * @author SevenSoul
 */
@Component
public class CGRandomSelectFaction extends CGMessage{
	
	
	public CGRandomSelectFaction (){
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
		return MessageType.CG_RANDOM_SELECT_FACTION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RANDOM_SELECT_FACTION";
	}

	@Override
	public void execute() {
	}
}