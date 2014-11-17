package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团矿战矿位类型模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineTypeTemplateVO extends TemplateObject {

	/** 矿位数量 */
	@ExcelCellBinding(offset = 1)
	protected int mineNum;

	/** 人数上限基数 */
	@ExcelCellBinding(offset = 2)
	protected int peopleBaseNum;

	/** 收益 */
	@ExcelCellBinding(offset = 3)
	protected int singleRevenue;


	public int getMineNum() {
		return this.mineNum;
	}

	public void setMineNum(int mineNum) {
		if (mineNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[矿位数量]mineNum不可以为0");
		}
		this.mineNum = mineNum;
	}
	
	public int getPeopleBaseNum() {
		return this.peopleBaseNum;
	}

	public void setPeopleBaseNum(int peopleBaseNum) {
		if (peopleBaseNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[人数上限基数]peopleBaseNum不可以为0");
		}
		this.peopleBaseNum = peopleBaseNum;
	}
	
	public int getSingleRevenue() {
		return this.singleRevenue;
	}

	public void setSingleRevenue(int singleRevenue) {
		if (singleRevenue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[收益]singleRevenue不可以为0");
		}
		this.singleRevenue = singleRevenue;
	}
	

	@Override
	public String toString() {
		return "LegionMineTypeTemplateVO[mineNum=" + mineNum + ",peopleBaseNum=" + peopleBaseNum + ",singleRevenue=" + singleRevenue + ",]";

	}
}