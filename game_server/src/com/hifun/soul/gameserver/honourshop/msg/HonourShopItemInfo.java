package com.hifun.soul.gameserver.honourshop.msg;

import com.hifun.soul.gameserver.item.assist.CommonItem;

public class HonourShopItemInfo {
	private Integer itemId;
	private int needHonour;
	/** 物品的基本属性 */
	private CommonItem commonItem;
	private int itemType;
	private int visibleLevel;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public int getNeedHonour() {
		return needHonour;
	}

	public void setNeedHonour(int needHonour) {
		this.needHonour = needHonour;
	}

	public CommonItem getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getVisibleLevel() {
		return visibleLevel;
	}

	public void setVisibleLevel(int visibleLevel) {
		this.visibleLevel = visibleLevel;
	}
}
