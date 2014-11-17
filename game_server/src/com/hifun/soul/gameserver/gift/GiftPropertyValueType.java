package com.hifun.soul.gameserver.gift;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 天赋属性对角色属性的影响方式状态
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum GiftPropertyValueType implements IndexedEnum {
	@ClientEnumComment(comment="加法加成")
	ADDITION(1),
	@ClientEnumComment(comment="乘法加成")
	MULTIPLICATION(2);
	private int index;
	private GiftPropertyValueType(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
