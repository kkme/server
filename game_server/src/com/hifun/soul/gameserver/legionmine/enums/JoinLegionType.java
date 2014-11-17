package com.hifun.soul.gameserver.legionmine.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
/**
 * 矿战军团类型
 * 
 * @author yandajun
 *
 */
@AutoCreateClientEnumType
public enum JoinLegionType implements IndexedEnum {
	@ClientEnumComment(comment="无军团")
	NO_LEGION(0),
	@ClientEnumComment(comment="红色军团")
	RED_LEGION(1),
	@ClientEnumComment(comment="蓝色军团")
	BLUE_LEGION(2)
	;

	private static final List<JoinLegionType> indexes = IndexedEnumUtil
			.toIndexes(JoinLegionType.values());

	private int index;

	private JoinLegionType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static JoinLegionType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
