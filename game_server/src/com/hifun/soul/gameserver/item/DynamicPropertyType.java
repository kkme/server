package com.hifun.soul.gameserver.item;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 动态属性类型
 * @author magicstone
 *
 */
public enum DynamicPropertyType implements IndexedEnum{
	COIN(1),
	HONOUR(2),
	PRESTIGE(3)
	;

	private int index;
	private DynamicPropertyType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {		
		return index;
	}
	
	private static final List<DynamicPropertyType> indexes = IndexedEnumUtil.toIndexes(DynamicPropertyType.values());
	public static DynamicPropertyType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}

}
