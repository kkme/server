package com.hifun.soul.gameserver.target.msg.info;

import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;

public class TargetRewardInfo {
	private int itemNum;
	private SimpleCommonItem commonItem;

	public SimpleCommonItem getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(SimpleCommonItem commonItem) {
		this.commonItem = commonItem;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
}
