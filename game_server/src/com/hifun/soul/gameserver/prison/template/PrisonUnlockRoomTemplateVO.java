package com.hifun.soul.gameserver.prison.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 战俘营解锁房间消费模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class PrisonUnlockRoomTemplateVO extends TemplateObject {

	/** 消费货币类型 */
	@ExcelCellBinding(offset = 1)
	protected int currencyType;

	/** 消耗数量 */
	@ExcelCellBinding(offset = 2)
	protected int costNum;


	public int getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(int currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[消费货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		if (costNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[消耗数量]costNum不可以为0");
		}
		this.costNum = costNum;
	}
	

	@Override
	public String toString() {
		return "PrisonUnlockRoomTemplateVO[currencyType=" + currencyType + ",costNum=" + costNum + ",]";

	}
}