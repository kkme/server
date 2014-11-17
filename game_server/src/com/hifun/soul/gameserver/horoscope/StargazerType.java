package com.hifun.soul.gameserver.horoscope;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 占星师的类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum StargazerType implements IndexedEnum {
	STARGAZER_ONE(1),
	STARGAZER_TWO(2),
	STARGAZER_THREE(3),
	STARGAZER_FOUR(4),
	STARGAZER_FIVE(5);

	private int index;
	
	private static final List<StargazerType> indexes = IndexedEnumUtil.toIndexes(StargazerType.values());
	
	private StargazerType(int index) {
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
	public static StargazerType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
