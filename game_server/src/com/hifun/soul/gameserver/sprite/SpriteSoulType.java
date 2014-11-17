package com.hifun.soul.gameserver.sprite;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 精魂类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum SpriteSoulType implements IndexedEnum {
	/** 精魂-绿 */
	@ClientEnumComment(comment = "精魂-绿 ")
	SPRITE_SOUL_GREEN(1) {
		@Override
		public void doAddSpriteNum(Human human, int rewardNum) {
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(
							HumanIntProperty.SPRITE_SOUL_GREEN,
							human.getPropertyManager()
									.getIntPropertySet(
											PropertyType.HUMAN_INT_PROPERTY)
									.getPropertyValue(
											HumanIntProperty.SPRITE_SOUL_GREEN)
									+ rewardNum);
		}
	},
	/** 精魂-蓝 */
	@ClientEnumComment(comment = "精魂-蓝 ")
	SPRITE_SOUL_BLUE(2) {
		@Override
		public void doAddSpriteNum(Human human, int rewardNum) {
			human.getPropertyManager()
			.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(
					HumanIntProperty.SPRITE_SOUL_BLUE,
					human.getPropertyManager()
							.getIntPropertySet(
									PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(
									HumanIntProperty.SPRITE_SOUL_BLUE)
							+ rewardNum);
		}
	},
	/** 精魂-紫 */
	@ClientEnumComment(comment = "精魂-紫 ")
	SPRITE_SOUL_PURPLE(3) {
		@Override
		public void doAddSpriteNum(Human human, int rewardNum) {
			human.getPropertyManager()
			.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(
					HumanIntProperty.SPRITE_SOUL_PURPLE,
					human.getPropertyManager()
							.getIntPropertySet(
									PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(
									HumanIntProperty.SPRITE_SOUL_PURPLE)
							+ rewardNum);
		}
	},
	/** 精魂-橙 */
	@ClientEnumComment(comment = "精魂-橙")
	SPRITE_SOUL_ORANGE(4) {
		@Override
		public void doAddSpriteNum(Human human, int rewardNum) {
			human.getPropertyManager()
			.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(
					HumanIntProperty.SPRITE_SOUL_ORANGE,
					human.getPropertyManager()
							.getIntPropertySet(
									PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(
									HumanIntProperty.SPRITE_SOUL_ORANGE)
							+ rewardNum);
		}
	},
	/** 精魂-红 */
	@ClientEnumComment(comment = "精魂-红 ")
	SPRITE_SOUL_RED(5) {
		@Override
		public void doAddSpriteNum(Human human, int rewardNum) {
			human.getPropertyManager()
			.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(
					HumanIntProperty.SPRITE_SOUL_RED,
					human.getPropertyManager()
							.getIntPropertySet(
									PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(
									HumanIntProperty.SPRITE_SOUL_RED)
							+ rewardNum);
		}
	},
	;

	private int type;
	private static Map<Integer, SpriteSoulType> types = new HashMap<Integer, SpriteSoulType>();

	static {
		for (SpriteSoulType each : SpriteSoulType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	SpriteSoulType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SpriteSoulType typeOf(int soulType) {
		return types.get(soulType);
	}

	public abstract void doAddSpriteNum(Human human, int rewardNum);

}
