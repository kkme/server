package com.hifun.soul.gameserver.activity;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 活动常量
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum ActivityType implements IndexedEnum{
	
	/** 奇遇答题  */
	@ClientEnumComment(comment="奇遇答题")
	ANSWER_QUESTION(1),
	/** boss战 */
	@ClientEnumComment(comment="boss战")
	BOSS_WAR(2),
	/** 魔石匹配战 */
	@ClientEnumComment(comment="魔石匹配战")
	MATCH_BATTLE(3),
	/** 军团boss战 */
	@ClientEnumComment(comment="军团boss战")
	LEGION_BOSS(4),
	/** 军团矿战 */
	@ClientEnumComment(comment="军团矿战")
	LEGION_MINE(5),
	/** 跑商加成 */
	@ClientEnumComment(comment="跑商加成")
	ESCORT_AMEND(6)
	;
	
	private int index;
	private static final List<ActivityType> indexes = IndexedEnumUtil.toIndexes(ActivityType.values());
	
	private ActivityType(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public static ActivityType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
	
}