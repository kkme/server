package com.hifun.soul.gameserver.legion.info;

import com.hifun.soul.gameserver.item.assist.CommonItem;

/**
 * 军团商品信息
 * 
 * @author yandajun
 * 
 */
public class LegionShopItemInfo {
	private CommonItem commonItem;
	private int needMedal;
	private int maxNum;
	private int remainNum;
	private int itemType;

	public CommonItem getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}

	public int getNeedMedal() {
		return needMedal;
	}

	public void setNeedMedal(int needMedal) {
		this.needMedal = needMedal;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
}
