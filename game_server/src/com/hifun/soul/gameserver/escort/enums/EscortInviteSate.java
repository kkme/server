package com.hifun.soul.gameserver.escort.enums;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 押运邀请状态
 * 
 * @author yandajun
 * 
 */
@AutoCreateClientEnumType
public enum EscortInviteSate implements IndexedEnum {
	@ClientEnumComment(comment = "未邀请")
	NOT_INVITE(0),
	@ClientEnumComment(comment = "已邀请，未同意")
	INVITE_NOT_AGREE(1), 
	@ClientEnumComment(comment = "已同意")
	AGREE(2)
	;

	private static final List<EscortInviteSate> indexes = IndexedEnumUtil
			.toIndexes(EscortInviteSate.values());

	private int index;

	private EscortInviteSate(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public static EscortInviteSate indexOf(int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
