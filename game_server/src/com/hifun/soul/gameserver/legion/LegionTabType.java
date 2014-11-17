package com.hifun.soul.gameserver.legion;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum LegionTabType implements IndexedEnum {
	@ClientEnumComment(comment = "军团列表")
	LEGIONLIST(1),
	@ClientEnumComment(comment = "军团信息")
	LEGIONINFO(2),
	@ClientEnumComment(comment = "成员列表")
	MEMBERLIST(3),
	@ClientEnumComment(comment = "申请列表")
	APPLYLIST(4),
	@ClientEnumComment(comment = "军团管理")
	LEGIONMANAGE(5);

	private int index;
	private static final List<LegionTabType> indexes = IndexedEnumUtil
			.toIndexes(LegionTabType.values());

	LegionTabType(int index) {

	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static LegionTabType valueOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
