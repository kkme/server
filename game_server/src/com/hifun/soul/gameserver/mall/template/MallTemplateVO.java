package com.hifun.soul.gameserver.mall.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 商城模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MallTemplateVO extends TemplateObject {

	/**  道具ID */
	@ExcelCellBinding(offset = 1)
	protected int itemId;

	/**  购买货币类型 */
	@ExcelCellBinding(offset = 2)
	protected short currencyType;

	/**  购买货币数量 */
	@ExcelCellBinding(offset = 3)
	protected int num;

	/**  是否上架 */
	@ExcelCellBinding(offset = 4)
	protected boolean sell;

	/**  是否打折 */
	@ExcelCellBinding(offset = 5)
	protected boolean discount;

	/**  打折折扣 */
	@ExcelCellBinding(offset = 6)
	protected short discountRate;

	/**  是否限时 */
	@ExcelCellBinding(offset = 7)
	protected boolean limit;

	/**  限时结束时间 */
	@ExcelCellBinding(offset = 8)
	protected String stopLimitTime;

	/**  物品类型(显示在哪个商城页签,支持多个配置以分号分隔) */
	@ExcelCellBinding(offset = 9)
	protected String mallItemType;

	/**  等级限制 */
	@ExcelCellBinding(offset = 10)
	protected int levelLimit;

	/**  职业限制 */
	@ExcelCellBinding(offset = 11)
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
	
	public boolean isSell() {
		return this.sell;
	}

	public void setSell(boolean sell) {
		this.sell = sell;
	}
	
	public boolean isDiscount() {
		return this.discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	
	public short getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(short discountRate) {
		if (discountRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 打折折扣]discountRate不可以为0");
		}
		this.discountRate = discountRate;
	}
	
	public boolean isLimit() {
		return this.limit;
	}

	public void setLimit(boolean limit) {
		this.limit = limit;
	}
	
	public String getStopLimitTime() {
		return this.stopLimitTime;
	}

	public void setStopLimitTime(String stopLimitTime) {
		if (StringUtils.isEmpty(stopLimitTime)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 限时结束时间]stopLimitTime不可以为空");
		}
		this.stopLimitTime = stopLimitTime;
	}
	
	public String getMallItemType() {
		return this.mallItemType;
	}

	public void setMallItemType(String mallItemType) {
		if (StringUtils.isEmpty(mallItemType)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 物品类型(显示在哪个商城页签,支持多个配置以分号分隔)]mallItemType不可以为空");
		}
		this.mallItemType = mallItemType;
	}
	
	public int getLevelLimit() {
		return this.levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		if (levelLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 等级限制]levelLimit的值不得小于0");
		}
		this.levelLimit = levelLimit;
	}
	
	public int getOccupationLimit() {
		return this.occupationLimit;
	}

	public void setOccupationLimit(int occupationLimit) {
		if (occupationLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 职业限制]occupationLimit的值不得小于0");
		}
		this.occupationLimit = occupationLimit;
	}
	

	@Override
	public String toString() {
		return "MallTemplateVO[itemId=" + itemId + ",currencyType=" + currencyType + ",num=" + num + ",sell=" + sell + ",discount=" + discount + ",discountRate=" + discountRate + ",limit=" + limit + ",stopLimitTime=" + stopLimitTime + ",mallItemType=" + mallItemType + ",levelLimit=" + levelLimit + ",occupationLimit=" + occupationLimit + ",]";

	}
}