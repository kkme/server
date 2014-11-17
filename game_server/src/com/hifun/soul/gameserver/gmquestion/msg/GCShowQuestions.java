package com.hifun.soul.gameserver.gmquestion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示提问信息
 *
 * @author SevenSoul
 */
@Component
public class GCShowQuestions extends GCMessage{
	
	/** 问答列表 */
	private com.hifun.soul.gameserver.gmquestion.GmQuestionInfo[] questions;

	public GCShowQuestions (){
	}
	
	public GCShowQuestions (
			com.hifun.soul.gameserver.gmquestion.GmQuestionInfo[] questions ){
			this.questions = questions;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		questions = new com.hifun.soul.gameserver.gmquestion.GmQuestionInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.gmquestion.GmQuestionInfo objquestions = new com.hifun.soul.gameserver.gmquestion.GmQuestionInfo();
			questions[i] = objquestions;
					objquestions.setQuestionId(readLong());
							objquestions.setName(readString());
							objquestions.setContent(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(questions.length);
	for(int i=0; i<questions.length; i++){
	com.hifun.soul.gameserver.gmquestion.GmQuestionInfo objquestions = questions[i];
				writeLong(objquestions.getQuestionId());
				writeString(objquestions.getName());
				writeString(objquestions.getContent());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_QUESTIONS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_QUESTIONS";
	}

	public com.hifun.soul.gameserver.gmquestion.GmQuestionInfo[] getQuestions(){
		return questions;
	}

	public void setQuestions(com.hifun.soul.gameserver.gmquestion.GmQuestionInfo[] questions){
		this.questions = questions;
	}	
}