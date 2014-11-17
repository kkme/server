package com.hifun.soul.gameserver.activity.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 活动显示类型模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ActivityShowTypeTemplateVO extends TemplateObject {

	/** 类型名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 类型名称多语言id */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 目标 */
	@ExcelCellBinding(offset = 3)
	protected int iconId;

	/** 可视等级 */
	@ExcelCellBinding(offset = 4)
	protected int visibleLevel;

	/** 开启等级 */
	@ExcelCellBinding(offset = 5)
	protected int openLevel;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[类型名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[类型名称多语言id]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[目标]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	
	public int getVisibleLevel() {
		return this.visibleLevel;
	}

	public void setVisibleLevel(int visibleLevel) {
		if (visibleLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[可视等级]visibleLevel不可以为0");
		}
		this.visibleLevel = visibleLevel;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[开启等级]openLevel不可以为0");
		}
		this.openLevel = openLevel;
	}
	

	@Override
	public String toString() {
		return "ActivityShowTypeTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",iconId=" + iconId + ",visibleLevel=" + visibleLevel + ",openLevel=" + openLevel + ",]";

	}
}