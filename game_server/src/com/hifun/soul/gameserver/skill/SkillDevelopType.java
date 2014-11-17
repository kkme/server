package com.hifun.soul.gameserver.skill;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 技能发展类型;
 * 
 * @author magicstone
 * 
 */
@AutoCreateClientEnumType
public enum SkillDevelopType implements IndexedEnum {
	@ClientEnumComment(comment="默认技能")
	DEFAULT(1),
	@ClientEnumComment(comment="宝石")
	GEM(2),
	@ClientEnumComment(comment="能量")
	ENERGY(3),
	@ClientEnumComment(comment="辅助")
	ASSIST(4),
	;

	private int type;
	private static Map<Integer, SkillDevelopType> types = new HashMap<Integer, SkillDevelopType>();

	static {
		for (SkillDevelopType type : SkillDevelopType.values()) {
			types.put(type.getIndex(), type);
		}
	}

	SkillDevelopType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SkillDevelopType typeOf(int type) {
		return types.get(type);
	}
}
