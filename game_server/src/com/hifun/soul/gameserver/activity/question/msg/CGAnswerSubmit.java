package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 提交问题答案
 * 
 * @author SevenSoul
 */
@Component
public class CGAnswerSubmit extends CGMessage{
	
	/** 答案序号 */
	private int answerIndex;
	/** 是否使用祈福 */
	private boolean isBless;
	/** 是否确认花费金币 */
	private boolean isConfirm;
	
	public CGAnswerSubmit (){
	}
	
	public CGAnswerSubmit (
			int answerIndex,
			boolean isBless,
			boolean isConfirm ){
			this.answerIndex = answerIndex;
			this.isBless = isBless;
			this.isConfirm = isConfirm;
	}
	
	@Override
	protected boolean readImpl() {
		answerIndex = readInteger();
		isBless = readBoolean();
		isConfirm = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(answerIndex);
		writeBoolean(isBless);
		writeBoolean(isConfirm);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ANSWER_SUBMIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ANSWER_SUBMIT";
	}

	public int getAnswerIndex(){
		return answerIndex;
	}
		
	public void setAnswerIndex(int answerIndex){
		this.answerIndex = answerIndex;
	}

	public boolean getIsBless(){
		return isBless;
	}
		
	public void setIsBless(boolean isBless){
		this.isBless = isBless;
	}

	public boolean getIsConfirm(){
		return isConfirm;
	}
		
	public void setIsConfirm(boolean isConfirm){
		this.isConfirm = isConfirm;
	}

	@Override
	public void execute() {
	}
}