package com.hifun.soul.gameserver.helper;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum HelpStateType implements IndexedEnum {
	
	/** 可以点击 */
	@ClientEnumComment(comment="可以点击")
	CAN_START(1),
	/** 正在执行(cd中) */
	@ClientEnumComment(comment="正在执行(cd中)")
	RUNING(2),
	/** 可以收获 */
	@ClientEnumComment(comment="可以收获")
	CAN_GET(3),
	/** 已经结束 */
	@ClientEnumComment(comment="已经结束")
	CLOSED(4),
	;
	
	private HelpStateType(int index) {
		this.index = index;
	}
	
	private static final List<HelpStateType> indexes = IndexedEnumUtil.toIndexes(HelpStateType.values());
	
	private int index;
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static HelpStateType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
