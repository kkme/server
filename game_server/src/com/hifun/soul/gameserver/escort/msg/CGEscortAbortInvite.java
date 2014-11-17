package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求撤销邀请 
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortAbortInvite extends CGMessage{
	
	
	public CGEscortAbortInvite (){
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
		return MessageType.CG_ESCORT_ABORT_INVITE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_ABORT_INVITE";
	}

	@Override
	public void execute() {
	}
}