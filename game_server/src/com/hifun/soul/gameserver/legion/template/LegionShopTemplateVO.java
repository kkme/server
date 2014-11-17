package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团商店物品模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionShopTemplateVO extends TemplateObject {

	/** 物品ID */
	@ExcelCellBinding(offset = 1)
	protected int itemId;

	/** 消耗勋章 */
	@ExcelCellBinding(offset = 2)
	protected int costMedal;

	/** 每日限量 */
	@ExcelCellBinding(offset = 3)
	protected int dayNum;

	/** 物品类型 */
	@ExcelCellBinding(offset = 4)
	protected int itemType;

	/** 需要建筑等级 */
	@ExcelCellBinding(offset = 5)
	protected int needBuildingLevel;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[物品ID]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getCostMedal() {
		return this.costMedal;
	}

	public void setCostMedal(int costMedal) {
		if (costMedal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[消耗勋章]costMedal不可以为0");
		}
		this.costMedal = costMedal;
	}
	
	public int getDayNum() {
		return this.dayNum;
	}

	public void setDayNum(int dayNum) {
		if (dayNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[每日限量]dayNum不可以为0");
		}
		this.dayNum = dayNum;
	}
	
	public int getItemType() {
		return this.itemType;
	}

	public void setItemType(int itemType) {
		if (itemType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[物品类型]itemType不可以为0");
		}
		this.itemType = itemType;
	}
	
	public int getNeedBuildingLevel() {
		return this.needBuildingLevel;
	}

	public void setNeedBuildingLevel(int needBuildingLevel) {
		if (needBuildingLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[需要建筑等级]needBuildingLevel的值不得小于0");
		}
		this.needBuildingLevel = needBuildingLevel;
	}
	

	@Override
	public String toString() {
		return "LegionShopTemplateVO[itemId=" + itemId + ",costMedal=" + costMedal + ",dayNum=" + dayNum + ",itemType=" + itemType + ",needBuildingLevel=" + needBuildingLevel + ",]";

	}
}