package com.hifun.soul.gameserver.human.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 购买体力值花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BuyEnergyCostTemplateVO extends TemplateObject {

	/** 获得的体力值 */
	@ExcelCellBinding(offset = 1)
	protected int energyNum;

	/** 魔晶花费 */
	@ExcelCellBinding(offset = 2)
	protected int crystalCost;


	public int getEnergyNum() {
		return this.energyNum;
	}

	public void setEnergyNum(int energyNum) {
		if (energyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[获得的体力值]energyNum不可以为0");
		}
		this.energyNum = energyNum;
	}
	
	public int getCrystalCost() {
		return this.crystalCost;
	}

	public void setCrystalCost(int crystalCost) {
		if (crystalCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[魔晶花费]crystalCost不可以为0");
		}
		this.crystalCost = crystalCost;
	}
	

	@Override
	public String toString() {
		return "BuyEnergyCostTemplateVO[energyNum=" + energyNum + ",crystalCost=" + crystalCost + ",]";

	}
}