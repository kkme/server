package com.hifun.soul.gameserver.cd.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * cd疲劳度模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CdTiredTemplateVO extends TemplateObject {

	/**  疲劳度 */
	@ExcelCellBinding(offset = 1)
	protected Float tired;


	public Float getTired() {
		return this.tired;
	}

	public void setTired(Float tired) {
		if (tired == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 疲劳度]tired不可以为空");
		}	
		this.tired = tired;
	}
	

	@Override
	public String toString() {
		return "CdTiredTemplateVO[tired=" + tired + ",]";

	}
}