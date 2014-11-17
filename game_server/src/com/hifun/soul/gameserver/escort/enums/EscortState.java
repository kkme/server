package com.hifun.soul.gameserver.escort.enums;

public enum EscortState {
	/** 已结束  */
	END(0),
	/** 进行中 */
	UNDERWAY(1);
	private int index;

	EscortState(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
