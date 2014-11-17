package com.hifun.soul.gameserver.foster.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 培养金币消耗模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FosterCoinCostTemplateVO extends TemplateObject {

	/** 花费培养币数量 */
	@ExcelCellBinding(offset = 1)
	protected int trainCoinCostNum;


	public int getTrainCoinCostNum() {
		return this.trainCoinCostNum;
	}

	public void setTrainCoinCostNum(int trainCoinCostNum) {
		if (trainCoinCostNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[花费培养币数量]trainCoinCostNum不可以为0");
		}
		this.trainCoinCostNum = trainCoinCostNum;
	}
	

	@Override
	public String toString() {
		return "FosterCoinCostTemplateVO[trainCoinCostNum=" + trainCoinCostNum + ",]";

	}
}