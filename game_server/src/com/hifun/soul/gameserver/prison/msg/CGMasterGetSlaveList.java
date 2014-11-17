package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 主人获取奴隶列表
 * 
 * @author SevenSoul
 */
@Component
public class CGMasterGetSlaveList extends CGMessage{
	
	
	public CGMasterGetSlaveList (){
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
		return MessageType.CG_MASTER_GET_SLAVE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MASTER_GET_SLAVE_LIST";
	}

	@Override
	public void execute() {
	}
}