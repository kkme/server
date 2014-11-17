package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求提取威望(以前是声望)
 * 
 * @author SevenSoul
 */
@Component
public class CGExtractAbattoirHonor extends CGMessage{
	
	
	public CGExtractAbattoirHonor (){
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
		return MessageType.CG_EXTRACT_ABATTOIR_HONOR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EXTRACT_ABATTOIR_HONOR";
	}

	@Override
	public void execute() {
	}
}