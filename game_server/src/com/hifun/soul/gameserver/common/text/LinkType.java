package com.hifun.soul.gameserver.common.text;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 链接对象类型
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum LinkType implements IndexedEnum{
	/** 角色 */
	@ClientEnumComment(comment = "角色")
	CHARACTER(1),
	/** 可叠加的通用物品,通过itemId查询 */
	@ClientEnumComment(comment = "可叠加的通用物品,通过itemId查询")
	COMMON_ITEM(2),
	/** 含特殊属性的物品,通过uuid进行查询 */
	@ClientEnumComment(comment = "含特殊属性的物品,通过uuid进行查询")
	SPECIAL_ITEM(3);
	private int index;
	private LinkType(int index){
		this.index = index;
	}
	public int getIndex(){
		return this.index;
	}
}
