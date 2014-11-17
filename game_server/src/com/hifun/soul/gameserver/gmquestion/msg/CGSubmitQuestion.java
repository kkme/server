package com.hifun.soul.gameserver.gmquestion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 提交问题
 * 
 * @author SevenSoul
 */
@Component
public class CGSubmitQuestion extends CGMessage{
	
	/** 问题类型 */
	private int questionType;
	/** 问题内容 */
	private String content;
	
	public CGSubmitQuestion (){
	}
	
	public CGSubmitQuestion (
			int questionType,
			String content ){
			this.questionType = questionType;
			this.content = content;
	}
	
	@Override
	protected boolean readImpl() {
		questionType = readInteger();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questionType);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SUBMIT_QUESTION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SUBMIT_QUESTION";
	}

	public int getQuestionType(){
		return questionType;
	}
		
	public void setQuestionType(int questionType){
		this.questionType = questionType;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	@Override
	public void execute() {
	}
}