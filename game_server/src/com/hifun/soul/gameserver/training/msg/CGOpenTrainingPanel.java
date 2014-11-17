package com.hifun.soul.gameserver.training.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开训练面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenTrainingPanel extends CGMessage{
	
	
	public CGOpenTrainingPanel (){
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
		return MessageType.CG_OPEN_TRAINING_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_TRAINING_PANEL";
	}

	@Override
	public void execute() {
	}
}