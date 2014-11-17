package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 税收押注必胜消耗模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LevyCertainWinCostTemplateVO extends TemplateObject {

	/** 消耗的魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int crystal;


	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		if (crystal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[消耗的魔晶数]crystal不可以为0");
		}
		this.crystal = crystal;
	}
	

	@Override
	public String toString() {
		return "LevyCertainWinCostTemplateVO[crystal=" + crystal + ",]";

	}
}