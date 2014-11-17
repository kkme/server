package com.hifun.soul.gameserver.foster.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 保存当前的数值
 * 
 * @author SevenSoul
 */
@Component
public class CGSaveFoster extends CGMessage{
	
	
	public CGSaveFoster (){
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
		return MessageType.CG_SAVE_FOSTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SAVE_FOSTER";
	}

	@Override
	public void execute() {
	}
}