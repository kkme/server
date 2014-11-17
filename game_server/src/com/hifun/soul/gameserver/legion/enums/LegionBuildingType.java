package com.hifun.soul.gameserver.legion.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 军团建筑类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum LegionBuildingType implements IndexedEnum {
	@ClientEnumComment(comment="军团矿战")
	MINE_WAR(1),
	@ClientEnumComment(comment="冥思殿")
	MEDITATION(2),
	@ClientEnumComment(comment="科技厅")
	TECHNOLOGY(3),
	@ClientEnumComment(comment="藏宝楼")
	SHOP(4),
	@ClientEnumComment(comment="赏金令")
	TASK(5),
	@ClientEnumComment(comment="荣誉堂")
	HONOR(6)
	;

	private static final List<LegionBuildingType> indexes = IndexedEnumUtil
			.toIndexes(LegionBuildingType.values());

	private int index;

	private LegionBuildingType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static LegionBuildingType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
