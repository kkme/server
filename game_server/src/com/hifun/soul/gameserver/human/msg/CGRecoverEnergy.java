package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求恢复体力值
 * 
 * @author SevenSoul
 */
@Component
public class CGRecoverEnergy extends CGMessage{
	
	
	public CGRecoverEnergy (){
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
		return MessageType.CG_RECOVER_ENERGY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RECOVER_ENERGY";
	}

	@Override
	public void execute() {
	}
}