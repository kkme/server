package com.hifun.soul.gameserver.yellowvip;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

@AutoCreateClientEnumType
public enum YellowVipRewardType implements IndexedEnum{
	@ClientEnumComment(comment="新手礼包")
	ONCE_REWARD(1),
	@ClientEnumComment(comment="每日礼包")
	DAILY_REWARD(2),
	@ClientEnumComment(comment="升级礼包")
	LEVEL_UP_REWARD(3),
	@ClientEnumComment(comment="年费黄钻礼包")
	YEAR_VIP_REWARD(4),
	;
	private int index;
	private YellowVipRewardType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}

}
