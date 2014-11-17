package com.hifun.soul.gameserver.activity;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 活动状态
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum ActivityState implements IndexedEnum {
	@ClientEnumComment(comment="不可见")
	UNVISABLE(1),	
	@ClientEnumComment(comment="未解锁")
	LOCK(2),
	@ClientEnumComment(comment="功能已开启，活动未开始")
	READY(3),
	@ClientEnumComment(comment="进行中")
	OPEN(4),
	@ClientEnumComment(comment="已结束")
	FINISH(5),
	;
	private int index;
	private static final List<ActivityState> indexes = IndexedEnumUtil.toIndexes(ActivityState.values());
	private ActivityState(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
	public static ActivityState indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
}
