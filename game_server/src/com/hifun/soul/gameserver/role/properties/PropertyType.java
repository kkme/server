package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.gameserver.role.properties.type.RolePropertyType;

/**
 * 属性类型;<br>
 * 以后考虑使用面向对象替换常量的方式;现在使用RolePropertyType作为辅助类;
 * 
 * @author crazyjohn
 * 
 */
public abstract class PropertyType {

	/** 角色一级属性 */
	public static final int LEVEL1_PROPERTY = 1;
	/** 角色二级属性 */
	public static final int LEVEL2_PROPERTY = 2;
	/** 角色Int属性 */
	public static final int HUMAN_INT_PROPERTY = 3;
	/** 角色Long属性 */
	public static final int HUMAN_LONG_PROPERTY = 9;
	/** 整形属性数组的长度 */
	public static final int INT_PROPERTY_LENGTH = 3;
	/** 基数 */
	public static final int BASE = 100;

	/**
	 * 产生属性的KEY值，用于服务器之间，服务器和客户端之间数据发送接受
	 * 
	 * @param index
	 *            属性在Property类中的索引
	 * @param propertyType
	 *            Property类的类型
	 * @return
	 */
	public static int genPropertyKey(int index, int propertyType) {
		return propertyType * BASE + index;
	}

	/**
	 * 根据属性的key(如801)获取属性类型;
	 * 
	 * @param key
	 * @return
	 */
	public static int propertyKeyToPropertyType(int key) {
		return RolePropertyType.keyToType(key).getType();
	}

	/**
	 * 属性的key到属性索引的转换;
	 * 
	 * @param key
	 * @return
	 */
	public static int propertyKeyToPropertyIndex(int key) {
		return RolePropertyType.keyToIndex(key);
	}

}
