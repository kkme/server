package com.hifun.soul.gameserver.mail;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum MailType implements IndexedEnum {
	@ClientEnumComment(comment="系统邮件")
	SYSTEM_MAIL(1),
	@ClientEnumComment(comment="用户邮件")
	USER_MAIL(2);
	
	private int index;
	
	private static final List<MailType> indexes = IndexedEnumUtil.toIndexes(MailType.values());
	
	private MailType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static MailType indexOf(int index){
		return EnumUtil.valueOf(indexes, index);
	}
}
