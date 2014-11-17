package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器更新问答面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateQuestionPannel extends GCMessage{
	
	/** 当前问题序号 */
	private int questionIndex;
	/** 问题总数 */
	private int totalQuestionNum;
	/** 问题题目 */
	private String question;
	/** 问题答案 */
	private com.hifun.soul.gameserver.activity.question.model.AnswerInfo[] answers;
	/** 当前可以使用加成次数 */
	private int usableBlessNum;
	/** 可用最大加成次数 */
	private int maxUsableBlessNum;
	/** 可购买祈福次数 */
	private int canBuyBlessNum;
	/** 一键答题是否开启 */
	private boolean onekeyAnswerOpen;
	/** 一键答题花费魔晶 */
	private int onekeyAnswerCost;

	public GCUpdateQuestionPannel (){
	}
	
	public GCUpdateQuestionPannel (
			int questionIndex,
			int totalQuestionNum,
			String question,
			com.hifun.soul.gameserver.activity.question.model.AnswerInfo[] answers,
			int usableBlessNum,
			int maxUsableBlessNum,
			int canBuyBlessNum,
			boolean onekeyAnswerOpen,
			int onekeyAnswerCost ){
			this.questionIndex = questionIndex;
			this.totalQuestionNum = totalQuestionNum;
			this.question = question;
			this.answers = answers;
			this.usableBlessNum = usableBlessNum;
			this.maxUsableBlessNum = maxUsableBlessNum;
			this.canBuyBlessNum = canBuyBlessNum;
			this.onekeyAnswerOpen = onekeyAnswerOpen;
			this.onekeyAnswerCost = onekeyAnswerCost;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		questionIndex = readInteger();
		totalQuestionNum = readInteger();
		question = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		answers = new com.hifun.soul.gameserver.activity.question.model.AnswerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.activity.question.model.AnswerInfo objanswers = new com.hifun.soul.gameserver.activity.question.model.AnswerInfo();
			answers[i] = objanswers;
					objanswers.setAnswerIndex(readInteger());
							objanswers.setContent(readString());
				}
		usableBlessNum = readInteger();
		maxUsableBlessNum = readInteger();
		canBuyBlessNum = readInteger();
		onekeyAnswerOpen = readBoolean();
		onekeyAnswerCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questionIndex);
		writeInteger(totalQuestionNum);
		writeString(question);
	writeShort(answers.length);
	for(int i=0; i<answers.length; i++){
	com.hifun.soul.gameserver.activity.question.model.AnswerInfo objanswers = answers[i];
				writeInteger(objanswers.getAnswerIndex());
				writeString(objanswers.getContent());
	}
		writeInteger(usableBlessNum);
		writeInteger(maxUsableBlessNum);
		writeInteger(canBuyBlessNum);
		writeBoolean(onekeyAnswerOpen);
		writeInteger(onekeyAnswerCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_QUESTION_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_QUESTION_PANNEL";
	}

	public int getQuestionIndex(){
		return questionIndex;
	}
		
	public void setQuestionIndex(int questionIndex){
		this.questionIndex = questionIndex;
	}

	public int getTotalQuestionNum(){
		return totalQuestionNum;
	}
		
	public void setTotalQuestionNum(int totalQuestionNum){
		this.totalQuestionNum = totalQuestionNum;
	}

	public String getQuestion(){
		return question;
	}
		
	public void setQuestion(String question){
		this.question = question;
	}

	public com.hifun.soul.gameserver.activity.question.model.AnswerInfo[] getAnswers(){
		return answers;
	}

	public void setAnswers(com.hifun.soul.gameserver.activity.question.model.AnswerInfo[] answers){
		this.answers = answers;
	}	

	public int getUsableBlessNum(){
		return usableBlessNum;
	}
		
	public void setUsableBlessNum(int usableBlessNum){
		this.usableBlessNum = usableBlessNum;
	}

	public int getMaxUsableBlessNum(){
		return maxUsableBlessNum;
	}
		
	public void setMaxUsableBlessNum(int maxUsableBlessNum){
		this.maxUsableBlessNum = maxUsableBlessNum;
	}

	public int getCanBuyBlessNum(){
		return canBuyBlessNum;
	}
		
	public void setCanBuyBlessNum(int canBuyBlessNum){
		this.canBuyBlessNum = canBuyBlessNum;
	}

	public boolean getOnekeyAnswerOpen(){
		return onekeyAnswerOpen;
	}
		
	public void setOnekeyAnswerOpen(boolean onekeyAnswerOpen){
		this.onekeyAnswerOpen = onekeyAnswerOpen;
	}

	public int getOnekeyAnswerCost(){
		return onekeyAnswerCost;
	}
		
	public void setOnekeyAnswerCost(int onekeyAnswerCost){
		this.onekeyAnswerCost = onekeyAnswerCost;
	}
}