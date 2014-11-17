package com.hifun.soul.gameserver.prison;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
@AutoCreateClientEnumType
public enum PrisonLogType implements IndexedEnum {
	/** 逮捕 */
	@ClientEnumComment(comment = "逮捕")
	ARREST(1),
	/** 抢夺 */
	@ClientEnumComment(comment = "抢夺")
	LOOT(2),
	/** 互动 */
	@ClientEnumComment(comment = "互动")
	INTERACT(3),
	/** 求救 */
	@ClientEnumComment(comment = "求救")
	SOS(4),
	/** 解救 */
	@ClientEnumComment(comment = "解救")
	RESCUE(5),
	/** 反抗 */
	@ClientEnumComment(comment = "反抗")
	REVOLT(6);
	private static final List<PrisonLogType> indexes = IndexedEnumUtil
			.toIndexes(PrisonLogType.values());

	private int index;

	private PrisonLogType(int index) {
		this.index = index;
	}

	public static PrisonLogType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

	@Override
	public int getIndex() {
		return this.index;
	}
}
