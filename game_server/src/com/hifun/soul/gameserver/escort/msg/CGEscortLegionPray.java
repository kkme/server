package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求军团祈福 
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortLegionPray extends CGMessage{
	
	
	public CGEscortLegionPray (){
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
		return MessageType.CG_ESCORT_LEGION_PRAY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_LEGION_PRAY";
	}

	@Override
	public void execute() {
	}
}