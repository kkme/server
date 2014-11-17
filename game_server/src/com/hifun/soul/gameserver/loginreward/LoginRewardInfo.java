package com.hifun.soul.gameserver.loginreward;

import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;

/**
 * 连续登陆奖励翻牌信息
 */
public class LoginRewardInfo {
	private int index;
	private SimpleCommonItem commonItem;

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public SimpleCommonItem getCommonItem() {
		return commonItem;
	}
	public void setCommonItem(SimpleCommonItem commonItem) {
		this.commonItem = commonItem;
	}
}
