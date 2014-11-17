package com.hifun.soul.gameserver.stage;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 关卡地图的状态;
 * @author magicstone
 */
@AutoCreateClientEnumType
public enum StageMapState implements IndexedEnum {
	/** 关卡未开启(开启条件不满足) */
	@ClientEnumComment(comment="关卡未开启(开启条件不满足)")
	UNOPEN(1),
	/** 关卡开启了,但是还未通过 */
	@ClientEnumComment(comment="关卡开启了,但是还未通过")
	OPEN(2),
	/** 关卡已经通过 */
	@ClientEnumComment(comment="关卡已经通过")
	PASSED(3),
	/** 已经领取奖励 */
	@ClientEnumComment(comment="已经领取奖励")
	GETTED(4);
	
	private static final List<StageMapState> indexes = IndexedEnumUtil.toIndexes(StageMapState.values());

	private int type;

	StageMapState(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}
	
	public static StageMapState indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
