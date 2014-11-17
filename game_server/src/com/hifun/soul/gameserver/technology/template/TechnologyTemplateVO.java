package com.hifun.soul.gameserver.technology.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 科技模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TechnologyTemplateVO extends TemplateObject {

	/**  科技名称多语言 */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  科技名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  属性多语言 */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  属性名称 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  图标id */
	@ExcelCellBinding(offset = 5)
	protected int icon;

	/**  科技开放等级 */
	@ExcelCellBinding(offset = 6)
	protected int openLevel;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 科技名称多语言]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 科技名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 属性多语言]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 属性名称]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 图标id]icon的值不得小于0");
		}
		this.icon = icon;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 科技开放等级]openLevel的值不得小于0");
		}
		this.openLevel = openLevel;
	}
	

	@Override
	public String toString() {
		return "TechnologyTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",icon=" + icon + ",openLevel=" + openLevel + ",]";

	}
}