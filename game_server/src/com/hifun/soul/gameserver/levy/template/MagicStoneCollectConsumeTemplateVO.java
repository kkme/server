package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 收集魔法石花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MagicStoneCollectConsumeTemplateVO extends TemplateObject {

	/** 消费的魔晶 */
	@ExcelCellBinding(offset = 1)
	protected int costCrystalNum;


	public int getCostCrystalNum() {
		return this.costCrystalNum;
	}

	public void setCostCrystalNum(int costCrystalNum) {
		if (costCrystalNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[消费的魔晶]costCrystalNum不可以为0");
		}
		this.costCrystalNum = costCrystalNum;
	}
	

	@Override
	public String toString() {
		return "MagicStoneCollectConsumeTemplateVO[costCrystalNum=" + costCrystalNum + ",]";

	}
}