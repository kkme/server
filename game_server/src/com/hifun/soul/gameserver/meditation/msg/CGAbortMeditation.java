package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 中止冥想
 * 
 * @author SevenSoul
 */
@Component
public class CGAbortMeditation extends CGMessage{
	
	
	public CGAbortMeditation (){
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
		return MessageType.CG_ABORT_MEDITATION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ABORT_MEDITATION";
	}

	@Override
	public void execute() {
	}
}