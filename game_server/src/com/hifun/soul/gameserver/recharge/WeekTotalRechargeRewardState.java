package com.hifun.soul.gameserver.recharge;

public enum WeekTotalRechargeRewardState {
	/** 未达到 */
	NOT_REACH(0),
	/** 达到，未领取 */
	NOT_GET(1),
	/** 已领取 */
	FINISHED(2);
	private int index;

	private WeekTotalRechargeRewardState(int index) {
		this.index = index;
	}

	public WeekTotalRechargeRewardState indexOf(int index) {
		return WeekTotalRechargeRewardState.values()[index];
	}

	public int getIndex() {
		return index;
	}
}
