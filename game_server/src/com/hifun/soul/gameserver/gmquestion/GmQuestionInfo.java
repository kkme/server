package com.hifun.soul.gameserver.gmquestion;

public class GmQuestionInfo {
	/** 问题id */
	private long questionId;
	/** 提问人名称 */
	private String name;
	/** 内容 */
	private String content;
	
	
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
