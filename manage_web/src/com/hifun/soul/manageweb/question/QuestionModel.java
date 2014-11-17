package com.hifun.soul.manageweb.question;

import java.util.Date;

import com.hifun.soul.proto.services.Services.QuestionInfo;

/**
 * 玩家反馈信息
 * @author magicstone
 *
 */
public class QuestionModel {
	private int id;
	private long questionId;
	private int questionType;
	private long askerId;
	private String askerName;
	private String content;
	private Date askTime;
	
	public QuestionModel(){}
	
	public QuestionModel(QuestionInfo questionInfo){
		this.id = questionInfo.getId();
		this.questionId = questionInfo.getQuestionId();
		this.questionType = questionInfo.getQuestionType();
		this.askerId = questionInfo.getAskerId();
		this.askerName = questionInfo.getAskerName();
		this.content = questionInfo.getContent();
		this.askTime = new Date(questionInfo.getAskTime());
	}
	
	public QuestionInfo convertToQuestionInfo(){
		QuestionInfo.Builder builder = QuestionInfo.newBuilder();
		builder.setId(id);
		builder.setQuestionId(questionId);
		builder.setQuestionType(questionType);
		builder.setAskerId(askerId);
		builder.setAskerName(askerName);
		builder.setContent(content);
		builder.setAskTime(askTime.getTime());
		return builder.build();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public long getAskerId() {
		return askerId;
	}
	public void setAskerId(long askerId) {
		this.askerId = askerId;
	}
	public String getAskerName() {
		return askerName;
	}
	public void setAskerName(String askerName) {
		this.askerName = askerName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAskTime() {
		return askTime;
	}
	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}
	
}
