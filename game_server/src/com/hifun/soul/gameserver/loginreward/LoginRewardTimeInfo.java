package com.hifun.soul.gameserver.loginreward;

/**
 * 连续登陆天数和对应的奖励领取次数
 */
public class LoginRewardTimeInfo {
	private int days;
	private int times;
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
}
