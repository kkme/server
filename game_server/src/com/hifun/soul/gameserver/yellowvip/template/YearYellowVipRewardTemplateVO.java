package com.hifun.soul.gameserver.yellowvip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 年费黄钻奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class YearYellowVipRewardTemplateVO extends TemplateObject {

	/** 物品1id */
	@ExcelCellBinding(offset = 1)
	protected int itemId1;

	/** 物品1数量 */
	@ExcelCellBinding(offset = 2)
	protected int itemCount1;

	/** 物品2id */
	@ExcelCellBinding(offset = 3)
	protected int itemId2;

	/** 物品2数量 */
	@ExcelCellBinding(offset = 4)
	protected int itemCount2;


	public int getItemId1() {
		return this.itemId1;
	}

	public void setItemId1(int itemId1) {
		if (itemId1 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[物品1id]itemId1不可以为0");
		}
		this.itemId1 = itemId1;
	}
	
	public int getItemCount1() {
		return this.itemCount1;
	}

	public void setItemCount1(int itemCount1) {
		if (itemCount1 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[物品1数量]itemCount1不可以为0");
		}
		this.itemCount1 = itemCount1;
	}
	
	public int getItemId2() {
		return this.itemId2;
	}

	public void setItemId2(int itemId2) {
		if (itemId2 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[物品2id]itemId2不可以为0");
		}
		this.itemId2 = itemId2;
	}
	
	public int getItemCount2() {
		return this.itemCount2;
	}

	public void setItemCount2(int itemCount2) {
		if (itemCount2 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[物品2数量]itemCount2不可以为0");
		}
		this.itemCount2 = itemCount2;
	}
	

	@Override
	public String toString() {
		return "YearYellowVipRewardTemplateVO[itemId1=" + itemId1 + ",itemCount1=" + itemCount1 + ",itemId2=" + itemId2 + ",itemCount2=" + itemCount2 + ",]";

	}
}