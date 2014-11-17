package com.hifun.soul.gameserver.functionhelper.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 功能助手分类模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FuncCategoryTemplateVO extends TemplateObject {

	/** 分类名称多语言id */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/** 分类名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[分类名称多语言id]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[分类名称]name不可以为空");
		}
		this.name = name;
	}
	

	@Override
	public String toString() {
		return "FuncCategoryTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",]";

	}
}