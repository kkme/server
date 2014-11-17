package com.hifun.soul.gameserver.training.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 普通训练类型模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TrainingTypeTemplateVO extends TemplateObject {

	/** 训练模式名称 */
	@ExcelCellBinding(offset = 1)
	protected String trainingTypeName;

	/** 多语言名称 */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 训练时长（分钟） */
	@ExcelCellBinding(offset = 3)
	protected int trainingTime;

	/** 收益比率 */
	@ExcelCellBinding(offset = 4)
	protected int expRate;


	public String getTrainingTypeName() {
		return this.trainingTypeName;
	}

	public void setTrainingTypeName(String trainingTypeName) {
		if (StringUtils.isEmpty(trainingTypeName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[训练模式名称]trainingTypeName不可以为空");
		}
		this.trainingTypeName = trainingTypeName;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[多语言名称]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getTrainingTime() {
		return this.trainingTime;
	}

	public void setTrainingTime(int trainingTime) {
		if (trainingTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[训练时长（分钟）]trainingTime不可以为0");
		}
		this.trainingTime = trainingTime;
	}
	
	public int getExpRate() {
		return this.expRate;
	}

	public void setExpRate(int expRate) {
		if (expRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[收益比率]expRate不可以为0");
		}
		this.expRate = expRate;
	}
	

	@Override
	public String toString() {
		return "TrainingTypeTemplateVO[trainingTypeName=" + trainingTypeName + ",nameLangId=" + nameLangId + ",trainingTime=" + trainingTime + ",expRate=" + expRate + ",]";

	}
}