package com.hifun.soul.gameserver.levy.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum LevyBetType implements IndexedEnum {
	@ClientEnumComment(comment="大")
	BIG(1),
	@ClientEnumComment(comment="小")
	SMALL(2),
	@ClientEnumComment(comment="必胜")
	WIN(3)
	;
	
	private static final List<LevyBetType> indexes = IndexedEnumUtil.toIndexes(LevyBetType.values());
	
	private int index;
	private LevyBetType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static LevyBetType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}

}
