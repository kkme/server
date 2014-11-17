package com.hifun.soul.gameserver.costnotify.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 消费提醒模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CostNotifyTemplateVO extends TemplateObject {

	/** 多语言 */
	@ExcelCellBinding(offset = 1)
	protected int langNameId;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 多语言 */
	@ExcelCellBinding(offset = 3)
	protected int langDescId;

	/** 名称 */
	@ExcelCellBinding(offset = 4)
	protected String desc;


	public int getLangNameId() {
		return this.langNameId;
	}

	public void setLangNameId(int langNameId) {
		if (langNameId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言]langNameId不可以为0");
		}
		this.langNameId = langNameId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getLangDescId() {
		return this.langDescId;
	}

	public void setLangDescId(int langDescId) {
		if (langDescId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言]langDescId不可以为0");
		}
		this.langDescId = langDescId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[名称]desc不可以为空");
		}
		this.desc = desc;
	}
	

	@Override
	public String toString() {
		return "CostNotifyTemplateVO[langNameId=" + langNameId + ",name=" + name + ",langDescId=" + langDescId + ",desc=" + desc + ",]";

	}
}