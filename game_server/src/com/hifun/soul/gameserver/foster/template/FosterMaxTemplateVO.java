package com.hifun.soul.gameserver.foster.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 培养上限模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FosterMaxTemplateVO extends TemplateObject {

	/** 一级属性 */
	@ExcelCellBinding(offset = 1)
	protected int level1Property;

	/** 角色等级 */
	@ExcelCellBinding(offset = 2)
	protected int humanLevel;

	/** 培养上限 */
	@ExcelCellBinding(offset = 3)
	protected int maxFosterNum;


	public int getLevel1Property() {
		return this.level1Property;
	}

	public void setLevel1Property(int level1Property) {
		if (level1Property == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[一级属性]level1Property不可以为0");
		}
		this.level1Property = level1Property;
	}
	
	public int getHumanLevel() {
		return this.humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		if (humanLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[角色等级]humanLevel不可以为0");
		}
		this.humanLevel = humanLevel;
	}
	
	public int getMaxFosterNum() {
		return this.maxFosterNum;
	}

	public void setMaxFosterNum(int maxFosterNum) {
		if (maxFosterNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[培养上限]maxFosterNum的值不得小于0");
		}
		this.maxFosterNum = maxFosterNum;
	}
	

	@Override
	public String toString() {
		return "FosterMaxTemplateVO[level1Property=" + level1Property + ",humanLevel=" + humanLevel + ",maxFosterNum=" + maxFosterNum + ",]";

	}
}