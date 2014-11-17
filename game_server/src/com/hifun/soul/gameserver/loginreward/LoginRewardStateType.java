package com.hifun.soul.gameserver.loginreward;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 连续登陆特殊奖励状态
 */
@AutoCreateClientEnumType
public enum LoginRewardStateType implements IndexedEnum {
	/** 不可以领取 */
	@ClientEnumComment(comment = "不可以领取")
	CAN_NOT_GET(1),
	/** 可以领取 */
	@ClientEnumComment(comment = "可以领取")
	CAN_GET(2),
	/** 已经领取 */
	@ClientEnumComment(comment = "已经领取")
	GETED(3),
	/** 已经过期 */
	@ClientEnumComment(comment = "已经过期")
	PASTED(4),
	;

	private int index;

	private static final List<LoginRewardStateType> indexes = IndexedEnumUtil
			.toIndexes(LoginRewardStateType.values());

	private LoginRewardStateType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static LoginRewardStateType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
