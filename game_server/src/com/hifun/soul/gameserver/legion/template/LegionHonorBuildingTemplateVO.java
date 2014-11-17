package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团荣誉建筑模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionHonorBuildingTemplateVO extends TemplateObject {

	/** 升至下级需要军团等级 */
	@ExcelCellBinding(offset = 1)
	protected int needLegionLevel;

	/** 升至下级需要军团资金 */
	@ExcelCellBinding(offset = 2)
	protected int needLegionCoin;

	/** 可兑换头衔数 */
	@ExcelCellBinding(offset = 3)
	protected int exchangeNum;


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
	
	public int getExchangeNum() {
		return this.exchangeNum;
	}

	public void setExchangeNum(int exchangeNum) {
		if (exchangeNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[可兑换头衔数]exchangeNum不可以为0");
		}
		this.exchangeNum = exchangeNum;
	}
	

	@Override
	public String toString() {
		return "LegionHonorBuildingTemplateVO[needLegionLevel=" + needLegionLevel + ",needLegionCoin=" + needLegionCoin + ",exchangeNum=" + exchangeNum + ",]";

	}
}