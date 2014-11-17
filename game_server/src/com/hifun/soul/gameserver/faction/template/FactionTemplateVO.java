package com.hifun.soul.gameserver.faction.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 阵营模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FactionTemplateVO extends TemplateObject {

	/** 阵营名称 */
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
	protected int iconId;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[阵营名称]name不可以为空");
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
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[图标id]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	

	@Override
	public String toString() {
		return "FactionTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",iconId=" + iconId + ",]";

	}
}