package com.hifun.soul.gameserver.skill;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * HP改变类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum HpChangedType implements IndexedEnum {
	/** 被攻击掉血 */
	@ClientEnumComment(comment = "被攻击掉血")
	BE_ATTACKED(0),
	/** 其它原因引起的掉血 */
	@ClientEnumComment(comment = "其它原因引起的掉血")
	OTHER(1);

	private int type;

	HpChangedType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
}
