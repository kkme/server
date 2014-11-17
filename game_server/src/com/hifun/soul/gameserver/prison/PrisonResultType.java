package com.hifun.soul.gameserver.prison;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
@AutoCreateClientEnumType
public enum PrisonResultType implements IndexedEnum{
	@ClientEnumComment(comment="成功")
	SUCCESS(0),
	@ClientEnumComment(comment="还未加入任何军团")
	NOT_JOINED_LEGION(1),
	@ClientEnumComment(comment="无成员被抓")
	NO_MEMBER_ARRESTED(2),
	@ClientEnumComment(comment="无可求救成员 ")
	NO_MEMBER_HELP(3),
	@ClientEnumComment(comment="已经没有奴隶 ")
	HAVE_NO_SLAVE(4)
	;
	private static final List<PrisonResultType> indexes = IndexedEnumUtil
			.toIndexes(PrisonResultType.values());

	private int index;

	private PrisonResultType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static PrisonResultType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
