package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 魔法石模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MagicStoneTemplateVO extends TemplateObject {

	/** 魔法石图标 */
	@ExcelCellBinding(offset = 1)
	protected int icon;

	/** 是否为目标宝石 */
	@ExcelCellBinding(offset = 2)
	protected boolean inTarget;


	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[魔法石图标]icon不可以为0");
		}
		this.icon = icon;
	}
	
	public boolean isInTarget() {
		return this.inTarget;
	}

	public void setInTarget(boolean inTarget) {
		this.inTarget = inTarget;
	}
	

	@Override
	public String toString() {
		return "MagicStoneTemplateVO[icon=" + icon + ",inTarget=" + inTarget + ",]";

	}
}