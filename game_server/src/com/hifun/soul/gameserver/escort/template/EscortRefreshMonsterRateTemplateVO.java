package com.hifun.soul.gameserver.escort.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 刷新怪物概率模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EscortRefreshMonsterRateTemplateVO extends TemplateObject {

	/** 升一级概率 */
	@ExcelCellBinding(offset = 1)
	protected int upRate;

	/** 不变概率 */
	@ExcelCellBinding(offset = 2)
	protected int holdRate;

	/** 降一级概率 */
	@ExcelCellBinding(offset = 3)
	protected int downRate;


	public int getUpRate() {
		return this.upRate;
	}

	public void setUpRate(int upRate) {
		if (upRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[升一级概率]upRate的值不得小于0");
		}
		this.upRate = upRate;
	}
	
	public int getHoldRate() {
		return this.holdRate;
	}

	public void setHoldRate(int holdRate) {
		if (holdRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[不变概率]holdRate的值不得小于0");
		}
		this.holdRate = holdRate;
	}
	
	public int getDownRate() {
		return this.downRate;
	}

	public void setDownRate(int downRate) {
		if (downRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[降一级概率]downRate的值不得小于0");
		}
		this.downRate = downRate;
	}
	

	@Override
	public String toString() {
		return "EscortRefreshMonsterRateTemplateVO[upRate=" + upRate + ",holdRate=" + holdRate + ",downRate=" + downRate + ",]";

	}
}