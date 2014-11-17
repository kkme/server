package com.hifun.soul.gameserver.abattoir;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 角斗场房间类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum AbattoirRoomType implements IndexedEnum {
	@ClientEnumComment(comment="不在角斗场")
	NO_ABATTOIR_ROOM(0),
	@ClientEnumComment(comment="痛苦角斗场")
	PAIN_ABATTOIR_ROOM(1),
	@ClientEnumComment(comment="残忍角斗场")
	CRUEL_ABATTOIR_ROOM(2),
	@ClientEnumComment(comment="屠杀角斗场")
	KILL_ABATTOIR_ROOM(3),
	;

	private static final List<AbattoirRoomType> indexes = IndexedEnumUtil
			.toIndexes(AbattoirRoomType.values());

	private int index;

	private AbattoirRoomType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static AbattoirRoomType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
