package com.hifun.soul.gameserver.recharge;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 充值活动类型
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum RechargeActivityType implements IndexedEnum {
	/** 首充 */
	@ClientEnumComment(comment = "首充")
	FIRST_RECHARGE(1),
	/** 直充 */
	@ClientEnumComment(comment = "直充")
	SINGLE_RECHARGE(2),
	/** 累充 */
	@ClientEnumComment(comment = "累充")
	TOTAL_RECHARGE(3);
	private int index;
	private static final List<RechargeActivityType> indexes = IndexedEnumUtil
			.toIndexes(RechargeActivityType.values());

	private RechargeActivityType(int index) {
		this.index = index;
	}

	public RechargeActivityType indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

	public int getIndex() {
		return index;
	}

}
