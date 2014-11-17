package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 购买征税次数消耗模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LevyCostTemplateVO extends TemplateObject {

	/** 魔晶消耗 */
	@ExcelCellBinding(offset = 1)
	protected int crystalCost;


	public int getCrystalCost() {
		return this.crystalCost;
	}

	public void setCrystalCost(int crystalCost) {
		if (crystalCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[魔晶消耗]crystalCost不可以为0");
		}
		this.crystalCost = crystalCost;
	}
	

	@Override
	public String toString() {
		return "LevyCostTemplateVO[crystalCost=" + crystalCost + ",]";

	}
}