package com.hifun.soul.gameserver.battle.gem.erase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.gem.GemPosition;

/**
 * 宝石消除范围枚举;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum EraseRangeType implements IndexedEnum {
	/** 全盘 */
	@ClientEnumComment(comment="全盘")
	ALL(1),
	/** 指定行 */
	@ClientEnumComment(comment="指定行")
	ROW(2),
	/** 指定列 */
	@ClientEnumComment(comment="指定列")
	COL(3),
	/** 九宫格 */
	@ClientEnumComment(comment="九宫格")
	SUDOKU(4),
	/** 十字 */
	@ClientEnumComment(comment="十字")
	CROSS(5);
	private int type;
	private static Map<Integer, EraseRangeType> types = new HashMap<Integer, EraseRangeType>();

	static {
		for (EraseRangeType each : EraseRangeType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	EraseRangeType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static EraseRangeType typeOf(int rangeType) {
		return types.get(rangeType);
	}

	public Collection<GemPosition> getRangeGemsByRange(Battle battle,
			boolean selected, GemPosition selectedGemPos) {
		return battle.getEraseGemsByRange(selected, selectedGemPos, this);
	}
}
