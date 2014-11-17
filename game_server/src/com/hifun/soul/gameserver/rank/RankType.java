package com.hifun.soul.gameserver.rank;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum RankType implements IndexedEnum {
	@ClientEnumComment(comment="角色等级排行榜")
	HUMAN_LEVEL_RANK(1),
	@ClientEnumComment(comment="竞技场排行榜")
	ARENA_RANK(2),
	@ClientEnumComment(comment="匹配战连胜排行榜")
	MATCH_BATTLE_STREAK_WIN_RANK(3),
	@ClientEnumComment(comment="角色军衔排行榜")
	HUMAN_TITLE_RANK(4),
	@ClientEnumComment(comment="角色荣誉值排行榜")
	HUMAN_HONOR_RANK(5),
	@ClientEnumComment(comment="角色VIP排行榜")
	HUMAN_VIP_RANK(6),
	@ClientEnumComment(comment="军团等级排行榜")
	LEGION_LEVEL_RANK(7),
	@ClientEnumComment(comment="军团人数排行榜")
	LEGION_MEMBER_RANK(8)
	;
	
	private static final List<RankType> indexes = IndexedEnumUtil.toIndexes(RankType.values());
	
	private int index;
	private RankType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static RankType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}

}
