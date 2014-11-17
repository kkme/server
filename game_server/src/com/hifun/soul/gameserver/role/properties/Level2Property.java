package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.core.annotation.Comment;
import com.hifun.soul.core.annotation.Type;

/**
 * 角色二级属性集合;
 * 
 * @author crazyjohn
 * 
 */
public class Level2Property extends IntPropertyCacheSet {
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;

	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;
	
	@Comment(content = "最大HP")
	@Type(Integer.class)
	public static final int MAX_HP = _BEGIN;

	@Comment(content = "技能攻击力")
	@Type(Integer.class)
	public static final int ATTACK = ++_END;

	@Comment(content = "技能防御力")
	@Type(Integer.class)
	public static final int DEFENSE = ++_END;

	@Comment(content = "命中")
	@Type(Integer.class)
	public static final int HIT = ++_END;

	@Comment(content = "闪避")
	@Type(Integer.class)
	public static final int DODGE = ++_END;

	@Comment(content = "暴击")
	@Type(Integer.class)
	public static final int CRITICAL = ++_END;

	@Comment(content = "韧性")
	@Type(Integer.class)
	public static final int UNCRITICAL = ++_END;

	@Comment(content = "暴击伤害")
	@Type(Integer.class)
	public static final int CRITICAL_DAMAGE = ++_END;

	@Comment(content = "韧性伤害")
	@Type(Integer.class)
	public static final int UNCRITICAL_DAMAGE = ++_END;

	@Comment(content = "格挡")
	@Type(Integer.class)
	public static final int PARRY = ++_END;

	@Comment(content = "格挡伤害")
	@Type(Integer.class)
	public static final int PARRY_DAMAGE = ++_END;

	@Comment(content = "破击")
	@Type(Integer.class)
	public static final int UNPARRY = ++_END;

	@Comment(content = "破击伤害")
	@Type(Integer.class)
	public static final int UNPARRY_DAMAGE = ++_END;
	// ========== red ==========
	@Comment(content = "红魔上限")
	@Type(Integer.class)
	public static final int RED_MAX = ++_END;

	@Comment(content = "红魔初始值")
	@Type(Integer.class)
	public static final int RED_INIT_VALUE = ++_END;

	@Comment(content = "消除红魔加成值")
	@Type(Integer.class)
	public static final int RED_ELIMINATE_BONUS = ++_END;

	// ========== yellow ==========
	@Comment(content = "黄魔上限")
	@Type(Integer.class)
	public static final int YELLOW_MAX = ++_END;

	@Comment(content = "黄魔初始值")
	@Type(Integer.class)
	public static final int YELLOW_INIT_VALUE = ++_END;

	@Comment(content = "消除黄魔加成")
	@Type(Integer.class)
	public static final int YELLOW_ELIMINATE_BONUS = ++_END;

	// ========== BLUE ==========
	@Comment(content = "蓝魔上限")
	@Type(Integer.class)
	public static final int BLUE_MAX = ++_END;

	@Comment(content = "蓝魔初始值")
	@Type(Integer.class)
	public static final int BLUE_INIT_VALUE = ++_END;

	@Comment(content = "消除蓝魔加成值")
	@Type(Integer.class)
	public static final int BLUE_ELIMINATE_BONUS = ++_END;

	// ========== GREEN ==========
	@Comment(content = "绿魔上限")
	@Type(Integer.class)
	public static final int GREEN_MAX = ++_END;

	@Comment(content = "绿魔初始值")
	@Type(Integer.class)
	public static final int GREEN_INIT_VALUE = ++_END;

	@Comment(content = "消除绿魔获得的加成")
	@Type(Integer.class)
	public static final int GREEN_ELIMINATE_BONUS = ++_END;

	// ========== PURPLE ==========
	@Comment(content = "紫魔上限")
	@Type(Integer.class)
	public static final int PURPLE_MAX = ++_END;

	@Comment(content = "紫魔初始值")
	@Type(Integer.class)
	public static final int PURPLE_INIT_VALUE = ++_END;

	@Comment(content = "消除紫魔加成值")
	@Type(Integer.class)
	public static final int PURPLE_ELIMINATE_BONUS = ++_END;
	
	@Comment(content = "宝石攻击力")
	@Type(Integer.class)
	public static final int GEM_ATTACK = ++_END;

	@Comment(content = "宝石防御力")
	@Type(Integer.class)
	public static final int GEM_DEFENSE = ++_END;
	
	@Comment(content = "先攻")
	@Type(Integer.class)
	public static final int FIRST_ATTACK = ++_END;
	
	@Comment(content = "物理抗性")
	@Type(Integer.class)
	public static final int PHYSICAL_RESISTANCE = ++_END;
	
	@Comment(content = "法术抗性")
	@Type(Integer.class)
	public static final int MAGIC_RESISTANCE = ++_END;
	
	@Comment(content = "黑宝石攻击加成百分比")
	@Type(Integer.class)
	public static final int BLACK_GEM_ATTACK_PER_ADD_RATE = ++_END;
	
	@Comment(content = "白宝石恢复能量上限百分比")
	@Type(Integer.class)
	public static final int WHITE_GEM_RECOVER_ENERGY_RATE = ++_END;

	public static final int TYPE = PropertyType.LEVEL2_PROPERTY;

	/** 属性的个数 */
	public static final int SIZE = _END - _BEGIN + 1;

	public Level2Property() {
		super(Integer.class, SIZE, PropertyType.LEVEL2_PROPERTY);
	}

	@Override
	public int getPropertyType() {
		return PropertyType.LEVEL2_PROPERTY;
	}

}
