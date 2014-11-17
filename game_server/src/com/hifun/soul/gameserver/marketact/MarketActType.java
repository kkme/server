package com.hifun.soul.gameserver.marketact;

import com.hifun.soul.core.enums.IndexedEnum;

public enum MarketActType implements IndexedEnum{
	TURNTABLE(1);
	private int index;
	private MarketActType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
}
