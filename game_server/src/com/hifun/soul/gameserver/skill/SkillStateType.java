package com.hifun.soul.gameserver.skill;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 技能状态;
 * 
 * @author magicstone
 * 
 */
@AutoCreateClientEnumType
public enum SkillStateType implements IndexedEnum {
	@ClientEnumComment(comment="未学习")
	NOT_STUDY(1),
	@ClientEnumComment(comment="未学习，但是可以学习")
	CAN_STUDY(2),
	@ClientEnumComment(comment="已经学习")
	STUDYED(3),
	@ClientEnumComment(comment="重置")
	RESETED(4),
	;

	private int type;
	private static Map<Integer, SkillStateType> types = new HashMap<Integer, SkillStateType>();

	static {
		for (SkillStateType type : SkillStateType.values()) {
			types.put(type.getIndex(), type);
		}
	}

	SkillStateType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SkillStateType typeOf(int type) {
		return types.get(type);
	}
}
