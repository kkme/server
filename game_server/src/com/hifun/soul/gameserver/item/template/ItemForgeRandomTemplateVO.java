package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 物品洗练随机属性的概率
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ItemForgeRandomTemplateVO extends TemplateObject {

	/** 随机范围低 */
	@ExcelCellBinding(offset = 1)
	protected int minValue;

	/** 随机范围高 */
	@ExcelCellBinding(offset = 2)
	protected int maxValue;

	/** 概率低 */
	@ExcelCellBinding(offset = 3)
	protected int minRate;

	/** 概率高 */
	@ExcelCellBinding(offset = 4)
	protected int maxRate;


	public int getMinValue() {
		return this.minValue;
	}

	public void setMinValue(int minValue) {
		if (minValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[随机范围低]minValue的值不得小于0");
		}
		this.minValue = minValue;
	}
	
	public int getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(int maxValue) {
		if (maxValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[随机范围高]maxValue的值不得小于0");
		}
		this.maxValue = maxValue;
	}
	
	public int getMinRate() {
		return this.minRate;
	}

	public void setMinRate(int minRate) {
		if (minRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[概率低]minRate的值不得小于0");
		}
		this.minRate = minRate;
	}
	
	public int getMaxRate() {
		return this.maxRate;
	}

	public void setMaxRate(int maxRate) {
		if (maxRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[概率高]maxRate的值不得小于0");
		}
		this.maxRate = maxRate;
	}
	

	@Override
	public String toString() {
		return "ItemForgeRandomTemplateVO[minValue=" + minValue + ",maxValue=" + maxValue + ",minRate=" + minRate + ",maxRate=" + maxRate + ",]";

	}
}