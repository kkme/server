package com.hifun.soul.gameserver.building;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 建筑类型
 * 
 * @author magicstone
 * 
 */
@AutoCreateClientEnumType
public enum BuildingType implements IndexedEnum {
	/** 主城 */
	@ClientEnumComment(comment = "主城")
	MAINCITY(1, MainCity.class),
	/** 铁匠铺 */
	@ClientEnumComment(comment = "铁匠铺")
	SMITHY(2, Smithy.class),
	/** 矿场 */
	@ClientEnumComment(comment = "矿场")
	MINEHOUSE(3, MineHouse.class),
	/** 伐木场 */
	@ClientEnumComment(comment = "伐木场")
	WOODHOUSE(4, WoodHouse.class),
	/** 占星屋 */
	@ClientEnumComment(comment = "占星屋")
	ASTROLOGICALHOUSE(5, AstrologicalHouse.class),
	/** 竞技场 */
	@ClientEnumComment(comment = "竞技场")
	ARENA(6, Arena.class),
	/** 科技屋 */
	@ClientEnumComment(comment = "竞技场")
	TECHNOLOGY(7, Technology.class);

	private int index;

	private Class<? extends AbstractBuilding> clazz;

	private static final List<BuildingType> indexes = IndexedEnumUtil
			.toIndexes(BuildingType.values());

	private BuildingType(int index, Class<? extends AbstractBuilding> clazz) {
		this.index = index;
		this.clazz = clazz;
	}

	@Override
	public int getIndex() {
		return index;
	}

	public Class<? extends AbstractBuilding> getClazz() {
		return clazz;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static BuildingType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
