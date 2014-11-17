package com.hifun.soul.gameserver.bag.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 主背包升级花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MainBagUpgradeTemplateVO extends TemplateObject {

	/** 开启背包的花费 */
	@ExcelCellBinding(offset = 1)
	protected int costNum;


	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		if (costNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[开启背包的花费]costNum不可以为0");
		}
		this.costNum = costNum;
	}
	

	@Override
	public String toString() {
		return "MainBagUpgradeTemplateVO[costNum=" + costNum + ",]";

	}
}