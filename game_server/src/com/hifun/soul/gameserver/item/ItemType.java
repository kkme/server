package com.hifun.soul.gameserver.item;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;


/**
 * 
 * 物品标签类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum ItemType  implements IndexedEnum{
	
	@ClientEnumComment(comment="材料")
	MATERIAL(1),
	@ClientEnumComment(comment="道具")
	PROP(2),
	@ClientEnumComment(comment="秘药")
	NOSTURM(3),
	@ClientEnumComment(comment="魄之灵")
	MAGIC_PAPER(4),
	@ClientEnumComment(comment="天赋碎片")
	GIFT_CHIP(5);
	
	
	private int index;
	
	private static final List<ItemType> indexes = IndexedEnumUtil.toIndexes(ItemType.values());
	
	private ItemType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static ItemType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
