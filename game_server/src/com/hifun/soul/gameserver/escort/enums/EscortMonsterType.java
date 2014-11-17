package com.hifun.soul.gameserver.escort.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 押运怪物品质类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum EscortMonsterType implements IndexedEnum {
	@ClientEnumComment(comment = "白色怪物")
	WHITE_MONSTER(1), 
	@ClientEnumComment(comment = "绿色怪物")
	GREEN_MONSTER(2),
	@ClientEnumComment(comment = "蓝色怪物")
	BLUE_MONSTER(3),
	@ClientEnumComment(comment = "紫色怪物")
	PURPLE_MONSTER(4),
	@ClientEnumComment(comment = "橙色怪物")
	ORANGE_MONSTER(5);

	private static final List<EscortMonsterType> indexes = IndexedEnumUtil
			.toIndexes(EscortMonsterType.values());

	private int index;

	private EscortMonsterType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static EscortMonsterType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
