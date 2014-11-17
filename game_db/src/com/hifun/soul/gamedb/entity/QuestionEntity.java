package com.hifun.soul.gamedb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name="t_question")
public class QuestionEntity extends BaseCommonEntity{

	@Id
	@Column
	private int id;
	/** 问题id */
	@Column
	private long questionId;
	/** 问题类型 */
	@Column
	private int questionType;
	/** 内容 */
	@Column
	private String content;
	/** 提问人uuid*/
	@Column
	private long askerId;
	/** 名称 */
	@Column
	private String askerName;
	/** 时间 */
	@Column
	private Date askTime;	
	
	@Override
	public Integer getId() {		
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Integer)id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getAskTime() {
		return askTime;
	}

	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}

}
