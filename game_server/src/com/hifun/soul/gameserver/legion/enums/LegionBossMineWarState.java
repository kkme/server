package com.hifun.soul.gameserver.legion.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum LegionBossMineWarState implements IndexedEnum {
	@ClientEnumComment(comment="boss战未开启")
	BOSS_NOT_OPEN(1),
	@ClientEnumComment(comment="boss战进行中")
	BOSS_FIGHTING(2),
	@ClientEnumComment(comment="boss战已结束，矿战未开启")
	BOSS_END_MINE_NOT_OPEN(3),
	@ClientEnumComment(comment="矿战进行中")
	MINE_FIGHTING(4),
	@ClientEnumComment(comment="矿战已结束")
	MINE_END(5)
	;

	private static final List<LegionBossMineWarState> indexes = IndexedEnumUtil
			.toIndexes(LegionBossMineWarState.values());

	private int index;

	private LegionBossMineWarState(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static LegionBossMineWarState indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
