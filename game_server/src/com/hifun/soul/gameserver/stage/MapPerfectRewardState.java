package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 地图完美通关奖励的状态;
 * @author magicstone
 */
@AutoCreateClientEnumType
public enum MapPerfectRewardState implements IndexedEnum {
	/** 不能领取 */
	@ClientEnumComment(comment="不能领取")
	UNGET(1),
	/** 可以领取*/
	@ClientEnumComment(comment="可以领取")
	CANGET(2),
	/** 已经领取 */
	@ClientEnumComment(comment="已经领取")
	GETTED(3);
	
	private static final List<MapPerfectRewardState> indexes = IndexedEnumUtil.toIndexes(MapPerfectRewardState.values());

	private int type;

	MapPerfectRewardState(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
	
	public static MapPerfectRewardState indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
