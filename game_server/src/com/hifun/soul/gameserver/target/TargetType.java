package com.hifun.soul.gameserver.target;

import com.hifun.soul.core.enums.IndexedEnum;

public enum TargetType implements IndexedEnum {
	/** 等级 */
	LEVEL(1);

	private int index;

	TargetType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

}
