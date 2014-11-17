package com.hifun.soul.gameserver.horoscope;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 角色身上运魂的位置
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum HoroscopeSoulType implements IndexedEnum {
	SOUL_ONE(0),
	SOUL_TWO(1),
	SOUL_THREE(2),
	SOUL_FOUR(3),
	SOUL_FIVE(4),
	SOUL_SIX(5),
	SOUL_SEVEN(6),
	SOUL_EIGHT(7);

	private int index;
	
	private static final List<HoroscopeSoulType> indexes = IndexedEnumUtil.toIndexes(HoroscopeSoulType.values());
	
	private HoroscopeSoulType(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static HoroscopeSoulType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
