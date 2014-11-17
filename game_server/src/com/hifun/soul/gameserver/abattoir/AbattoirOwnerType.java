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
public enum AbattoirOwnerType implements IndexedEnum {
	@ClientEnumComment(comment="NPC")
	NPC_WRESTLER(1),
	@ClientEnumComment(comment="玩家")
	PLAYER_WRESTLER(2)
	;

	private static final List<AbattoirOwnerType> indexes = IndexedEnumUtil
			.toIndexes(AbattoirOwnerType.values());

	private int index;

	private AbattoirOwnerType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static AbattoirOwnerType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
