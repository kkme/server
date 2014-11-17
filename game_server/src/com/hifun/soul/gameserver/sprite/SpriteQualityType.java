package com.hifun.soul.gameserver.sprite;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate;

/**
 * 精灵品质类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum SpriteQualityType implements IndexedEnum {
	/** 精灵-白 */
	@ClientEnumComment(comment = "精灵-白 ")
	SPRITE_WHITE(0) {
		@Override
		public boolean isSoulEnough(Human human,
				SpriteRecruitTemplate recruitTemplate) {
			return false;
		}

		@Override
		public void costSoul(Human human, SpriteRecruitTemplate recruitTemplate) {

		}
	},
	/** 精灵-绿 */
	@ClientEnumComment(comment = "精灵-绿 ")
	SPRITE_GREEN(1) {
		@Override
		public boolean isSoulEnough(Human human,
				SpriteRecruitTemplate recruitTemplate) {
			return human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.getPropertyValue(HumanIntProperty.SPRITE_SOUL_GREEN) >= recruitTemplate
					.getSoulNum();
		}

		@Override
		public void costSoul(Human human, SpriteRecruitTemplate recruitTemplate) {
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(
							HumanIntProperty.SPRITE_SOUL_GREEN,
							human.getPropertyManager()
									.getIntPropertySet(
											PropertyType.HUMAN_INT_PROPERTY)
									.getPropertyValue(
											HumanIntProperty.SPRITE_SOUL_GREEN)
									- recruitTemplate.getSoulNum());
		}
	},
	/** 精灵-蓝 */
	@ClientEnumComment(comment = "精灵-蓝 ")
	SPRITE_BLUE(2) {
		@Override
		public boolean isSoulEnough(Human human,
				SpriteRecruitTemplate recruitTemplate) {
			return human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.getPropertyValue(HumanIntProperty.SPRITE_SOUL_BLUE) >= recruitTemplate
					.getSoulNum();
		}

		@Override
		public void costSoul(Human human, SpriteRecruitTemplate recruitTemplate) {
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(
							HumanIntProperty.SPRITE_SOUL_BLUE,
							human.getPropertyManager()
									.getIntPropertySet(
											PropertyType.HUMAN_INT_PROPERTY)
									.getPropertyValue(
											HumanIntProperty.SPRITE_SOUL_BLUE)
									- recruitTemplate.getSoulNum());
		}
	},
	/** 精灵-紫 */
	@ClientEnumComment(comment = "精灵-紫 ")
	SPRITE_PURPLE(3) {
		@Override
		public boolean isSoulEnough(Human human,
				SpriteRecruitTemplate recruitTemplate) {
			return human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.getPropertyValue(HumanIntProperty.SPRITE_SOUL_PURPLE) >= recruitTemplate
					.getSoulNum();
		}

		@Override
		public void costSoul(Human human, SpriteRecruitTemplate recruitTemplate) {
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(
							HumanIntProperty.SPRITE_SOUL_PURPLE,
							human.getPropertyManager()
									.getIntPropertySet(
											PropertyType.HUMAN_INT_PROPERTY)
									.getPropertyValue(
											HumanIntProperty.SPRITE_SOUL_PURPLE)
									- recruitTemplate.getSoulNum());
		}
	},
	/** 精魂-灵 */
	@ClientEnumComment(comment = "精灵-橙")
	SPRITE_ORANGE(4) {
		@Override
		public boolean isSoulEnough(Human human,
				SpriteRecruitTemplate recruitTemplate) {
			return human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.getPropertyValue(HumanIntProperty.SPRITE_SOUL_ORANGE) >= recruitTemplate
					.getSoulNum();
		}

		@Override
		public void costSoul(Human human, SpriteRecruitTemplate recruitTemplate) {
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(
							HumanIntProperty.SPRITE_SOUL_ORANGE,
							human.getPropertyManager()
									.getIntPropertySet(
											PropertyType.HUMAN_INT_PROPERTY)
									.getPropertyValue(
											HumanIntProperty.SPRITE_SOUL_ORANGE)
									- recruitTemplate.getSoulNum());
		}
	},
	/** 精灵-红 */
	@ClientEnumComment(comment = "精灵-红 ")
	SPRITE_RED(5) {
		@Override
		public boolean isSoulEnough(Human human,
				SpriteRecruitTemplate recruitTemplate) {
			return human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.getPropertyValue(HumanIntProperty.SPRITE_SOUL_RED) >= recruitTemplate
					.getSoulNum();
		}

		@Override
		public void costSoul(Human human, SpriteRecruitTemplate recruitTemplate) {
			human.getPropertyManager()
					.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(
							HumanIntProperty.SPRITE_SOUL_RED,
							human.getPropertyManager()
									.getIntPropertySet(
											PropertyType.HUMAN_INT_PROPERTY)
									.getPropertyValue(
											HumanIntProperty.SPRITE_SOUL_RED)
									- recruitTemplate.getSoulNum());
		}
	},
	;

	private int type;
	private static final Map<Integer, SpriteQualityType> types = new HashMap<Integer, SpriteQualityType>();

	static {
		for (SpriteQualityType each : SpriteQualityType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	SpriteQualityType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static SpriteQualityType typeOf(int type) {
		return types.get(type);
	}

	public abstract boolean isSoulEnough(Human human,
			SpriteRecruitTemplate recruitTemplate);

	public abstract void costSoul(Human human,
			SpriteRecruitTemplate recruitTemplate);
}
