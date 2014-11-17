package com.hifun.soul.gameserver.target;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
@AutoCreateClientEnumType
public enum TargetRewardState implements IndexedEnum {
	@ClientEnumComment(comment="未达到")
	NOT_REACH(0),
	@ClientEnumComment(comment="已达到，未领奖")
	CAN_GET(1),
	@ClientEnumComment(comment="已领奖")
	FINISHED(2)
	;

	private static final List<TargetRewardState> indexes = IndexedEnumUtil
			.toIndexes(TargetRewardState.values());

	private int index;

	private TargetRewardState(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static TargetRewardState indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
