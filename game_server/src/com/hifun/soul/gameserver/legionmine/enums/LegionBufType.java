package com.hifun.soul.gameserver.legionmine.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
@AutoCreateClientEnumType
public enum LegionBufType implements IndexedEnum{
	@ClientEnumComment(comment="占领红矿收益加成buf")
	RED_MINE_AMEND(1),
	@ClientEnumComment(comment="所占矿位数少攻防加成buf")
	OCCUPY_MINE_AMEND(2);
	private static final List<LegionBufType> indexes = IndexedEnumUtil
			.toIndexes(LegionBufType.values());

	private int index;

	private LegionBufType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static LegionBufType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
