package com.hifun.soul.gameserver.legion.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 申请加入军团状态
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum JoinApplyLegionStatus implements IndexedEnum {
	@ClientEnumComment(comment = "不可申请")
	CAN_NOT_APPLY(1),
	@ClientEnumComment(comment = "可申请")
	CAN_APPLY(2),
	@ClientEnumComment(comment = "已申请")
	APPLIED(3);

	private static final List<JoinApplyLegionStatus> indexes = IndexedEnumUtil
			.toIndexes(JoinApplyLegionStatus.values());

	private int index;

	private JoinApplyLegionStatus(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static JoinApplyLegionStatus indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
