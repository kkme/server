package com.hifun.soul.gameserver.mall.msg;

import com.hifun.soul.gameserver.item.assist.CommonItem;


/**
 * 
 * 商城物品,主要用于与客户端通信
 * 
 * @author magicstone
 *
 */
public class MallItemInfo {

	private Integer itemId;
	
	private short currencyType;
	
	private int num;
	
	private boolean discount;
	
	private float discountRate;
	
	private int mallItemType;
	
	/** 物品的基本属性 */
	private CommonItem commonItem;
	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public short getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(short currencyType) {
		this.currencyType = currencyType;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean getDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	public float getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(float discountRate) {
		this.discountRate = discountRate;
	}

	public CommonItem getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}

	public int getMallItemType() {
		return mallItemType;
	}

	public void setMallItemType(int mallItemType) {
		this.mallItemType = mallItemType;
	}
	
}
