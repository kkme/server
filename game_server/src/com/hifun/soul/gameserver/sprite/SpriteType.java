package com.hifun.soul.gameserver.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 精灵类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum SpriteType implements IndexedEnum {
	/** 普攻类型 */
	@ClientEnumComment(comment = "普攻类型 ")
	COMMON_ATTACK(1),
	/** 技能攻击类型 */
	@ClientEnumComment(comment = "技能攻击类型 ")
	SKILL_ATTACK(2),
	/** 血量 */
	@ClientEnumComment(comment = "血量 ")
	HP(3),
	/** 普通防御 */
	@ClientEnumComment(comment = "普通防御")
	COMMON_DEFENCE(4),
	/** 技能防御 */
	@ClientEnumComment(comment = "技能防御 ")
	SKILL_DEFENCE(5), ;

	private int type;
	private static final Map<Integer, SpriteType> types = new HashMap<Integer, SpriteType>();

	static {
		for (SpriteType each : SpriteType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	SpriteType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SpriteType typeOf(int type) {
		return types.get(type);
	}

	/**
	 * 是否所有品质都收集齐了;
	 * 
	 * @param spriteTypeSet
	 * @return
	 */
	public static boolean isAllGet(Set<Integer> spriteTypeSet) {
		List<Integer> targets = new ArrayList<Integer>();
		for (SpriteType eachSpriteType : SpriteType.values()) {
			targets.add(eachSpriteType.getIndex());
		}
		for (Integer eachInt : spriteTypeSet) {
			targets.remove(eachInt);
		}
		return targets.isEmpty();
	}
}
