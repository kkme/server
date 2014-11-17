package com.hifun.soul.gameserver.title.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;

/**
 * 角色军衔列表模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HumanTitleTemplateVO extends TemplateObject {

	/** 军衔名称 */
	@ExcelCellBinding(offset = 1)
	protected String titleName;

	/** 升至下级所需威望 */
	@ExcelCellBinding(offset = 2)
	protected int needPrestige;

	/** 携带技能数量 */
	@ExcelCellBinding(offset = 3)
	protected int titleSkillNum;

	/** 每日俸禄 */
	@ExcelCellBinding(offset = 4)
	protected int titleSalary;

	/**  军衔对应属性加成 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.title.template.HumanTitleProperty[].class, collectionNumber = "5,6,7;8,9,10;11,12,13;14,15,16;17,18,19")
	protected com.hifun.soul.gameserver.title.template.HumanTitleProperty[] titleProperties;

	/** 荣誉上限 */
	@ExcelCellBinding(offset = 20)
	protected int maxHonor;


	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	public int getNeedPrestige() {
		return this.needPrestige;
	}

	public void setNeedPrestige(int needPrestige) {
		this.needPrestige = needPrestige;
	}
	
	public int getTitleSkillNum() {
		return this.titleSkillNum;
	}

	public void setTitleSkillNum(int titleSkillNum) {
		this.titleSkillNum = titleSkillNum;
	}
	
	public int getTitleSalary() {
		return this.titleSalary;
	}

	public void setTitleSalary(int titleSalary) {
		this.titleSalary = titleSalary;
	}
	
	public com.hifun.soul.gameserver.title.template.HumanTitleProperty[] getTitleProperties() {
		return this.titleProperties;
	}

	public void setTitleProperties(com.hifun.soul.gameserver.title.template.HumanTitleProperty[] titleProperties) {
		if (titleProperties == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 军衔对应属性加成]titleProperties不可以为空");
		}	
		this.titleProperties = titleProperties;
	}
	
	public int getMaxHonor() {
		return this.maxHonor;
	}

	public void setMaxHonor(int maxHonor) {
		this.maxHonor = maxHonor;
	}
	

	@Override
	public String toString() {
		return "HumanTitleTemplateVO[titleName=" + titleName + ",needPrestige=" + needPrestige + ",titleSkillNum=" + titleSkillNum + ",titleSalary=" + titleSalary + ",titleProperties=" + titleProperties + ",maxHonor=" + maxHonor + ",]";

	}
}