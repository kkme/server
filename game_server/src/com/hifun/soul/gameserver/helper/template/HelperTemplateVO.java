package com.hifun.soul.gameserver.helper.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 小助手模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HelperTemplateVO extends TemplateObject {

	/** 开关 */
	@ExcelCellBinding(offset = 1)
	protected int open;

	/** 名称多语言id */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 名称 */
	@ExcelCellBinding(offset = 3)
	protected String name;


	public int getOpen() {
		return this.open;
	}

	public void setOpen(int open) {
		if (open < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[开关]open的值不得小于0");
		}
		this.open = open;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[名称多语言id]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[名称]name不可以为空");
		}
		this.name = name;
	}
	

	@Override
	public String toString() {
		return "HelperTemplateVO[open=" + open + ",nameLangId=" + nameLangId + ",name=" + name + ",]";

	}
}