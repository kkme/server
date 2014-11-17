package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团矿战矿位富裕度模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineRichRateTemplateVO extends TemplateObject {

	/** 范围下限 */
	@ExcelCellBinding(offset = 1)
	protected float lowest;

	/** 范围上限 */
	@ExcelCellBinding(offset = 2)
	protected float highest;

	/** 富裕度描述 */
	@ExcelCellBinding(offset = 3)
	protected String richRateDesc;

	/** 收益比例 */
	@ExcelCellBinding(offset = 4)
	protected int revenueRate;


	public float getLowest() {
		return this.lowest;
	}

	public void setLowest(float lowest) {
		if (lowest < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[范围下限]lowest的值不得小于0");
		}
		this.lowest = lowest;
	}
	
	public float getHighest() {
		return this.highest;
	}

	public void setHighest(float highest) {
		if (highest < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[范围上限]highest的值不得小于0");
		}
		this.highest = highest;
	}
	
	public String getRichRateDesc() {
		return this.richRateDesc;
	}

	public void setRichRateDesc(String richRateDesc) {
		if (StringUtils.isEmpty(richRateDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[富裕度描述]richRateDesc不可以为空");
		}
		this.richRateDesc = richRateDesc;
	}
	
	public int getRevenueRate() {
		return this.revenueRate;
	}

	public void setRevenueRate(int revenueRate) {
		if (revenueRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[收益比例]revenueRate不可以为0");
		}
		this.revenueRate = revenueRate;
	}
	

	@Override
	public String toString() {
		return "LegionMineRichRateTemplateVO[lowest=" + lowest + ",highest=" + highest + ",richRateDesc=" + richRateDesc + ",revenueRate=" + revenueRate + ",]";

	}
}