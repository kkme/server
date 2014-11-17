package com.hifun.soul.gameserver.horoscope;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 星运所在包的类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum HoroscopeBagType implements IndexedEnum {
	/** 星运主背包 */
	@ClientEnumComment(comment = "星运主背包")
	MAIN_BAG(1),
	/** 装备包 */
	@ClientEnumComment(comment = "装备包")
	EQUIP_BAG(2),
	/** 存储包 */
	@ClientEnumComment(comment = "存储包")
	STORAGE_BAG(3),
	;

	private int index;
	
	private static final List<HoroscopeBagType> indexes = IndexedEnumUtil.toIndexes(HoroscopeBagType.values());
	
	private HoroscopeBagType(int index) {
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
	public static HoroscopeBagType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
