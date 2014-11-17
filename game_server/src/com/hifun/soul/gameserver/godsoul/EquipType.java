package com.hifun.soul.gameserver.godsoul;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;
/**
 * 装备位类型枚举
 * 
 * @author yandajun
 *
 */
@AutoCreateClientEnumType
public enum EquipType implements IndexedEnum {
	/** 帽子 */
	@ClientEnumComment(comment="头盔")
	HAT(1),
	/** 衣服 */
	@ClientEnumComment(comment="衣服")
	CLOTH(2),
	/** 裤子*/
	@ClientEnumComment(comment="裤子")
	TROUSERS(3),
	/** 鞋子 */
	@ClientEnumComment(comment="鞋子")
	SHOES(4),
	/** 武器 */
	@ClientEnumComment(comment="武器")
	WEAPON(5),
	/** 饰品 */
	@ClientEnumComment(comment="饰品")
	NECKLACE(6),
	/** 戒指 */
	@ClientEnumComment(comment="戒指")
	RING(7),
	/** 护符 */
	@ClientEnumComment(comment="护符")
	AMULET(8)
	;

	private static final List<EquipType> indexes = IndexedEnumUtil
			.toIndexes(EquipType.values());

	private int index;

	private EquipType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static EquipType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
