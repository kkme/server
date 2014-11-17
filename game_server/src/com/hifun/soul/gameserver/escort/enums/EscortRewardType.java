package com.hifun.soul.gameserver.escort.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum EscortRewardType implements IndexedEnum {
	@ClientEnumComment(comment = "押运奖励")
	ESCORT_REWARD(1),
	@ClientEnumComment(comment = "协助奖励")
	HELP_REWARD(2), 
	@ClientEnumComment(comment = "拦截奖励")
	ROB_REWARD(3)
	;

	private static final List<EscortRewardType> indexes = IndexedEnumUtil
			.toIndexes(EscortRewardType.values());

	private int index;

	private EscortRewardType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static EscortRewardType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
