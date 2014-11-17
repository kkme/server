package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 矿场消费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MineConsumeTemplateVO extends TemplateObject {

	/** /购买矿坑花费货币类型 */
	@ExcelCellBinding(offset = 1)
	protected int buyMineFieldCostType;

	/** 购买矿坑花费货币数量 */
	@ExcelCellBinding(offset = 2)
	protected int buyMineFieldCostNum;


	public int getBuyMineFieldCostType() {
		return this.buyMineFieldCostType;
	}

	public void setBuyMineFieldCostType(int buyMineFieldCostType) {
		if (buyMineFieldCostType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[/购买矿坑花费货币类型]buyMineFieldCostType不可以为0");
		}
		this.buyMineFieldCostType = buyMineFieldCostType;
	}
	
	public int getBuyMineFieldCostNum() {
		return this.buyMineFieldCostNum;
	}

	public void setBuyMineFieldCostNum(int buyMineFieldCostNum) {
		if (buyMineFieldCostNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[购买矿坑花费货币数量]buyMineFieldCostNum不可以为0");
		}
		this.buyMineFieldCostNum = buyMineFieldCostNum;
	}
	

	@Override
	public String toString() {
		return "MineConsumeTemplateVO[buyMineFieldCostType=" + buyMineFieldCostType + ",buyMineFieldCostNum=" + buyMineFieldCostNum + ",]";

	}
}