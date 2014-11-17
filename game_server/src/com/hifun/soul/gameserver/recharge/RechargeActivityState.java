package com.hifun.soul.gameserver.recharge;

public enum RechargeActivityState {
	/** 无活动 */
	NO_ACTIVITY(0),
	/** 时间未到 */
	PRE_TIME(1),
	/** 进行中 */
	IN_TIME(2),
	/** 过期 */
	OUT_TIME(3);
	private int index;

	private RechargeActivityState(int index) {
		this.index = index;
	}

	public RechargeActivityState indexOf(int index) {
		return RechargeActivityState.values()[index];
	}

	public int getIndex() {
		return index;
	}
}
