package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 一键答题
 * 
 * @author SevenSoul
 */
@Component
public class CGOnekeyAnswerQuestion extends CGMessage{
	
	
	public CGOnekeyAnswerQuestion (){
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
		return MessageType.CG_ONEKEY_ANSWER_QUESTION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ONEKEY_ANSWER_QUESTION";
	}

	@Override
	public void execute() {
	}
}