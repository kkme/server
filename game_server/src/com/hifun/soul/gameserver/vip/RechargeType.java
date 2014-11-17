package com.hifun.soul.gameserver.vip;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 充值类型
 * 
 * @author magicstone
 *
 */
public enum RechargeType implements IndexedEnum {
	/** 正常充值 */
	NORMAL_RECHARGE(1),
	/** GM */
	GM_RECHARGE(2);
	
	private int index;
	private RechargeType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {		
		return index;
	}
	
}
