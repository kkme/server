package com.hifun.soul.gameserver.stage.model;

import com.hifun.soul.gameserver.item.assist.CommonItem;

/**
 * 关卡奖励物品信息
 * @author magicstone
 */
public class StageStarRewardItemInfo {
	private int itemId;
	private int itemNum;
	private CommonItem commonItem;
	
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
	public CommonItem getCommonItem() {
		return commonItem;
	}
	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}
}
