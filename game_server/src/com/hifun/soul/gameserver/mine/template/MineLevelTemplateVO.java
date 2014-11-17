package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 矿坑收益成长模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MineLevelTemplateVO extends TemplateObject {

	/** 金币收益 */
	@ExcelCellBinding(offset = 1)
	protected int coin;

	/** 培养币收益 */
	@ExcelCellBinding(offset = 2)
	protected int trainCoin;


	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		if (coin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[金币收益]coin不可以为0");
		}
		this.coin = coin;
	}
	
	public int getTrainCoin() {
		return this.trainCoin;
	}

	public void setTrainCoin(int trainCoin) {
		if (trainCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[培养币收益]trainCoin不可以为0");
		}
		this.trainCoin = trainCoin;
	}
	

	@Override
	public String toString() {
		return "MineLevelTemplateVO[coin=" + coin + ",trainCoin=" + trainCoin + ",]";

	}
}