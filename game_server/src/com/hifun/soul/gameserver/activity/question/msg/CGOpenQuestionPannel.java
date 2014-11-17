package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求打开问答面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenQuestionPannel extends CGMessage{
	
	
	public CGOpenQuestionPannel (){
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
		return MessageType.CG_OPEN_QUESTION_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_QUESTION_PANNEL";
	}

	@Override
	public void execute() {
	}
}