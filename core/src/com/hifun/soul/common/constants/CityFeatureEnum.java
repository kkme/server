package com.hifun.soul.common.constants;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 城市特色
 * 
 * @author SevenSoul
 *
 */
public enum CityFeatureEnum implements IndexedEnum {
	/** 无特色 */
	NONE(0),
	/** 政治中心 */
	POLITICAL_CENTER(1),
	/** 经济中心 */
	ECONOMIC_CENTER(2),
	/** 贸易中心 */
	TRADE_CENTER(4),
	/** 军事中心 */
	MILITARY_CENTER(8),
	/** 文化中心 */
	CULTURAL_CENTER(16),
;
	/** 枚举值列表 */
	private static final List<CityFeatureEnum> 
		values = IndexedEnumUtil.toIndexes(CityFeatureEnum.values());
	
	/** 枚举索引 */
	private final int index;

	/**
	 * 枚举参数构造器
	 * 
	 * @param index
	 */
	private CityFeatureEnum(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	/**
	 * 将 int 类型转换为枚举类型
	 * 
	 * @param index
	 * @return
	 */
	public static CityFeatureEnum valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}
}
