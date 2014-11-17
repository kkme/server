package com.hifun.soul.gameserver.prison;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum MasterExtractType implements IndexedEnum {
	@ClientEnumComment(comment = "提取当前经验")
	EXTRACT_CURRENT(1), 
	@ClientEnumComment(comment = "提取1小时经验")
	EXTRACT_ONE_HOUR(2), 
	@ClientEnumComment(comment = "提取全部经验")
	EXTRACT_TOTAL(3);

	private static final List<MasterExtractType> indexes = IndexedEnumUtil
			.toIndexes(MasterExtractType.values());

	private int index;

	private MasterExtractType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static MasterExtractType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
