package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 奴隶反抗主人
 * 
 * @author SevenSoul
 */
@Component
public class CGSlaveRevolt extends CGMessage{
	
	
	public CGSlaveRevolt (){
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
		return MessageType.CG_SLAVE_REVOLT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLAVE_REVOLT";
	}

	@Override
	public void execute() {
	}
}