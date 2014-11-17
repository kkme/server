package com.hifun.soul.gameserver.vip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 战俘营购买抓捕次数消费模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class PrisonBuyArrestNumTemplateVO extends TemplateObject {

	/** 消耗的魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int costCrystal;


	public int getCostCrystal() {
		return this.costCrystal;
	}

	public void setCostCrystal(int costCrystal) {
		if (costCrystal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[消耗的魔晶数]costCrystal不可以为0");
		}
		this.costCrystal = costCrystal;
	}
	

	@Override
	public String toString() {
		return "PrisonBuyArrestNumTemplateVO[costCrystal=" + costCrystal + ",]";

	}
}