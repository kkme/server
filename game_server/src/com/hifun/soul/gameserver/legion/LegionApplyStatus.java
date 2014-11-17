package com.hifun.soul.gameserver.legion;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum LegionApplyStatus implements IndexedEnum {
	@ClientEnumComment(comment = "已提")
	APPLIED(1),
	@ClientEnumComment(comment = "同意")
	AGREED(2),
	@ClientEnumComment(comment = "拒绝")
	REJECTED(3);

	private int index;

	private static final List<LegionApplyStatus> indexes = IndexedEnumUtil
			.toIndexes(LegionApplyStatus.values());

	private LegionApplyStatus(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	public LegionApplyStatus valueOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
