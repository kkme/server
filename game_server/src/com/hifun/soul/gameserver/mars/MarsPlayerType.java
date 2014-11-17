package com.hifun.soul.gameserver.mars;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 战神之巅玩家类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum MarsPlayerType implements IndexedEnum {
	@ClientEnumComment(comment="NPC")
	NPC(1),
	@ClientEnumComment(comment="玩家")
	PLAYER(2)
	;

	private static final List<MarsPlayerType> indexes = IndexedEnumUtil
			.toIndexes(MarsPlayerType.values());

	private int index;

	private MarsPlayerType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static MarsPlayerType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
