package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 奴隶展示求救标签页
 * 
 * @author SevenSoul
 */
@Component
public class CGSlaveShowSosTab extends CGMessage{
	
	
	public CGSlaveShowSosTab (){
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
		return MessageType.CG_SLAVE_SHOW_SOS_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLAVE_SHOW_SOS_TAB";
	}

	@Override
	public void execute() {
	}
}