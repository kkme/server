package com.hifun.soul.gameserver.activity.question.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 问答购买祈福模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class QuestionConsumeTemplateVO extends TemplateObject {

	/** 消耗魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int costNum;


	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		if (costNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[消耗魔晶数]costNum不可以为0");
		}
		this.costNum = costNum;
	}
	

	@Override
	public String toString() {
		return "QuestionConsumeTemplateVO[costNum=" + costNum + ",]";

	}
}