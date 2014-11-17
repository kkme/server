package com.hifun.soul.gameserver.gem.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 宝石合成模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class GemUpgradeTemplateVO extends TemplateObject {

	/** 所需数量 */
	@ExcelCellBinding(offset = 1)
	protected int needNum;

	/** 成功率 */
	@ExcelCellBinding(offset = 2)
	protected int successRate;

	/** 成功升级后的新宝石id */
	@ExcelCellBinding(offset = 3)
	protected int successGemItemId;

	/** 合成失败降级概率 */
	@ExcelCellBinding(offset = 4)
	protected int faildRate;

	/** 升级失败后的新宝石id */
	@ExcelCellBinding(offset = 5)
	protected int faildGemItemId;

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
	
	public int getSuccessGemItemId() {
		return this.successGemItemId;
	}

	public void setSuccessGemItemId(int successGemItemId) {
		if (successGemItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[成功升级后的新宝石id]successGemItemId不可以为0");
		}
		this.successGemItemId = successGemItemId;
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
	
	public int getFaildGemItemId() {
		return this.faildGemItemId;
	}

	public void setFaildGemItemId(int faildGemItemId) {
		if (faildGemItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[升级失败后的新宝石id]faildGemItemId不可以为0");
		}
		this.faildGemItemId = faildGemItemId;
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
		return "GemUpgradeTemplateVO[needNum=" + needNum + ",successRate=" + successRate + ",successGemItemId=" + successGemItemId + ",faildRate=" + faildRate + ",faildGemItemId=" + faildGemItemId + ",currencyType=" + currencyType + ",costNum=" + costNum + ",]";

	}
}