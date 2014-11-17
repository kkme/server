package com.hifun.soul.gameserver.elitestage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 刷新精英副本状态花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EliteStageStateRefreshCostTemplateVO extends TemplateObject {

	/** 消耗魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int costCrystal;


	public int getCostCrystal() {
		return this.costCrystal;
	}

	public void setCostCrystal(int costCrystal) {
		if (costCrystal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[消耗魔晶数]costCrystal不可以为0");
		}
		this.costCrystal = costCrystal;
	}
	

	@Override
	public String toString() {
		return "EliteStageStateRefreshCostTemplateVO[costCrystal=" + costCrystal + ",]";

	}
}