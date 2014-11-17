package com.hifun.soul.gameserver.mars;

public enum MarsRewardState {
	NO_REWARD(0), 
	CAN_GET(1), 
	GETTED(2);

	private int _index;

	private MarsRewardState(int index) {
		this._index = index;
	}

	public int getIndex() {
		return _index;
	}
}