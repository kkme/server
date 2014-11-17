package com.hifun.soul.gameserver.foster.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 培养花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FosterCostTemplateVO extends TemplateObject {

	/** 花费货币类型 */
	@ExcelCellBinding(offset = 1)
	protected int costCurrencyType;

	/** 花费数量 */
	@ExcelCellBinding(offset = 2)
	protected int costNum;


	public int getCostCurrencyType() {
		return this.costCurrencyType;
	}

	public void setCostCurrencyType(int costCurrencyType) {
		if (costCurrencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[花费货币类型]costCurrencyType不可以为0");
		}
		this.costCurrencyType = costCurrencyType;
	}
	
	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		if (costNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[花费数量]costNum的值不得小于0");
		}
		this.costNum = costNum;
	}
	

	@Override
	public String toString() {
		return "FosterCostTemplateVO[costCurrencyType=" + costCurrencyType + ",costNum=" + costNum + ",]";

	}
}