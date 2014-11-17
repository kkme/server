package com.hifun.soul.gameserver.escort.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
@AutoCreateClientEnumType
public enum EscortRewardState implements IndexedEnum {
	@ClientEnumComment(comment = "无奖励")
	NO_REWARD(0),
	@ClientEnumComment(comment = "有奖励，未领取")
	HAS_NOT_GET(1), 
	@ClientEnumComment(comment = "已领取")
	GOT_REWARD(2)
	;

	private static final List<EscortRewardState> indexes = IndexedEnumUtil
			.toIndexes(EscortRewardState.values());

	private int index;

	private EscortRewardState(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static EscortRewardState indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
