package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求必胜出拳
 * 
 * @author SevenSoul
 */
@Component
public class CGSucceedFinger extends CGMessage{
	
	
	public CGSucceedFinger (){
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
		return MessageType.CG_SUCCEED_FINGER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SUCCEED_FINGER";
	}

	@Override
	public void execute() {
	}
}