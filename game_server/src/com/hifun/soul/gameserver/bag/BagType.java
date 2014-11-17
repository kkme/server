package com.hifun.soul.gameserver.bag;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum BagType implements IndexedEnum{
	/** 主背包 */
	MAIN_BAG(1),
	/** 其他 */
	OTHER(3);
	private int index;
	
	private static final List<BagType> indexes = IndexedEnumUtil.toIndexes(BagType.values());
	
	private BagType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static BagType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
}
