package com.hifun.soul.gameserver.loginreward;

import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;

/**
 * 连续登陆特殊奖励信息
 */
public class SpecialLoginRewardInfo {
	private int days;
	private SimpleCommonItem reward;
	private int state;
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public SimpleCommonItem getReward() {
		return reward;
	}
	public void setReward(SimpleCommonItem reward) {
		this.reward = reward;
	}
}
