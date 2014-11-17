package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 关卡取消扫荡
 * 
 * @author SevenSoul
 */
@Component
public class CGCancelStageAutoBattle extends CGMessage{
	
	
	public CGCancelStageAutoBattle (){
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
		return MessageType.CG_CANCEL_STAGE_AUTO_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CANCEL_STAGE_AUTO_BATTLE";
	}

	@Override
	public void execute() {
	}
}