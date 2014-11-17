package com.hifun.soul.gameserver.skill.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 技能发展类型模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SkillDevelopTypeTemplateVO extends TemplateObject {

	/** 技能发展类型 */
	@ExcelCellBinding(offset = 1)
	protected int skillDevelopType;

	/** 职业 */
	@ExcelCellBinding(offset = 2)
	protected int occupation;

	/** 技能发展类型图标 */
	@ExcelCellBinding(offset = 3)
	protected int icon;

	/** 推荐加点类型 */
	@ExcelCellBinding(offset = 4)
	protected int recommendPointType;

	/**  主要消耗魔法 */
	@ExcelCellBinding(offset = 5)
	protected int mainCostMagic;

	/**  辅助消耗魔法(分号隔开) */
	@ExcelCellBinding(offset = 6)
	protected String assistCostMagic;


	public int getSkillDevelopType() {
		return this.skillDevelopType;
	}

	public void setSkillDevelopType(int skillDevelopType) {
		if (skillDevelopType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[技能发展类型]skillDevelopType不可以为0");
		}
		this.skillDevelopType = skillDevelopType;
	}
	
	public int getOccupation() {
		return this.occupation;
	}

	public void setOccupation(int occupation) {
		if (occupation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[职业]occupation不可以为0");
		}
		this.occupation = occupation;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[技能发展类型图标]icon不可以为0");
		}
		this.icon = icon;
	}
	
	public int getRecommendPointType() {
		return this.recommendPointType;
	}

	public void setRecommendPointType(int recommendPointType) {
		if (recommendPointType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[推荐加点类型]recommendPointType的值不得小于0");
		}
		this.recommendPointType = recommendPointType;
	}
	
	public int getMainCostMagic() {
		return this.mainCostMagic;
	}

	public void setMainCostMagic(int mainCostMagic) {
		if (mainCostMagic < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 主要消耗魔法]mainCostMagic的值不得小于0");
		}
		this.mainCostMagic = mainCostMagic;
	}
	
	public String getAssistCostMagic() {
		return this.assistCostMagic;
	}

	public void setAssistCostMagic(String assistCostMagic) {
		if (StringUtils.isEmpty(assistCostMagic)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 辅助消耗魔法(分号隔开)]assistCostMagic不可以为空");
		}
		this.assistCostMagic = assistCostMagic;
	}
	

	@Override
	public String toString() {
		return "SkillDevelopTypeTemplateVO[skillDevelopType=" + skillDevelopType + ",occupation=" + occupation + ",icon=" + icon + ",recommendPointType=" + recommendPointType + ",mainCostMagic=" + mainCostMagic + ",assistCostMagic=" + assistCostMagic + ",]";

	}
}