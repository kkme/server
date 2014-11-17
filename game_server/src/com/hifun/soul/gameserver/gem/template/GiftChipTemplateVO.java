package com.hifun.soul.gameserver.gem.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 天赋碎片合成模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class GiftChipTemplateVO extends TemplateObject {

	/** 所需数量 */
	@ExcelCellBinding(offset = 1)
	protected int needNum;

	/** 成功率 */
	@ExcelCellBinding(offset = 2)
	protected int successRate;

	/** 成功升级后的新天赋碎片id */
	@ExcelCellBinding(offset = 3)
	protected int successGiftChipItemId;

	/** 合成失败降级概率 */
	@ExcelCellBinding(offset = 4)
	protected int faildRate;

	/** 升级失败后的新天赋碎片id */
	@ExcelCellBinding(offset = 5)
	protected int faildGiftChipItemId;

	/** 升级花费的货币类型 */
	@ExcelCellBinding(offset = 6)
	protected int currencyType;

	/** 花费的货币数量 */
	@ExcelCellBinding(offset = 7)
	protected int costNum;


	public int getNeedNum() {
		return this.needNum;
	}

	public void setNeedNum(int needNum) {
		if (needNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[所需数量]needNum不可以为0");
		}
		this.needNum = needNum;
	}
	
	public int getSuccessRate() {
		return this.successRate;
	}

	public void setSuccessRate(int successRate) {
		if (successRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[成功率]successRate的值不得小于0");
		}
		this.successRate = successRate;
	}
	
	public int getSuccessGiftChipItemId() {
		return this.successGiftChipItemId;
	}

	public void setSuccessGiftChipItemId(int successGiftChipItemId) {
		if (successGiftChipItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[成功升级后的新天赋碎片id]successGiftChipItemId不可以为0");
		}
		this.successGiftChipItemId = successGiftChipItemId;
	}
	
	public int getFaildRate() {
		return this.faildRate;
	}

	public void setFaildRate(int faildRate) {
		if (faildRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[合成失败降级概率]faildRate的值不得小于0");
		}
		this.faildRate = faildRate;
	}
	
	public int getFaildGiftChipItemId() {
		return this.faildGiftChipItemId;
	}

	public void setFaildGiftChipItemId(int faildGiftChipItemId) {
		if (faildGiftChipItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[升级失败后的新天赋碎片id]faildGiftChipItemId不可以为0");
		}
		this.faildGiftChipItemId = faildGiftChipItemId;
	}
	
	public int getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(int currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[升级花费的货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		if (costNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[花费的货币数量]costNum不可以为0");
		}
		this.costNum = costNum;
	}
	

	@Override
	public String toString() {
		return "GiftChipTemplateVO[needNum=" + needNum + ",successRate=" + successRate + ",successGiftChipItemId=" + successGiftChipItemId + ",faildRate=" + faildRate + ",faildGiftChipItemId=" + faildGiftChipItemId + ",currencyType=" + currencyType + ",costNum=" + costNum + ",]";

	}
}