package com.hifun.soul.gameserver.predict.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 预见页码模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class PredictPageTemplateVO extends TemplateObject {

	/** 可见等级 */
	@ExcelCellBinding(offset = 1)
	protected int visibleLevel;

	/** 等级下限 */
	@ExcelCellBinding(offset = 2)
	protected int minLevel;

	/** 等级上限 */
	@ExcelCellBinding(offset = 3)
	protected int maxLevel;


	public int getVisibleLevel() {
		return this.visibleLevel;
	}

	public void setVisibleLevel(int visibleLevel) {
		if (visibleLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[可见等级]visibleLevel不可以为0");
		}
		this.visibleLevel = visibleLevel;
	}
	
	public int getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(int minLevel) {
		if (minLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[等级下限]minLevel不可以为0");
		}
		this.minLevel = minLevel;
	}
	
	public int getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		if (maxLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[等级上限]maxLevel不可以为0");
		}
		this.maxLevel = maxLevel;
	}
	

	@Override
	public String toString() {
		return "PredictPageTemplateVO[visibleLevel=" + visibleLevel + ",minLevel=" + minLevel + ",maxLevel=" + maxLevel + ",]";

	}
}