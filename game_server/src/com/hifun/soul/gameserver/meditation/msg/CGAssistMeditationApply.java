package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 申请好友协助
 * 
 * @author SevenSoul
 */
@Component
public class CGAssistMeditationApply extends CGMessage{
	
	
	public CGAssistMeditationApply (){
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
		return MessageType.CG_ASSIST_MEDITATION_APPLY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ASSIST_MEDITATION_APPLY";
	}

	@Override
	public void execute() {
	}
}