package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 税收押注胜利收益模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LevyBetRevenueTemplateVO extends TemplateObject {

	/** 押注胜利获得训练币 */
	@ExcelCellBinding(offset = 1)
	protected int trainCoin;


	public int getTrainCoin() {
		return this.trainCoin;
	}

	public void setTrainCoin(int trainCoin) {
		if (trainCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[押注胜利获得训练币]trainCoin不可以为0");
		}
		this.trainCoin = trainCoin;
	}
	

	@Override
	public String toString() {
		return "LevyBetRevenueTemplateVO[trainCoin=" + trainCoin + ",]";

	}
}