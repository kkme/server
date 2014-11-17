package com.hifun.soul.gameserver.shop;

import com.hifun.soul.gameserver.item.assist.CommonItem;

/**
 * 神秘商店物品
 * 
 * @author magicstone
 *
 */
public class SpecialShopItem {

	private int id;
	private int refreshType;
	private int itemId;
	private int itemNum;
	private int currencyType;
	private int currencyNum;
	private int rate;
	private boolean isBuy;
	/** 客户端显示tip的时候用 */
	private CommonItem commonItem;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRefreshType() {
		return refreshType;
	}
	public void setRefreshType(int refreshType) {
		this.refreshType = refreshType;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public int getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}
	public int getCurrencyNum() {
		return currencyNum;
	}
	public void setCurrencyNum(int currencyNum) {
		this.currencyNum = currencyNum;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public boolean getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}
	public CommonItem getCommonItem() {
		return commonItem;
	}
	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}
	
}
