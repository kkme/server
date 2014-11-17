package com.hifun.soul.gameserver.building.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 建筑模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BuildingTemplateVO extends TemplateObject {

	/**  建筑名称多语言 */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  建筑名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  建筑描述多语言 */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  建筑描述 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  建筑图标 */
	@ExcelCellBinding(offset = 5)
	protected int icon;

	/**  是否可以升级 */
	@ExcelCellBinding(offset = 6)
	protected boolean upgrade;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 建筑名称多语言]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 建筑名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 建筑描述多语言]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 建筑描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 建筑图标]icon的值不得小于0");
		}
		this.icon = icon;
	}
	
	public boolean isUpgrade() {
		return this.upgrade;
	}

	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}
	

	@Override
	public String toString() {
		return "BuildingTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",icon=" + icon + ",upgrade=" + upgrade + ",]";

	}
}