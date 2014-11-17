package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团商店建筑模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionShopBuildingTemplateVO extends TemplateObject {

	/** 升至下级需要军团等级 */
	@ExcelCellBinding(offset = 1)
	protected int needLegionLevel;

	/** 升至下级需要军团资金 */
	@ExcelCellBinding(offset = 2)
	protected int needLegionCoin;

	/** 售卖物品数 */
	@ExcelCellBinding(offset = 3)
	protected int sellItemNum;


	public int getNeedLegionLevel() {
		return this.needLegionLevel;
	}

	public void setNeedLegionLevel(int needLegionLevel) {
		if (needLegionLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[升至下级需要军团等级]needLegionLevel的值不得小于0");
		}
		this.needLegionLevel = needLegionLevel;
	}
	
	public int getNeedLegionCoin() {
		return this.needLegionCoin;
	}

	public void setNeedLegionCoin(int needLegionCoin) {
		if (needLegionCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[升至下级需要军团资金]needLegionCoin的值不得小于0");
		}
		this.needLegionCoin = needLegionCoin;
	}
	
	public int getSellItemNum() {
		return this.sellItemNum;
	}

	public void setSellItemNum(int sellItemNum) {
		if (sellItemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[售卖物品数]sellItemNum不可以为0");
		}
		this.sellItemNum = sellItemNum;
	}
	

	@Override
	public String toString() {
		return "LegionShopBuildingTemplateVO[needLegionLevel=" + needLegionLevel + ",needLegionCoin=" + needLegionCoin + ",sellItemNum=" + sellItemNum + ",]";

	}
}