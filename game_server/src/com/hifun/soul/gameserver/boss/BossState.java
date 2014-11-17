package com.hifun.soul.gameserver.boss;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum BossState implements IndexedEnum {
	/** 尚未复活或者被击败 */
	@ClientEnumComment(comment="尚未复活或者被击败")
	DEAD(1),
	/** 已经复活 */
	@ClientEnumComment(comment="已经复活")	
	LIVE(2),
	/** 逃跑 */
	@ClientEnumComment(comment=" 逃跑")
	ESCAPE(3)
	;

	private int index;

	private static final List<BossState> indexes = IndexedEnumUtil.toIndexes(BossState.values());

	private BossState(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static BossState indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
