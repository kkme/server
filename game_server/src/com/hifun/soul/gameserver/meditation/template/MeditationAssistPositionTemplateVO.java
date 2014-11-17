package com.hifun.soul.gameserver.meditation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 冥想协助位模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MeditationAssistPositionTemplateVO extends TemplateObject {

	/** 可用金币开启的等级限制 */
	@ExcelCellBinding(offset = 1)
	protected int openLevel;

	/** 金币花费 */
	@ExcelCellBinding(offset = 2)
	protected int costCoinNum;

	/** 花费魔晶数量 */
	@ExcelCellBinding(offset = 3)
	protected int costCrystalNum;

	/** 发出的申请数 */
	@ExcelCellBinding(offset = 4)
	protected int applyNum;

	/** 协助收益比例 */
	@ExcelCellBinding(offset = 5)
	protected int assistRate;


	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[可用金币开启的等级限制]openLevel不可以为0");
		}
		this.openLevel = openLevel;
	}
	
	public int getCostCoinNum() {
		return this.costCoinNum;
	}

	public void setCostCoinNum(int costCoinNum) {
		if (costCoinNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[金币花费]costCoinNum不可以为0");
		}
		this.costCoinNum = costCoinNum;
	}
	
	public int getCostCrystalNum() {
		return this.costCrystalNum;
	}

	public void setCostCrystalNum(int costCrystalNum) {
		if (costCrystalNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[花费魔晶数量]costCrystalNum不可以为0");
		}
		this.costCrystalNum = costCrystalNum;
	}
	
	public int getApplyNum() {
		return this.applyNum;
	}

	public void setApplyNum(int applyNum) {
		if (applyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[发出的申请数]applyNum不可以为0");
		}
		this.applyNum = applyNum;
	}
	
	public int getAssistRate() {
		return this.assistRate;
	}

	public void setAssistRate(int assistRate) {
		if (assistRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[协助收益比例]assistRate不可以为0");
		}
		this.assistRate = assistRate;
	}
	

	@Override
	public String toString() {
		return "MeditationAssistPositionTemplateVO[openLevel=" + openLevel + ",costCoinNum=" + costCoinNum + ",costCrystalNum=" + costCrystalNum + ",applyNum=" + applyNum + ",assistRate=" + assistRate + ",]";

	}
}