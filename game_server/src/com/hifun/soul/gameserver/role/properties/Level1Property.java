package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.core.annotation.Comment;
import com.hifun.soul.core.annotation.Type;

/**
 * 角色一级属性集合;
 * 
 * @author crazyjohn
 * 
 */
public class Level1Property extends IntPropertyCacheSet {
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;

	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;

	@Comment(content = "火焰")
	@Type(Integer.class)
	public static final int FIRE = _BEGIN;

	@Comment(content = "冰霜")
	@Type(Integer.class)
	public static final int ICE = ++_END;
	
	@Comment(content = "光明")
	@Type(Integer.class)
	public static final int LIGHT = ++_END;
	
	@Comment(content = "暗影")
	@Type(Integer.class)
	public static final int SHADOW = ++_END;

	@Comment(content = "自然")
	@Type(Integer.class)
	public static final int NATURE = ++_END;
	
	@Comment(content = "武力")
	@Type(Integer.class)
	public static final int FORCE = ++_END;

	@Comment(content = "敏捷")
	@Type(Integer.class)
	public static final int AGILE = ++_END;
	
	@Comment(content = "智力")
	@Type(Integer.class)
	public static final int INTELLIGENCE = ++_END;
	
	@Comment(content = "体力")
	@Type(Integer.class)
	public static final int STAMINA = ++_END;

	@Comment(content = "精力")
	@Type(Integer.class)
	public static final int SPIRIT = ++_END;
	
	
	public static final int TYPE = PropertyType.LEVEL1_PROPERTY;
	
	/** 属性的个数 */
	public static final int SIZE = _END - _BEGIN + 1;

	public Level1Property() {
		super(Integer.class, SIZE, PropertyType.LEVEL1_PROPERTY);
	}

	@Override
	public int getPropertyType() {
		return PropertyType.LEVEL1_PROPERTY;
	}

}
