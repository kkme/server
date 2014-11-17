package com.hifun.soul.gameserver.functionhelper.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 功能助手模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FuncHelperTemplateVO extends TemplateObject {

	/** 名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 名称多语言id */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 描述 */
	@ExcelCellBinding(offset = 3)
	protected String desc;

	/** 描述多语言id */
	@ExcelCellBinding(offset = 4)
	protected int descLangId;

	/** 图标id */
	@ExcelCellBinding(offset = 5)
	protected int icon;

	/** 关联的功能id */
	@ExcelCellBinding(offset = 6)
	protected int functionId;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[名称]name不可以为空");
		}
		this.name = name;
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
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[描述多语言id]descLangId不可以为0");
		}
		this.descLangId = descLangId;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[图标id]icon的值不得小于0");
		}
		this.icon = icon;
	}
	
	public int getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(int functionId) {
		if (functionId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[关联的功能id]functionId的值不得小于0");
		}
		this.functionId = functionId;
	}
	

	@Override
	public String toString() {
		return "FuncHelperTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",icon=" + icon + ",functionId=" + functionId + ",]";

	}
}