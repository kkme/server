package com.hifun.soul.gameserver.skill.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 技能卷轴模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SkillScrollTemplateVO extends TemplateObject {

	/**  技能类型 */
	@ExcelCellBinding(offset = 1)
	protected int skillDevelopType;

	/**  前置技能id */
	@ExcelCellBinding(offset = 2)
	protected int preSkillId;

	/**  需要的技能点 */
	@ExcelCellBinding(offset = 3)
	protected int needSkillPoint;

	/**  需要的卷轴id */
	@ExcelCellBinding(offset = 4)
	protected int needSkillScrollId;

	/**  技能是否默认开启 */
	@ExcelCellBinding(offset = 5)
	protected boolean defaultOpen;


	public int getSkillDevelopType() {
		return this.skillDevelopType;
	}

	public void setSkillDevelopType(int skillDevelopType) {
		if (skillDevelopType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 技能类型]skillDevelopType的值不得小于0");
		}
		this.skillDevelopType = skillDevelopType;
	}
	
	public int getPreSkillId() {
		return this.preSkillId;
	}

	public void setPreSkillId(int preSkillId) {
		if (preSkillId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 前置技能id]preSkillId的值不得小于0");
		}
		this.preSkillId = preSkillId;
	}
	
	public int getNeedSkillPoint() {
		return this.needSkillPoint;
	}

	public void setNeedSkillPoint(int needSkillPoint) {
		if (needSkillPoint < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 需要的技能点]needSkillPoint的值不得小于0");
		}
		this.needSkillPoint = needSkillPoint;
	}
	
	public int getNeedSkillScrollId() {
		return this.needSkillScrollId;
	}

	public void setNeedSkillScrollId(int needSkillScrollId) {
		if (needSkillScrollId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 需要的卷轴id]needSkillScrollId的值不得小于0");
		}
		this.needSkillScrollId = needSkillScrollId;
	}
	
	public boolean isDefaultOpen() {
		return this.defaultOpen;
	}

	public void setDefaultOpen(boolean defaultOpen) {
		this.defaultOpen = defaultOpen;
	}
	

	@Override
	public String toString() {
		return "SkillScrollTemplateVO[skillDevelopType=" + skillDevelopType + ",preSkillId=" + preSkillId + ",needSkillPoint=" + needSkillPoint + ",needSkillScrollId=" + needSkillScrollId + ",defaultOpen=" + defaultOpen + ",]";

	}
}