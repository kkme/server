package com.hifun.soul.gameserver.friend;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

@AutoCreateClientEnumType
public enum FriendRewardState implements IndexedEnum {
	/** 没有赠送 */
	@ClientEnumComment(comment = "没有赠送")
	NO_REWARD(1),
	/** 可以领取 */
	@ClientEnumComment(comment = "可以领取")
	CAN_GET(2),
	/** 已经领取 */
	@ClientEnumComment(comment = "已经领取")
	GETTED(3),
	;

	private int index;
	FriendRewardState(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
