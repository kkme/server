package com.hifun.soul.gameserver.role.properties.amend;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 定义供策划填表用的各种修正类型,提供了策划定义的修正key与系统的一级,二级和抗性属性的映射关系
 * 
 * 与生成给客户端使用的RoleProperties.as的具体值一致.
 * {@link com.imop.tr.tools.properties.RolePropertiesGenerator}
 * 
 */
public final class AmendTypes {
	public static final int ALL_PROPERTY_COUNT = Level1Property.SIZE
			+ Level2Property.SIZE;

	/** 定义系统对角色的一级,二级和抗性属性的修正类型 */
	private static final Map<Integer, Amend> propertyAmends = Maps.newHashMap();

	static {
		for (int j = 1; j <= (Level1Property.SIZE); j++) {
			int genKey = PropertyType.genPropertyKey(j,
					PropertyType.LEVEL1_PROPERTY);
			propertyAmends.put(genKey, new Amend(genKey,
					PropertyType.LEVEL1_PROPERTY, j));
		}
		for (int j = 1; j <= (Level2Property.SIZE); j++) {
			int genKey = PropertyType.genPropertyKey(j,
					PropertyType.LEVEL2_PROPERTY);
			propertyAmends.put(genKey, new Amend(genKey,
					PropertyType.LEVEL2_PROPERTY, j));
		}
	}

	/**
	 * 根据指定的key值取得对应的修正
	 * 
	 * @param key
	 *            该值是策划填表时使用的修正key,即段基值+偏移量
	 * @return
	 */
	public static Amend getAmend(final int genKey) {
		if (!propertyAmends.containsKey(genKey)) {
			throw new IllegalArgumentException("Not a valid amend genKey ["
					+ genKey + "]");
		}
		return propertyAmends.get(genKey);
	}
}
