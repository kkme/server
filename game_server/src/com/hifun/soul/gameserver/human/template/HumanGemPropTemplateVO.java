package com.hifun.soul.gameserver.human.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 角色宝石属性模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HumanGemPropTemplateVO extends TemplateObject {

	/**  初始值 */
	@ExcelCellBinding(offset = 1)
	protected int initValue;

	/**  上限 */
	@ExcelCellBinding(offset = 2)
	protected int maxValue;

	/**  每个宝石消除能量 */
	@ExcelCellBinding(offset = 3)
	protected int eliminateBonus;


	public int getInitValue() {
		return this.initValue;
	}

	public void setInitValue(int initValue) {
		if (initValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 初始值]initValue的值不得小于0");
		}
		this.initValue = initValue;
	}
	
	public int getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(int maxValue) {
		if (maxValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 上限]maxValue的值不得小于0");
		}
		this.maxValue = maxValue;
	}
	
	public int getEliminateBonus() {
		return this.eliminateBonus;
	}

	public void setEliminateBonus(int eliminateBonus) {
		if (eliminateBonus < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 每个宝石消除能量]eliminateBonus的值不得小于0");
		}
		this.eliminateBonus = eliminateBonus;
	}
	

	@Override
	public String toString() {
		return "HumanGemPropTemplateVO[initValue=" + initValue + ",maxValue=" + maxValue + ",eliminateBonus=" + eliminateBonus + ",]";

	}
}