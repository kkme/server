package com.hifun.soul.gameserver.yellowvip;

import com.hifun.soul.core.enums.IndexedEnum;

public enum YellowVipRewardState implements IndexedEnum {
	CANNOT_GET(0),
	CAN_GET(1),
	HAS_GOT(2),
	;
	private int index;
	private YellowVipRewardState(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {		
		return index;
	}
}
