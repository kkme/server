package com.hifun.soul.gameserver.item;

import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.enums.IndexedEnum;


/**
 * 洗炼类型
 * @author magicstone
 */
@AutoCreateClientEnumType
public enum ForgeType implements IndexedEnum{
	
	@ClientEnumComment(comment = "免费洗炼")
	FREE(1),
	@ClientEnumComment(comment = "灵石洗炼")
	STONE(2),
	;
	
	private int index;
	
	private ForgeType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
