package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 矿坑出现概率模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MineFieldRateTemplateVO extends TemplateObject {

	/** 玩家等级下限 */
	@ExcelCellBinding(offset = 1)
	protected int levelLimit;

	/** 怪物id */
	@ExcelCellBinding(offset = 2)
	protected int monsterId;

	/** 矿坑类型id */
	@ExcelCellBinding(offset = 3)
	protected int mineFieldTypeId;

	/** 矿坑类型出现的权重 */
	@ExcelCellBinding(offset = 4)
	protected int mineFieldTypeWeight;


	public int getLevelLimit() {
		return this.levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		if (levelLimit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[玩家等级下限]levelLimit不可以为0");
		}
		this.levelLimit = levelLimit;
	}
	
	public int getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(int monsterId) {
		if (monsterId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[怪物id]monsterId的值不得小于0");
		}
		this.monsterId = monsterId;
	}
	
	public int getMineFieldTypeId() {
		return this.mineFieldTypeId;
	}

	public void setMineFieldTypeId(int mineFieldTypeId) {
		if (mineFieldTypeId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[矿坑类型id]mineFieldTypeId不可以为0");
		}
		this.mineFieldTypeId = mineFieldTypeId;
	}
	
	public int getMineFieldTypeWeight() {
		return this.mineFieldTypeWeight;
	}

	public void setMineFieldTypeWeight(int mineFieldTypeWeight) {
		if (mineFieldTypeWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[矿坑类型出现的权重]mineFieldTypeWeight的值不得小于0");
		}
		this.mineFieldTypeWeight = mineFieldTypeWeight;
	}
	

	@Override
	public String toString() {
		return "MineFieldRateTemplateVO[levelLimit=" + levelLimit + ",monsterId=" + monsterId + ",mineFieldTypeId=" + mineFieldTypeId + ",mineFieldTypeWeight=" + mineFieldTypeWeight + ",]";

	}
}