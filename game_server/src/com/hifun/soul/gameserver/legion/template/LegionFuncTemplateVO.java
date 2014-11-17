package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团子功能开启模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionFuncTemplateVO extends TemplateObject {

	/** 开启所需军团等级 */
	@ExcelCellBinding(offset = 1)
	protected int openLegionLevel;


	public int getOpenLegionLevel() {
		return this.openLegionLevel;
	}

	public void setOpenLegionLevel(int openLegionLevel) {
		if (openLegionLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[开启所需军团等级]openLegionLevel不可以为0");
		}
		this.openLegionLevel = openLegionLevel;
	}
	

	@Override
	public String toString() {
		return "LegionFuncTemplateVO[openLegionLevel=" + openLegionLevel + ",]";

	}
}