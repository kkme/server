package com.hifun.soul.gameserver.legionmine.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 矿堆包围状态
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum MineSurroundState implements IndexedEnum {
	@ClientEnumComment(comment="无包围")
	NOT_SURROUNDED(0),
	@ClientEnumComment(comment="包围")
	SURROUNDED(1),
	@ClientEnumComment(comment="四面楚歌")
	FOUR_SURROUNDED(2),
	@ClientEnumComment(comment="十面埋伏")
	TEN_SURROUNDED(3)
	;

	private static final List<MineSurroundState> indexes = IndexedEnumUtil
			.toIndexes(MineSurroundState.values());

	private int index;

	private MineSurroundState(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static MineSurroundState indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
