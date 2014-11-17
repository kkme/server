package com.hifun.soul.gameserver.mars.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 战神之巅战场级别模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MarsFieldTemplateVO extends TemplateObject {

	/** 等级下限 */
	@ExcelCellBinding(offset = 1)
	protected int startLevel;

	/** 等级上限 */
	@ExcelCellBinding(offset = 2)
	protected int endLevel;

	/** 多语言 */
	@ExcelCellBinding(offset = 3)
	protected int language;

	/** 战场名称 */
	@ExcelCellBinding(offset = 4)
	protected String fieldName;


	public int getStartLevel() {
		return this.startLevel;
	}

	public void setStartLevel(int startLevel) {
		if (startLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[等级下限]startLevel不可以为0");
		}
		this.startLevel = startLevel;
	}
	
	public int getEndLevel() {
		return this.endLevel;
	}

	public void setEndLevel(int endLevel) {
		if (endLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[等级上限]endLevel不可以为0");
		}
		this.endLevel = endLevel;
	}
	
	public int getLanguage() {
		return this.language;
	}

	public void setLanguage(int language) {
		if (language == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言]language不可以为0");
		}
		this.language = language;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		if (StringUtils.isEmpty(fieldName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[战场名称]fieldName不可以为空");
		}
		this.fieldName = fieldName;
	}
	

	@Override
	public String toString() {
		return "MarsFieldTemplateVO[startLevel=" + startLevel + ",endLevel=" + endLevel + ",language=" + language + ",fieldName=" + fieldName + ",]";

	}
}