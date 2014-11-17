package com.hifun.soul.gameserver.faction;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 阵营类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum FactionType implements IndexedEnum {

	/** 黑暗 */
	@ClientEnumComment(comment = "黑暗")
	DARK(1),
	/** 光明 */
	@ClientEnumComment(comment = "光明 ")
	BRIGHT(2), ;

	private FactionType(int index) {
		this.index = index;
	}

	private static final List<FactionType> indexes = IndexedEnumUtil
			.toIndexes(FactionType.values());

	private int index;

	@Override
	public int getIndex() {
		return this.index;
	}

	public static FactionType typeOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
