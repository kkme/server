package com.hifun.soul.gameserver.matchbattle;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum EncourageType implements IndexedEnum {
	@ClientEnumComment(comment="金币鼓舞")
	COIN(1),
	@ClientEnumComment(comment="魔晶鼓舞")
	CRYSTAL(2),
	@ClientEnumComment(comment="灵石鼓舞")
	FORGE_STONE(3),
	@ClientEnumComment(comment="冥想力鼓舞")
	MEDITATION(4)
	;
	private int index;
	private EncourageType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
	
	private static final List<EncourageType> indexes = IndexedEnumUtil
			.toIndexes(EncourageType.values());
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static EncourageType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
