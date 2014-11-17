package com.hifun.soul.gameserver.activity;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 活动类型枚举
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum ActivityShowType implements IndexedEnum{
	@ClientEnumComment(comment="独立图标类型")
	ALONE_ACTIVITY(1),
	@ClientEnumComment(comment="每日活动类型")
	DAILY_ACTIVITY(2),
	;

	private int index;
	
	private ActivityShowType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}

}
