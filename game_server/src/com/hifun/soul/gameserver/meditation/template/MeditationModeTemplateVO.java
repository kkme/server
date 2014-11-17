package com.hifun.soul.gameserver.meditation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 冥想模式
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MeditationModeTemplateVO extends TemplateObject {

	/** 冥想模式名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 收益倍率(万分制) */
	@ExcelCellBinding(offset = 2)
	protected int rate;

	/** 所需货币类型 */
	@ExcelCellBinding(offset = 3)
	protected int currencyType;

	/** 所需货币数量 */
	@ExcelCellBinding(offset = 4)
	protected int currencyNum;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[冥想模式名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		if (rate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[收益倍率(万分制)]rate不可以为0");
		}
		this.rate = rate;
	}
	
	public int getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(int currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[所需货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		if (currencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[所需货币数量]currencyNum不可以为0");
		}
		if (currencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[所需货币数量]currencyNum的值不得小于0");
		}
		this.currencyNum = currencyNum;
	}
	

	@Override
	public String toString() {
		return "MeditationModeTemplateVO[name=" + name + ",rate=" + rate + ",currencyType=" + currencyType + ",currencyNum=" + currencyNum + ",]";

	}
}