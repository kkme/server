package com.hifun.soul.gameserver.mars;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum MarsRoomType implements IndexedEnum {
	@ClientEnumComment(comment="普通")
	COMMON(1),
	@ClientEnumComment(comment="精良")
	SUPERIOR(2),
	@ClientEnumComment(comment="优秀")
	EXCELLENT(3),
	@ClientEnumComment(comment="传奇")
	LEGEND(4)
	;

	private static final List<MarsRoomType> indexes = IndexedEnumUtil
			.toIndexes(MarsRoomType.values());

	private int index;

	private MarsRoomType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static MarsRoomType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
