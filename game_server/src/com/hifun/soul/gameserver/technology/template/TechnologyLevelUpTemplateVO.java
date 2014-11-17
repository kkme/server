package com.hifun.soul.gameserver.technology.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 科技升级模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TechnologyLevelUpTemplateVO extends TemplateObject {

	/**  科技id */
	@ExcelCellBinding(offset = 1)
	protected int technologyId;

	/**  科技等级 */
	@ExcelCellBinding(offset = 2)
	protected int level;

	/**  角色等级限制 */
	@ExcelCellBinding(offset = 3)
	protected int roleLevel;

	/**  属性id */
	@ExcelCellBinding(offset = 4)
	protected int key;

	/**  升级属性加成值 */
	@ExcelCellBinding(offset = 5)
	protected int propAddValue;

	/**  升级消耗的科技点数 */
	@ExcelCellBinding(offset = 6)
	protected int costValue;


	public int getTechnologyId() {
		return this.technologyId;
	}

	public void setTechnologyId(int technologyId) {
		if (technologyId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 科技id]technologyId不可以为0");
		}
		if (technologyId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 科技id]technologyId的值不得小于1");
		}
		this.technologyId = technologyId;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 科技等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getRoleLevel() {
		return this.roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		if (roleLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 角色等级限制]roleLevel的值不得小于0");
		}
		this.roleLevel = roleLevel;
	}
	
	public int getKey() {
		return this.key;
	}

	public void setKey(int key) {
		if (key < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 属性id]key的值不得小于0");
		}
		this.key = key;
	}
	
	public int getPropAddValue() {
		return this.propAddValue;
	}

	public void setPropAddValue(int propAddValue) {
		if (propAddValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 升级属性加成值]propAddValue的值不得小于0");
		}
		this.propAddValue = propAddValue;
	}
	
	public int getCostValue() {
		return this.costValue;
	}

	public void setCostValue(int costValue) {
		if (costValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 升级消耗的科技点数]costValue的值不得小于0");
		}
		this.costValue = costValue;
	}
	

	@Override
	public String toString() {
		return "TechnologyLevelUpTemplateVO[technologyId=" + technologyId + ",level=" + level + ",roleLevel=" + roleLevel + ",key=" + key + ",propAddValue=" + propAddValue + ",costValue=" + costValue + ",]";

	}
}