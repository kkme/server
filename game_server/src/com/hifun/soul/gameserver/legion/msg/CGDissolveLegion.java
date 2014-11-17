package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求解散军团 
 * 
 * @author SevenSoul
 */
@Component
public class CGDissolveLegion extends CGMessage{
	
	
	public CGDissolveLegion (){
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
		return MessageType.CG_DISSOLVE_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DISSOLVE_LEGION";
	}

	@Override
	public void execute() {
	}
}