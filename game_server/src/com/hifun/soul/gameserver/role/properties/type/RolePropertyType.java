package com.hifun.soul.gameserver.role.properties.type;

import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 以后角色属性类型的重构方向;<br>
 * 目前作为PropertyType的辅助类;
 * 
 * @author crazyjohn
 * 
 */
public enum RolePropertyType {
	/** 一级属性 */
	LEVEL1_PROPERTY(PropertyType.LEVEL1_PROPERTY, 1, 1),
	/** 二级属性 */
	LEVEL2_PROPERTY(PropertyType.LEVEL2_PROPERTY, 2, 2),
	/** 角色整形属性 */
	HUMAN_INT_PROPERTY(PropertyType.HUMAN_INT_PROPERTY, 3, 8),
	/** 角色长整形属性 */
	HUMAN_LONG_PROPERTY(PropertyType.HUMAN_LONG_PROPERTY, 9, 9);

	/** 起始 */
	private int begin;
	/** 结束 */
	private int end;
	/** 类型 */
	private int type;

	RolePropertyType(int type, int begin, int end) {
		this.type = type;
		this.begin = begin;
		this.end = end;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public int getType() {
		return type;
	}

	/**
	 * 是否是当前属性类型的范围;
	 * 
	 * @param value
	 * @return
	 */
	public boolean isMyRange(int value) {
		return (value >= begin && value <= end);
	}

	private static RolePropertyType findTypeByRange(int propType) {
		for (RolePropertyType each : RolePropertyType.values()) {
			if (each.isMyRange(propType)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 属性的key值到属性索引的转换;
	 * 
	 * @param key
	 * @return
	 */
	public static int keyToIndex(int key) {
		return key - (keyToType(key).getType() * PropertyType.BASE);
	}

	/**
	 * 根据属性的key值,如801获取属性类型;
	 * 
	 * @param key
	 * @return
	 */
	public static RolePropertyType keyToType(int key) {
		int propType = key / PropertyType.BASE;
		return RolePropertyType.findTypeByRange(propType);
	}
}
