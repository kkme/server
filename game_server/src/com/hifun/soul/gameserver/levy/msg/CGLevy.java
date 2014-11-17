package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端进行征收操作
 * 
 * @author SevenSoul
 */
@Component
public class CGLevy extends CGMessage{
	
	
	public CGLevy (){
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
		return MessageType.CG_LEVY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEVY";
	}

	@Override
	public void execute() {
	}
}