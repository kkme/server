package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 重置属性点
 * 
 * @author SevenSoul
 */
@Component
public class CGResetPropertyPoint extends CGMessage{
	
	
	public CGResetPropertyPoint (){
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
		return MessageType.CG_RESET_PROPERTY_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RESET_PROPERTY_POINT";
	}

	@Override
	public void execute() {
	}
}