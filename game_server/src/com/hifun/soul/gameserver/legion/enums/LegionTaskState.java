package com.hifun.soul.gameserver.legion.enums;

public enum LegionTaskState {
	/** 进行中 */
	UNDERWAY(1),
	/** 已结束 */
	END(2);
	private int index;

	LegionTaskState(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
