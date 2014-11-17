package com.hifun.soul.gameserver.skill;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 技能类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum SkillType implements IndexedEnum {
	@ClientEnumComment(comment="普通攻击技能, 客户端不显示到技能栏")
	NORMAL_ATTACK(1),
	@ClientEnumComment(comment="combo技能, 客户端不显示到技能栏")
	COMBO_ATTACK(2),
	@ClientEnumComment(comment="其它技能, 客户端显示到技能栏")
	OTHER(3);

	private int type;
	private static Map<Integer, SkillType> types = new HashMap<Integer, SkillType>();

	static {
		for (SkillType type : SkillType.values()) {
			types.put(type.getIndex(), type);
		}
	}

	SkillType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SkillType typeOf(int type) {
		return types.get(type);
	}
}
