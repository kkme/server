package com.hifun.soul.gameserver.title;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum TitleAmendType implements IndexedEnum {
	@ClientEnumComment(comment="加")
	ADD(1),
	@ClientEnumComment(comment="乘")
	MULIPLY(2),
	@ClientEnumComment(comment="百分比加成")
	ADD_PER(3)
	;

	private static final List<TitleAmendType> indexes = IndexedEnumUtil
			.toIndexes(TitleAmendType.values());

	private int index;

	private TitleAmendType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static TitleAmendType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
