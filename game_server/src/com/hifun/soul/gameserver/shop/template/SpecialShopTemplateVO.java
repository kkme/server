package com.hifun.soul.gameserver.shop.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 神秘商店
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpecialShopTemplateVO extends TemplateObject {

	/**  1为定时刷新,2为魔晶刷新 */
	@ExcelCellBinding(offset = 1)
	protected int refreshType;

	/**  物品id */
	@ExcelCellBinding(offset = 2)
	protected int itemId;

	/**  物品数量 */
	@ExcelCellBinding(offset = 3)
	protected int itemNum;

	/**  购买货币类型 */
	@ExcelCellBinding(offset = 4)
	protected short currencyType;

	/**  购买货币数量 */
	@ExcelCellBinding(offset = 5)
	protected int currencyNum;

	/**  获得权重 */
	@ExcelCellBinding(offset = 6)
	protected int rate;

	/**  等级限制 */
	@ExcelCellBinding(offset = 7)
	protected int levelLimit;

	/**  职业限制 */
	@ExcelCellBinding(offset = 8)
	protected int occupationLimit;


	public int getRefreshType() {
		return this.refreshType;
	}

	public void setRefreshType(int refreshType) {
		if (refreshType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 1为定时刷新,2为魔晶刷新]refreshType不可以为0");
		}
		this.refreshType = refreshType;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 物品id]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(int itemNum) {
		if (itemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 物品数量]itemNum不可以为0");
		}
		this.itemNum = itemNum;
	}
	
	public short getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(short currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 购买货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		if (currencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 购买货币数量]currencyNum不可以为0");
		}
		this.currencyNum = currencyNum;
	}
	
	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		if (rate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 获得权重]rate不可以为0");
		}
		this.rate = rate;
	}
	
	public int getLevelLimit() {
		return this.levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		if (levelLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 等级限制]levelLimit的值不得小于0");
		}
		this.levelLimit = levelLimit;
	}
	
	public int getOccupationLimit() {
		return this.occupationLimit;
	}

	public void setOccupationLimit(int occupationLimit) {
		if (occupationLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 职业限制]occupationLimit的值不得小于0");
		}
		this.occupationLimit = occupationLimit;
	}
	

	@Override
	public String toString() {
		return "SpecialShopTemplateVO[refreshType=" + refreshType + ",itemId=" + itemId + ",itemNum=" + itemNum + ",currencyType=" + currencyType + ",currencyNum=" + currencyNum + ",rate=" + rate + ",levelLimit=" + levelLimit + ",occupationLimit=" + occupationLimit + ",]";

	}
}