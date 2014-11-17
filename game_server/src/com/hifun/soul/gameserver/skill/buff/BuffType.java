package com.hifun.soul.gameserver.skill.buff;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * BUFF类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum BuffType implements IndexedEnum {
	/** 眩晕效果 */
	@ClientEnumComment(comment = "眩晕效果")
	STUN(1, false),
	/** 禁魔 */
	@ClientEnumComment(comment = "禁魔 ")
	FORBID_MAGIC(2, false),
	/** 中毒 */
	@ClientEnumComment(comment = "中毒 ")
	POISONING(3, false),
	/** 治愈 */
	@ClientEnumComment(comment = "治愈 ")
	HEALING(4, false) {
		@Override
		public int getPropId() {
			// TODO Auto-generated method stub
			return 0;
		}
	},
	/** 防御 */
	@ClientEnumComment(comment = "技能防御 ")
	DEFENDING(5, false) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.DEFENSE,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 攻击 */
	@ClientEnumComment(comment = "技能攻击 ")
	ATTACKING(6, false) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.ATTACK,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 闪避 */
	@ClientEnumComment(comment = "闪避 ")
	DODGE(7, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.DODGE,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 宝石攻击 */
	@ClientEnumComment(comment = "宝石攻击 ")
	GEM_ATTACK(8, false) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.GEM_ATTACK,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 宝石防御 */
	@ClientEnumComment(comment = "宝石防御")
	GEM_DEFENSE(9, false) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.GEM_DEFENSE,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 物理抗性 */
	@ClientEnumComment(comment = "物理抗性")
	PHYSICAL_RESISTANCE(10, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(
					Level2Property.PHYSICAL_RESISTANCE,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 法术抗性 */
	@ClientEnumComment(comment = "法术抗性")
	MAGIC_RESISTANCE(11, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.MAGIC_RESISTANCE,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 暴击 */
	@ClientEnumComment(comment = "暴击")
	CRIT(12, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.CRITICAL,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 命中 */
	@ClientEnumComment(comment = "命中")
	HIT(13, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.HIT,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 格挡 */
	@ClientEnumComment(comment = "格挡")
	PARRY(14, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.PARRY,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	/** 暴击伤害 */
	@ClientEnumComment(comment = "暴击伤害")
	CRITICAL_DAMAGE(15, true) {
		@Override
		public int getPropId() {
			return PropertyType.genPropertyKey(Level2Property.CRITICAL_DAMAGE,
					PropertyType.LEVEL2_PROPERTY);
		}
	},
	;

	private int type;
	private boolean isPercent;
	private static Map<Integer, BuffType> types = new HashMap<Integer, BuffType>();

	static {
		for (BuffType each : BuffType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	BuffType(int type, boolean isPercent) {
		this.type = type;
		this.isPercent = isPercent;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static BuffType typeOf(int type) {
		return types.get(type);
	}

	/**
	 * 获取关联的属性ID;
	 * 
	 * @return
	 */
	public int getPropId() {
		return -1;
	}

	public String getBuffAddAmendDesc(int value) {
		if (isPercent) {
			return String.valueOf(value
					/ SharedConstants.PROPERTY_PERCENT_DIVISOR + "%");
		} else {
			return String.valueOf(value);
		}
	}

}
