package com.hifun.soul.gameserver.legionmine.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 个人buf类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum SelfBufType implements IndexedEnum {
	@ClientEnumComment(comment="驰")
	RUN(1),
	@ClientEnumComment(comment="袭")
	RAID(2),
	@ClientEnumComment(comment="扰")
	DISTURB(3)
	;

	private static final List<SelfBufType> indexes = IndexedEnumUtil
			.toIndexes(SelfBufType.values());

	private int index;

	private SelfBufType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static SelfBufType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
