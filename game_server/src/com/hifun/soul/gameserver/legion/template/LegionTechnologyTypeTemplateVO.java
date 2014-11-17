package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团科技类型模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionTechnologyTypeTemplateVO extends TemplateObject {

	/** 名称多语言 */
	@ExcelCellBinding(offset = 1)
	protected int nameSysLang;

	/** 科技名称 */
	@ExcelCellBinding(offset = 2)
	protected String techName;

	/** 图标 */
	@ExcelCellBinding(offset = 3)
	protected int iconId;

	/** 描述多语言 */
	@ExcelCellBinding(offset = 4)
	protected int descSysLang;

	/** 科技效果描述 */
	@ExcelCellBinding(offset = 5)
	protected String techDesc;

	/** 需要建筑等级 */
	@ExcelCellBinding(offset = 6)
	protected int needBuildingLevel;


	public int getNameSysLang() {
		return this.nameSysLang;
	}

	public void setNameSysLang(int nameSysLang) {
		if (nameSysLang == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[名称多语言]nameSysLang不可以为0");
		}
		this.nameSysLang = nameSysLang;
	}
	
	public String getTechName() {
		return this.techName;
	}

	public void setTechName(String techName) {
		if (StringUtils.isEmpty(techName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[科技名称]techName不可以为空");
		}
		this.techName = techName;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[图标]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	
	public int getDescSysLang() {
		return this.descSysLang;
	}

	public void setDescSysLang(int descSysLang) {
		if (descSysLang == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[描述多语言]descSysLang不可以为0");
		}
		this.descSysLang = descSysLang;
	}
	
	public String getTechDesc() {
		return this.techDesc;
	}

	public void setTechDesc(String techDesc) {
		if (StringUtils.isEmpty(techDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[科技效果描述]techDesc不可以为空");
		}
		this.techDesc = techDesc;
	}
	
	public int getNeedBuildingLevel() {
		return this.needBuildingLevel;
	}

	public void setNeedBuildingLevel(int needBuildingLevel) {
		if (needBuildingLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[需要建筑等级]needBuildingLevel不可以为0");
		}
		this.needBuildingLevel = needBuildingLevel;
	}
	

	@Override
	public String toString() {
		return "LegionTechnologyTypeTemplateVO[nameSysLang=" + nameSysLang + ",techName=" + techName + ",iconId=" + iconId + ",descSysLang=" + descSysLang + ",techDesc=" + techDesc + ",needBuildingLevel=" + needBuildingLevel + ",]";

	}
}