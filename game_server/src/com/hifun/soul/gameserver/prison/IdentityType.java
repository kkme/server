package com.hifun.soul.gameserver.prison;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum IdentityType implements IndexedEnum {
	@ClientEnumComment(comment="自由人")
	FREEMAN(1),
	@ClientEnumComment(comment="主人")
	MASTER(2),
	@ClientEnumComment(comment="奴隶")
	SLAVE(3)
	;

	private static final List<IdentityType> indexes = IndexedEnumUtil
			.toIndexes(IdentityType.values());

	private int index;

	private IdentityType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static IdentityType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
