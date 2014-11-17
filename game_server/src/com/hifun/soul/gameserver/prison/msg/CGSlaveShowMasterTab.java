package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 奴隶展示主人标签页 
 * 
 * @author SevenSoul
 */
@Component
public class CGSlaveShowMasterTab extends CGMessage{
	
	
	public CGSlaveShowMasterTab (){
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
		return MessageType.CG_SLAVE_SHOW_MASTER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLAVE_SHOW_MASTER_TAB";
	}

	@Override
	public void execute() {
	}
}