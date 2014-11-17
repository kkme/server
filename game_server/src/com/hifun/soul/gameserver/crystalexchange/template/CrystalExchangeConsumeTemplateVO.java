package com.hifun.soul.gameserver.crystalexchange.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 魔晶兑换消耗模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CrystalExchangeConsumeTemplateVO extends TemplateObject {

	/**  消耗货币类型 */
	@ExcelCellBinding(offset = 1)
	protected Short currencyType;

	/**  消耗货币数量 */
	@ExcelCellBinding(offset = 2)
	protected int currencyNum;


	public Short getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(Short currencyType) {
		if (currencyType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 消耗货币类型]currencyType不可以为空");
		}	
		if (currencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 消耗货币类型]currencyType的值不得小于1");
		}
		this.currencyType = currencyType;
	}
	
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		if (currencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 消耗货币数量]currencyNum不可以为0");
		}
		if (currencyNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 消耗货币数量]currencyNum的值不得小于1");
		}
		this.currencyNum = currencyNum;
	}
	

	@Override
	public String toString() {
		return "CrystalExchangeConsumeTemplateVO[currencyType=" + currencyType + ",currencyNum=" + currencyNum + ",]";

	}
}