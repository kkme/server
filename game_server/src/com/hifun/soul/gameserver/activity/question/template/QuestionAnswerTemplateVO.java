package com.hifun.soul.gameserver.activity.question.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 问答模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class QuestionAnswerTemplateVO extends TemplateObject {

	/** 问题题目 */
	@ExcelCellBinding(offset = 1)
	protected String question;

	/**  问题答案 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.activity.question.template.Answer.class, collectionNumber = "2,3,4,5,6;7,8,9,10,11;12,13,14,15,16")
	protected List<com.hifun.soul.gameserver.activity.question.template.Answer> answers;

	/** 正确答案（从1开始） */
	@ExcelCellBinding(offset = 17)
	protected int rightAnswerIndex;


	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		if (StringUtils.isEmpty(question)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[问题题目]question不可以为空");
		}
		this.question = question;
	}
	
	public List<com.hifun.soul.gameserver.activity.question.template.Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<com.hifun.soul.gameserver.activity.question.template.Answer> answers) {
		if (answers == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 问题答案]answers不可以为空");
		}	
		this.answers = answers;
	}
	
	public int getRightAnswerIndex() {
		return this.rightAnswerIndex;
	}

	public void setRightAnswerIndex(int rightAnswerIndex) {
		if (rightAnswerIndex == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[正确答案（从1开始）]rightAnswerIndex不可以为0");
		}
		this.rightAnswerIndex = rightAnswerIndex;
	}
	

	@Override
	public String toString() {
		return "QuestionAnswerTemplateVO[question=" + question + ",answers=" + answers + ",rightAnswerIndex=" + rightAnswerIndex + ",]";

	}
}