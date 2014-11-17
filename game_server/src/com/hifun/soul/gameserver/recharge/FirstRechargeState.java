package com.hifun.soul.gameserver.recharge;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;


@AutoCreateClientEnumType
public enum FirstRechargeState implements IndexedEnum {
	@ClientEnumComment(comment="未充值")
	NO_RECHARGE(0),
	@ClientEnumComment(comment="已充值，未领奖")
	NO_REWARD(1),
	@ClientEnumComment(comment="已领奖")
	FINISHED(2)
	;
	
	private static final List<FirstRechargeState> indexes = IndexedEnumUtil.toIndexes(FirstRechargeState.values());
	
	private int index;
	private FirstRechargeState(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static FirstRechargeState indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}

}
