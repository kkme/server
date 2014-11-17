package com.hifun.soul.gameserver.item;

import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.enums.IndexedEnum;


/**
 * 
 * 装备的位置
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum PositionType implements IndexedEnum{
	
	@ClientEnumComment(comment = "武器")
	WEAPON(1),
	@ClientEnumComment(comment = "手套")
	GLOVE(2),
	@ClientEnumComment(comment = "饰品")
	NECKLACE(3),
	@ClientEnumComment(comment = "头盔")
	HAT(4),
	@ClientEnumComment(comment = "衣服")
	CLOTH(5),
	@ClientEnumComment(comment = "鞋子")
	SHOES(6);
	
	private int index;
	
	private PositionType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
