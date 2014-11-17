package com.hifun.soul.gameserver.bloodtemple;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 嗜血神殿房间类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum BloodTempleOwnerType implements IndexedEnum {
	@ClientEnumComment(comment="NPC")
	NPC_WRESTLER(1),
	@ClientEnumComment(comment="玩家")
	PLAYER_WRESTLER(2)
	;

	private static final List<BloodTempleOwnerType> indexes = IndexedEnumUtil
			.toIndexes(BloodTempleOwnerType.values());

	private int index;

	private BloodTempleOwnerType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static BloodTempleOwnerType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
