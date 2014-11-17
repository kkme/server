package com.hifun.soul.gameserver.legion;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum LegionResultType implements IndexedEnum {
	@ClientEnumComment(comment="成功")
	SUCCESS(0),
	@ClientEnumComment(comment="退出军团时是团长")
	LEGION_QUIT_IS_COMMANDER(1),
	@ClientEnumComment(comment="退出军团时只有一个人")
	LEGION_QUIT_ONLY_ONE(2)
	;

	private static final List<LegionResultType> indexes = IndexedEnumUtil
			.toIndexes(LegionResultType.values());

	private int index;

	private LegionResultType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static LegionResultType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
