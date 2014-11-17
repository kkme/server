package com.hifun.soul.gameserver.gmquestion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示提问信息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowQuestions extends CGMessage{
	
	
	public CGShowQuestions (){
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
		return MessageType.CG_SHOW_QUESTIONS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_QUESTIONS";
	}

	@Override
	public void execute() {
	}
}