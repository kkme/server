package com.hifun.soul.gameserver.refine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 试炼地图模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RefineMapTemplateVO extends TemplateObject {

	/**  试炼地图名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/**  试炼地图名称多语言 */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/**  开放等级 */
	@ExcelCellBinding(offset = 3)
	protected int openLevel;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 试炼地图名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 试炼地图名称多语言]nameLangId不可以为0");
		}
		if (nameLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 试炼地图名称多语言]nameLangId的值不得小于1");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 开放等级]openLevel的值不得小于0");
		}
		this.openLevel = openLevel;
	}
	

	@Override
	public String toString() {
		return "RefineMapTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",openLevel=" + openLevel + ",]";

	}
}