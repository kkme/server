package com.hifun.soul.gameserver.shop.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 商店模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ShopTemplateVO extends TemplateObject {

	/**  道具ID */
	@ExcelCellBinding(offset = 1)
	protected int itemId;

	/**  购买货币类型 */
	@ExcelCellBinding(offset = 2)
	protected short currencyType;

	/**  购买货币数量 */
	@ExcelCellBinding(offset = 3)
	protected int num;

	/**  等级限制 */
	@ExcelCellBinding(offset = 4)
	protected int levelLimit;

	/**  职业限制 */
	@ExcelCellBinding(offset = 5)
	protected int occupationLimit;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 道具ID]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public short getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(short currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 购买货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		if (num == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 购买货币数量]num不可以为0");
		}
		this.num = num;
	}
	
	public int getLevelLimit() {
		return this.levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		if (levelLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 等级限制]levelLimit的值不得小于0");
		}
		this.levelLimit = levelLimit;
	}
	
	public int getOccupationLimit() {
		return this.occupationLimit;
	}

	public void setOccupationLimit(int occupationLimit) {
		if (occupationLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 职业限制]occupationLimit的值不得小于0");
		}
		this.occupationLimit = occupationLimit;
	}
	

	@Override
	public String toString() {
		return "ShopTemplateVO[itemId=" + itemId + ",currencyType=" + currencyType + ",num=" + num + ",levelLimit=" + levelLimit + ",occupationLimit=" + occupationLimit + ",]";

	}
}