package com.hifun.soul.gameserver.arena;

public enum ArenaRankRewardState {
	NO_REWARD(0),
	CAN_GET(1),
	GETTED(2);
	
	private int _index;
	
	private ArenaRankRewardState(int index) {
		this._index = index;
	}
	
	public int getIndex() {
		return _index;
	}
}
